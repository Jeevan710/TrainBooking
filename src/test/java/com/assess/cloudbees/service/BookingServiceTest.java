package com.assess.cloudbees.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.assess.cloudbees.DAO.BookingDAO;
import com.assess.cloudbees.DAO.TrainDAO;
import com.assess.cloudbees.bean.request.BookingRequest;
import com.assess.cloudbees.bean.request.ModifyRequest;
import com.assess.cloudbees.entity.Booking;
import com.assess.cloudbees.entity.Section;
import com.assess.cloudbees.entity.Train;
import com.assess.cloudbees.exception.InvalidBookingRequestException;
import com.assess.cloudbees.exception.ResourceNotFoundException;

@SpringBootTest
public class BookingServiceTest {
	@Mock
    private TrainDAO trainDAO;

    @Mock
    private BookingDAO bookingDAO;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private BookingRequest validBookingRequest;
    private Train validTrain;
    private List<Booking> existingBookings;
    private Booking validBooking;
    private ModifyRequest validModifyRequest;

    @BeforeEach
    public void setUp() {
        validBookingRequest = new BookingRequest();
        validBookingRequest.setMail("j@gmail.com");
        validBookingRequest.setSection("Sleeper");
        validBookingRequest.setTrainNumber("123456");
        validBookingRequest.setFrom("London");
        validBookingRequest.setTo("France");
        validBookingRequest.setSeat("1A");

        validTrain = new Train();
        validTrain.setTrainNumber("123456");
        validTrain.setFrom("London");
        validTrain.setTo("France");
        validTrain.addSection(new Section("Sleeper",20.00,"30"));
        validTrain.addSection(new Section("AC",30.00,"30"));
        existingBookings = Collections.emptyList(); // Assuming no existing bookings
        
        validBooking = new Booking();
        validBooking.setMail("test@example.com");
        validBooking.setSection("Sleeper");
        validBooking.setSeat("1A");
        
        validModifyRequest = new ModifyRequest();
        validModifyRequest.setMail("j@example.com");
        validModifyRequest.setFromSection("Sleeper");
        validModifyRequest.setFromSeat("1A");
        validModifyRequest.setToSection("AC");
        validModifyRequest.setToSeat("2B");
        validModifyRequest.setTrainNumber("123456");
    }

    // TestCase for Booking Tickets
    
    
    @Test
    void testBookTicket_ValidRequest() {
        when(trainDAO.findByTrainNumber("123456")).thenReturn(validTrain);
        when(bookingDAO.findAll()).thenReturn(existingBookings);
        when(bookingDAO.save(any(Booking.class))).thenAnswer(i -> i.getArgument(0));

        Booking booking = bookingService.bookTicket(validBookingRequest);

        assertNotNull(booking);
        assertEquals("Sleeper", booking.getSection());
        assertEquals("1A", booking.getSeat());
        verify(bookingDAO, times(1)).save(any(Booking.class));
    }

    @Test
    public void testBookTicket_InvalidSection() {
        validBookingRequest.setSection("InvalidSection");

        InvalidBookingRequestException exception = assertThrows(
                InvalidBookingRequestException.class,
                () -> bookingService.bookTicket(validBookingRequest)
        );

        assertEquals("Please select a valid section", exception.getMessage());
    }

    @Test
    public void testBookTicket_InvalidTrainDetails() {
        when(trainDAO.findByTrainNumber("123456")).thenReturn(null);

        InvalidBookingRequestException exception = assertThrows(
                InvalidBookingRequestException.class,
                () -> bookingService.bookTicket(validBookingRequest)
        );

        assertEquals("Please input valid train details", exception.getMessage());
    }

    @Test
    void testBookTicket_SeatNotAvailable() {
    	validBookingRequest.setSection("Sleeper");
        when(trainDAO.findByTrainNumber("123456")).thenReturn(validTrain);

        Booking existingBooking = new Booking();
        existingBooking.setSection("Sleeper");
        existingBooking.setSeat("1A");
        existingBookings = List.of(existingBooking);

        when(bookingDAO.findAll()).thenReturn(existingBookings);

        InvalidBookingRequestException exception = assertThrows(
                InvalidBookingRequestException.class,
                () -> bookingService.bookTicket(validBookingRequest)
        );

        assertEquals("Sorry, this seat is not available", exception.getMessage());
    }
    
    
    //TestCase for Receipts By Mail
    
    @Test
    public void testGetReceiptsByMail_BookingsFound() {
        String mail = "test@example.com";
        List<Booking> bookings = Arrays.asList(validBooking);

        when(bookingDAO.findByMail(mail)).thenReturn(bookings);

        List<Booking> result = bookingService.getReceiptsByMail(mail);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Sleeper", result.get(0).getSection());
        assertEquals("1A", result.get(0).getSeat());

        verify(bookingDAO, times(1)).findByMail(mail);
    }

    @Test
    public void testGetReceiptsByMail_NoBookingsFound() {
        String mail = "test@example.com";

        when(bookingDAO.findByMail(mail)).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> bookingService.getReceiptsByMail(mail)
        );

        assertEquals("No bookings found", exception.getMessage());

        verify(bookingDAO, times(1)).findByMail(mail);
    }
    
    //TestCase to Delete Tickets By User
    @Test
    public void testCancelBooking() {
        String mail = "test1@example.com";
        bookingService.cancelBooking(mail);
        verify(bookingDAO, times(1)).deleteByMail(mail);
    }
    
    //TestCase to view seat and user by section
    @Test
    public void testGetSectionDetails_ValidSection_BookingsFound() {
        String section = "Sleeper";
        List<Booking> bookings = Arrays.asList(validBooking);

        when(bookingDAO.findBySection(section)).thenReturn(bookings);

        List<Booking> result = bookingService.getSectionDetails(section);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Sleeper", result.get(0).getSection());
        assertEquals("1A", result.get(0).getSeat());

        verify(bookingDAO, times(1)).findBySection(section);
    }

    @Test
    public void testGetSectionDetails_InvalidSection() {
        String section = "Economy";

        InvalidBookingRequestException exception = assertThrows(
            InvalidBookingRequestException.class,
            () -> bookingService.getSectionDetails(section)
        );

        assertEquals("Please enter a valid section", exception.getMessage());

        verify(bookingDAO, never()).findBySection(anyString());
    }

    @Test
    public void testGetSectionDetails_ValidSection_NoBookingsFound() {
        String section = "Sleeper";

        when(bookingDAO.findBySection(section)).thenReturn(null);

        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> bookingService.getSectionDetails(section)
        );

        assertEquals("No bookings found", exception.getMessage());

        verify(bookingDAO, times(1)).findBySection(section);
    }

    //   Modify Seat
    
    @Test
    public void testModifyTicket_ValidModification() {
        List<Booking> bookings = Arrays.asList(validBooking);

        when(trainDAO.findByTrainNumber(validModifyRequest.getTrainNumber())).thenReturn(validTrain);
        when(bookingDAO.findAll()).thenReturn(bookings);
        when(bookingDAO.findByMailAndSectionAndSeat(
                validModifyRequest.getMail(),
                validModifyRequest.getFromSection(),
                validModifyRequest.getFromSeat()
        )).thenReturn(validBooking);
        when(bookingDAO.save(any(Booking.class))).thenAnswer(i -> i.getArgument(0));

        Booking modifiedBooking = bookingService.modifyTicket(validModifyRequest);

        assertNotNull(modifiedBooking);
        assertEquals("AC", modifiedBooking.getSection());
        assertEquals("2B", modifiedBooking.getSeat());
        assertEquals(30.00, modifiedBooking.getPrice());
    }
}
    

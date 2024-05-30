package com.assess.cloudbees.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.assess.cloudbees.DAO.BookingDAO;
import com.assess.cloudbees.DAO.TrainDAO;
import com.assess.cloudbees.bean.request.BookingRequest;
import com.assess.cloudbees.entity.Booking;
import com.assess.cloudbees.entity.Section;
import com.assess.cloudbees.entity.Train;
import com.assess.cloudbees.exception.InvalidBookingRequestException;

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
        existingBookings = Collections.emptyList(); // Assuming no existing bookings
    }

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
}
    

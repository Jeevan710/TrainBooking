package com.assess.cloudbees.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assess.cloudbees.DAO.BookingDAO;
import com.assess.cloudbees.DAO.TrainDAO;
import com.assess.cloudbees.bean.request.BookingRequest;
import com.assess.cloudbees.bean.request.ModifyRequest;
import com.assess.cloudbees.entity.Booking;
import com.assess.cloudbees.entity.Section;
import com.assess.cloudbees.entity.Train;
import com.assess.cloudbees.exception.InvalidBookingRequestException;
import com.assess.cloudbees.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class BookingServiceImpl implements BookingService{

	@Autowired
	private BookingDAO bookingDAO;
	@Autowired
	private TrainDAO trainDAO;
	
	@Override
	public Booking bookTicket(BookingRequest bookingRequest) {
		if(!(bookingRequest.getSection().equals("Sleeper") || bookingRequest.getSection().equals("AC")))
		{
			throw new InvalidBookingRequestException("Please select a valid section");
		}
		Train train=trainDAO.findByTrainNumber(bookingRequest.getTrainNumber());
		if(!(train!=null && train.getFrom().equals(bookingRequest.getFrom()) && train.getTo().equals(bookingRequest.getTo())))
		{
			throw new InvalidBookingRequestException("Please input valid train details");
		}
		List<Booking> bookings=bookingDAO.findAll();
		
		boolean isSeatAvailable=isSeatAvailable(bookingRequest.getSection(),bookingRequest.getSeat(),bookings);
		if (isSeatAvailable){
            Booking booking=getBookingDetails(bookingRequest,train);
            bookingDAO.save(booking);
            return booking;
        }else{
            throw  new InvalidBookingRequestException("Sorry, this seat is not available");
        }
	}
	
	@Override
	public List<Booking> getReceiptsByMail(String mail) {
		List<Booking> bookings=bookingDAO.findByMail(mail);
		if(bookings==null)
		{
			throw new ResourceNotFoundException("No bookings found");
		}
		return bookings;
	}
	
	@Transactional
	@Override
	public void cancelBooking(String mail) {
		bookingDAO.deleteByMail(mail);
	}
	
	@Override
	public List<Booking> getSectionDetails(String section) {
		if(!(section.equals("Sleeper") || section.equals("AC")))
		{
			throw new InvalidBookingRequestException("Please enter a valid section");
		}
		List<Booking> bookings=bookingDAO.findBySection(section);
		if(bookings==null)
		{
			throw new ResourceNotFoundException("No bookings found");
		}
		return bookings;
	}
	@Override
	public Booking modifyTicket(ModifyRequest modifyRequest) {
		if(!((modifyRequest.getFromSection().equals("Sleeper") || modifyRequest.getFromSection().equals("AC")) && 
				(modifyRequest.getToSection().equals("Sleeper") || modifyRequest.getToSection().equals("AC") ))){
			throw new InvalidBookingRequestException("Please enter a valid section");
		}
		Train train=trainDAO.findByTrainNumber(modifyRequest.getTrainNumber());
		if(train==null)
		{
			throw new ResourceNotFoundException("Enter a valid train");
		}
		List<Booking> bookings=bookingDAO.findAll();
		boolean isSeatAvailable=isSeatAvailable(modifyRequest.getToSection(),modifyRequest.getToSeat(),bookings);
		if (isSeatAvailable){
            Booking booking=bookingDAO.findByMailAndSectionAndSeat(modifyRequest.getMail(),modifyRequest.getFromSection(),modifyRequest.getFromSeat());
            booking.setSection(modifyRequest.getToSection());
            booking.setSeat(modifyRequest.getToSeat());
            booking.setPrice(getPriceDetails(modifyRequest.getToSection(),train));
            bookingDAO.save(booking);
            return booking;
        }else{
            throw  new InvalidBookingRequestException("Sorry, this seat is not available");
        }
	}
	
	private Booking getBookingDetails(BookingRequest bookingRequest,Train train) {
		Booking booking=new Booking();
		booking.setFrom(bookingRequest.getFrom());
		booking.setTo(bookingRequest.getTo());
		booking.setMail(bookingRequest.getMail());
		booking.setTrainNumber(bookingRequest.getTrainNumber());
		booking.setSeat(bookingRequest.getSeat());
		booking.setSection(bookingRequest.getSection());
		booking.setPrice(getPriceDetails(bookingRequest.getSection(),train));
		return booking;
	}

	private Double getPriceDetails(String section, Train train) {
		List<Section> sections=train.getSection();
		Double price=0.0;
		for(Section eachSection:sections)
		{
			if(eachSection.getName().equals(section))
			{
				price=eachSection.getPrice();
				break;
			}
		}
		return price;
	}

	private boolean isSeatAvailable(String section, String seat, List<Booking> bookings) {
		return bookings.stream()
                .noneMatch(booking ->
                        booking.getSection().equals(section) && booking.getSeat().equals(seat));
	}
	
}

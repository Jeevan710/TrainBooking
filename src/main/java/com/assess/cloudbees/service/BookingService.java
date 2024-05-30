package com.assess.cloudbees.service;

import java.util.List;

import com.assess.cloudbees.bean.request.BookingRequest;
import com.assess.cloudbees.bean.request.ModifyRequest;
import com.assess.cloudbees.entity.Booking;

public interface BookingService {

	Booking bookTicket(BookingRequest bookingRequest);

	List<Booking> getReceiptsByMail(String mail);

	void cancelBooking(String mail);

	List<Booking> getSectionDetails(String section);

	Booking modifyTicket(ModifyRequest modifyRequest);

}

package com.assess.cloudbees.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assess.cloudbees.bean.request.BookingRequest;
import com.assess.cloudbees.bean.request.ModifyRequest;
import com.assess.cloudbees.bean.response.ReceiptResponse;
import com.assess.cloudbees.bean.response.SectionResponse;
import com.assess.cloudbees.bean.response.UserResponse;
import com.assess.cloudbees.entity.Booking;
import com.assess.cloudbees.entity.User;
import com.assess.cloudbees.exception.InvalidBookingRequestException;
import com.assess.cloudbees.exception.ResourceNotFoundException;
import com.assess.cloudbees.service.BookingService;
import com.assess.cloudbees.service.UserService;

@RestController
@RequestMapping("/booking")
public class BookingController {
	@Autowired
	private BookingService bookingService;
	@Autowired
	private UserService userService;
	
	@PostMapping("/bookticket")
	public ResponseEntity<?> bookTickets(@RequestBody BookingRequest bookingRequest)
	{
		try{
			User user=userService.getUserByMail(bookingRequest.getMail());
			Booking booking=bookingService.bookTicket(bookingRequest);
			UserResponse userResponse = new UserResponse(user.getFirstName(),user.getLastName(),user.getMail());
			ReceiptResponse receiptResponse=getReceiptResponse(booking,userResponse);
            return ResponseEntity.ok(receiptResponse);

        }catch (InvalidBookingRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());  
        }
	}
	@GetMapping("/getReceipt/{mail}")
	public ResponseEntity<?> getReceipts(@PathVariable String mail)
	{
		try{
			User user=userService.getUserByMail(mail);
			List<Booking> bookings=bookingService.getReceiptsByMail(mail);
			
			UserResponse userResponse = new UserResponse(user.getFirstName(),user.getLastName(),user.getMail());
			List<ReceiptResponse> receipts=new ArrayList<>();
			for(Booking booking:bookings)
			{
				ReceiptResponse receipt=getReceiptResponse(booking,userResponse);
				receipts.add(receipt);
			}
            return ResponseEntity.ok(receipts);

        }catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());  
        }
	}

	@DeleteMapping("/deleteBooking/{mail}")
	public void deleteBooking(@PathVariable String mail)
	{
		bookingService.cancelBooking(mail);
	}
	@GetMapping("/getSection/{section}")
	public ResponseEntity<?> getSectionDetails(@PathVariable String section)
	{
		try{
			List<Booking> bookings=bookingService.getSectionDetails(section);
			List<SectionResponse> sectionResponses=new ArrayList<>();
			for(Booking booking:bookings)
			{
				User user=userService.getUserByMail(booking.getMail());
				SectionResponse sectionResponse=getSectionResponse(booking,user);
				sectionResponses.add(sectionResponse);
			}
			return ResponseEntity.ok(sectionResponses);
        }catch (InvalidBookingRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
		catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());  
        }
	}
	@PatchMapping("/modifyTicket")
	public ResponseEntity<?> modifyTicket(@RequestBody ModifyRequest modifyRequest)
	{
		try{
			User user=userService.getUserByMail(modifyRequest.getMail());
			Booking booking=bookingService.modifyTicket(modifyRequest);
			UserResponse userResponse = new UserResponse(user.getFirstName(),user.getLastName(),user.getMail());
			ReceiptResponse receiptResponse=getReceiptResponse(booking,userResponse);
            return ResponseEntity.ok(receiptResponse);
        }catch (InvalidBookingRequestException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());  
        }
	}
	private SectionResponse getSectionResponse(Booking booking, User user) {
		UserResponse userResponse=new UserResponse(user.getFirstName(),user.getLastName(),user.getMail());
		SectionResponse sectionResponse=new SectionResponse(userResponse,booking.getSeat());
		return sectionResponse;
	}
	private ReceiptResponse getReceiptResponse(Booking booking, UserResponse user) {
		ReceiptResponse receipt=new ReceiptResponse(booking.getFrom(),booking.getTo(),user,booking.getPrice());
		return receipt;
	}
}

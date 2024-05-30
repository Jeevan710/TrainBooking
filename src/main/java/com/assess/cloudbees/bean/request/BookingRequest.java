package com.assess.cloudbees.bean.request;

public class BookingRequest {
	private String mail;
	private String trainNumber;
	private String from;
	private String to;
	private String section;
	private String seat;
	public BookingRequest() {
		super();
	}
	public BookingRequest(String mail, String trainNumber, String from, String to, String section, String seat) {
		super();
		this.mail = mail;
		this.trainNumber = trainNumber;
		this.from = from;
		this.to = to;
		this.section = section;
		this.seat = seat;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getTrainNumber() {
		return trainNumber;
	}
	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	
	
}

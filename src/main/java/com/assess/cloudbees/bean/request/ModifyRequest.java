package com.assess.cloudbees.bean.request;

public class ModifyRequest {
	private String mail;
	private String trainNumber;
	private String fromSection;
	private String toSection;
	private String fromSeat;
	private String toSeat;
	public ModifyRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ModifyRequest(String mail, String trainNumber, String fromSection, String toSection, String fromSeat,
			String toSeat) {
		super();
		this.mail = mail;
		this.trainNumber = trainNumber;
		this.fromSection = fromSection;
		this.toSection = toSection;
		this.fromSeat = fromSeat;
		this.toSeat = toSeat;
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
	public String getFromSection() {
		return fromSection;
	}
	public void setFromSection(String fromSection) {
		this.fromSection = fromSection;
	}
	public String getToSection() {
		return toSection;
	}
	public void setToSection(String toSection) {
		this.toSection = toSection;
	}
	public String getFromSeat() {
		return fromSeat;
	}
	public void setFromSeat(String fromSeat) {
		this.fromSeat = fromSeat;
	}
	public String getToSeat() {
		return toSeat;
	}
	public void setToSeat(String toSeat) {
		this.toSeat = toSeat;
	}
	
}

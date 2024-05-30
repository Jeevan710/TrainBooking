package com.assess.cloudbees.bean.response;

public class ReceiptResponse {
	private String from;
	private String to;
	private UserResponse user;
	private Double price;
	public ReceiptResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ReceiptResponse(String from, String to, UserResponse user, Double price) {
		super();
		this.from = from;
		this.to = to;
		this.user = user;
		this.price = price;
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
	public UserResponse getUser() {
		return user;
	}
	public void setUser(UserResponse user) {
		this.user = user;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
}

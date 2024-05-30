package com.assess.cloudbees.bean.response;

public class SectionResponse {
	private UserResponse userResponse;
	private String seat;
	public SectionResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SectionResponse(UserResponse userResponse, String seat) {
		super();
		this.userResponse = userResponse;
		this.seat = seat;
	}
	public UserResponse getUserResponse() {
		return userResponse;
	}
	public void setUserResponse(UserResponse userResponse) {
		this.userResponse = userResponse;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	
}

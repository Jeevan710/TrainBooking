package com.assess.cloudbees.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Train {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String trainNumber;
	private String trainName;
	@Column(name="`from`")
	private String from;
	@Column(name="`to`")
	private String to;
	
	@OneToMany(mappedBy="train", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Section> section;

	public Train() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Train(String trainNumber, String trainName, String from, String to) {
		super();
		this.trainNumber = trainNumber;
		this.trainName = trainName;
		this.from = from;
		this.to = to;
	}
	
	public void addSection(Section section)
	{
		if(this.section==null)
		{
			this.section=new ArrayList<>();
		}
		this.section.add(section);
		section.setTrain(this);
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTrainNumber() {
		return trainNumber;
	}

	public void setTrainNumber(String trainNumber) {
		this.trainNumber = trainNumber;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
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

	public List<Section> getSection() {
		return section;
	}

	public void setSection(List<Section> section) {
		this.section = section;
	}
	
	
}

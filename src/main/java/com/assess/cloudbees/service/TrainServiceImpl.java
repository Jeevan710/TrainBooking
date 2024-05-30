package com.assess.cloudbees.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assess.cloudbees.DAO.SectionDAO;
import com.assess.cloudbees.DAO.TrainDAO;
import com.assess.cloudbees.entity.Section;
import com.assess.cloudbees.entity.Train;

@Service
public class TrainServiceImpl implements TrainService{
	@Autowired
	private TrainDAO trainDAO;
	@Autowired
	private SectionDAO sectionDAO;
	
	@Override
	public void addTrain() {
		Train train=new Train("123456","France Express","London","France");
		Section section1=new Section("Sleeper",20.00,"50");
		Section section2=new Section("AC",30.00,"20");
		trainDAO.save(train);
		train.addSection(section1);
		train.addSection(section2);
		sectionDAO.save(section1);
		sectionDAO.save(section2);
	}

	@Override
	public List<Train> getAllTrains() {
		return trainDAO.findAll();
	}

	@Override
	public Train getTrainByTrainNumber(String trainNumber) {
		return trainDAO.findByTrainNumber(trainNumber);
	}

}

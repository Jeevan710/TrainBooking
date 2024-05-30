package com.assess.cloudbees.service;

import java.util.List;

import com.assess.cloudbees.entity.Train;

public interface TrainService {

	void addTrain();

	List<Train> getAllTrains();

	Train getTrainByTrainNumber(String trainNumber);


}

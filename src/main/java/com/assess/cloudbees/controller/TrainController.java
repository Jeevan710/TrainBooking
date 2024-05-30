package com.assess.cloudbees.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assess.cloudbees.entity.Train;
import com.assess.cloudbees.service.TrainService;

@RestController
@RequestMapping("/train")
public class TrainController {
	@Autowired
	private TrainService trainService;
	
	@PostMapping("/addTrain")
	public void addTrain()
	{
		trainService.addTrain();
	}
	
	@GetMapping("/showtrains")
	public ResponseEntity<?> showAllTrains()
	{
		List<Train> trains=trainService.getAllTrains();
		return ResponseEntity.ok(trains);
	}
	
	@GetMapping("/showtrain/{trainNumber}")
	public ResponseEntity<?> showTrain(@PathVariable String trainNumber)
	{
		Train train=trainService.getTrainByTrainNumber(trainNumber);
		return ResponseEntity.ok(train);
	}
}

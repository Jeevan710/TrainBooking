package com.assess.cloudbees.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assess.cloudbees.entity.Train;

@Repository
public interface TrainDAO extends JpaRepository<Train,Long>{

	Train findByTrainNumber(String trainNumber);

}

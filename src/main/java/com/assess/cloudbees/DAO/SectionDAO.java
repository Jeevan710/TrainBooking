package com.assess.cloudbees.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assess.cloudbees.entity.Section;

@Repository
public interface SectionDAO extends JpaRepository<Section,Long>{

}

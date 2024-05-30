package com.assess.cloudbees.DAO;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.assess.cloudbees.entity.Booking;

@Repository
public interface BookingDAO extends JpaRepository<Booking,Long>{

	List<Booking> findByMail(String mail);

	void deleteByMail(String mail);

	List<Booking> findBySection(String section);

	@Query("SELECT b FROM Booking b WHERE b.mail = :mail AND b.section = :section AND b.seat = :seat")
	Booking findByMailAndSectionAndSeat(String mail, String section, String seat);

}

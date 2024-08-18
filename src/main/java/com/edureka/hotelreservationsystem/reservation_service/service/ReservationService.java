package com.edureka.hotelreservationsystem.reservation_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.edureka.hotelreservationsystem.reservation_service.entity.Reservation;
import com.edureka.hotelreservationsystem.reservation_service.repository.ReservationRepository;

public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;

	public Reservation createReservation(Reservation reservation) {
		return reservationRepository.save(reservation);
	}

	public Reservation getReservation(Long id) {
		return reservationRepository.findById(id).orElse(null);
	}

	public List<Reservation> getAllReservations() {
		return reservationRepository.findAll();
	}

	public Reservation getReservationById(Long id) {
		return reservationRepository.findById(id).orElse(null);
	}

	public Reservation addReservation(Reservation reservation) {
		return reservationRepository.save(reservation);

		// Add Logic to post to Kafka topic
		// event UserRegistered
	}

	public Reservation updateReservation(Long id, Reservation reservation) {
		if (reservationRepository.existsById(id)) {
			reservation.setId(id); // Ensure the correct ID is set for update
			return reservationRepository.save(reservation);
		} else {
			return null;
		}

		// Add Logic to post to Kafka topic
		// event UserUpdated
	}

	public boolean deleteReservation(Long id) {
		if (reservationRepository.existsById(id)) {
			reservationRepository.deleteById(id);
			return true;
		} else {
			return false;
		}
	}

}

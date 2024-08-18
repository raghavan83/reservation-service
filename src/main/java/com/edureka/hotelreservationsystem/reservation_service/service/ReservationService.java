package com.edureka.hotelreservationsystem.reservation_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.edureka.hotelreservationsystem.reservation_service.entity.Reservation;
import com.edureka.hotelreservationsystem.reservation_service.repository.ReservationRepository;

@Service
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private RestTemplate restTemplate;

	public Reservation createReservation(Reservation reservation) {

		// Check room availability with Hotel Management Service
		String url = "http://hotel-service/rooms/check-availability";
		Boolean isAvailable = restTemplate.postForObject(url, reservation, Boolean.class);

		if (Boolean.TRUE.equals(isAvailable)) {
			// Reserve the room
			reservation.setStatus("RESERVED");
			reservation = reservationRepository.save(reservation);

			// Send payment request to Payment Service
			String paymentUrl = "http://payment-service/payments/process";
			Boolean paymentStatus = restTemplate.postForObject(paymentUrl, reservation, Boolean.class);

			if (Boolean.TRUE.equals(paymentStatus)) {
				reservation.setStatus("CONFIRMED");
			} else {
				reservation.setStatus("PAYMENT_FAILED");
			}

			reservationRepository.save(reservation);

			// Notify customer via Notification Service
			// String notificationUrl = "http://notification-service/notifications/send";
			// restTemplate.postForObject(notificationUrl, reservation, Void.class);
		} else {
			reservation.setStatus("UNAVAILABLE");
		}

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

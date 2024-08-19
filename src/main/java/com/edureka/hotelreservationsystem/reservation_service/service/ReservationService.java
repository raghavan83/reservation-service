package com.edureka.hotelreservationsystem.reservation_service.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.edureka.hotelreservationsystem.reservation_service.dto.Payment;
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
		String url = "http://HOTEL-SERVICE/api/v1/hotels/check-availability?roomId=" + reservation.getRoomId();
		Boolean isAvailable = restTemplate.postForObject(url, reservation, Boolean.class);

		if (Boolean.TRUE.equals(isAvailable)) {
			// Reserve the room
			reservation.setStatus("RESERVED");
			reservation = reservationRepository.save(reservation);

			// Send payment request to Payment Service
			String paymentUrl = "http://PAYMENT-SERVICE/api/v1/payments";
			
			//Create payment details
			Payment payment = new Payment();
			payment.setCustomerId(reservation.getCustomerId());
			payment.setAmount(BigDecimal.valueOf(1000.00));
			payment.setPaymentDate(LocalDateTime.now());
			payment.setReservationId(reservation.getId());
			payment.setStatus("COMPLETED");
			
			Payment paymentResponse = restTemplate.postForObject(paymentUrl, payment, Payment.class);

			if (paymentResponse != null) {
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

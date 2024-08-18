package com.edureka.hotelreservationsystem.reservation_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edureka.hotelreservationsystem.reservation_service.entity.Reservation;
import com.edureka.hotelreservationsystem.reservation_service.service.ReservationService;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

	@Autowired
	ReservationService reservationService;

	@PostMapping
	public ResponseEntity<Reservation> makeReservation(@RequestBody Reservation reservation) {
		return ResponseEntity.ok(this.reservationService.createReservation(reservation));
	}

	// Retrieve all reservations
	@GetMapping
	public ResponseEntity<List<Reservation>> getAllReservations() {
		List<Reservation> reservations = reservationService.getAllReservations();
		return new ResponseEntity<>(reservations, HttpStatus.OK);
	}

	// Get a Reservation by ID
	@GetMapping("/{id}")
	public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
		Reservation reservation = reservationService.getReservationById(id);
		if (reservation != null) {
			return new ResponseEntity<>(reservation, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Update a Reservation
	@PutMapping("/{id}")
	public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
		Reservation updatedReservation = reservationService.updateReservation(id, reservation);
		if (updatedReservation != null) {
			return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Delete a Reservation
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
		boolean deleted = reservationService.deleteReservation(id);
		if (deleted) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}

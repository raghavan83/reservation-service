package com.edureka.hotelreservationsystem.reservation_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edureka.hotelreservationsystem.reservation_service.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{

}

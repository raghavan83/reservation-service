package com.edureka.hotelreservationsystem.reservation_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public class Payment {
	
	private Long id;	
	private Long customerId;
	private BigDecimal amount;
	private Long reservationId;
    private String status;
    private LocalDateTime paymentDate;

	public Payment() {
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the customerId
	 */
	public Long getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the reservationId
	 */
	public Long getReservationId() {
		return reservationId;
	}

	/**
	 * @param reservationId the reservationId to set
	 */
	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the paymentDate
	 */
	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	/**
	 * @param paymentDate the paymentDate to set
	 */
	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	

	// Additional fields such as payment method, status etc.

}

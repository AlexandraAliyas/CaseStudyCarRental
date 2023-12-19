package com.entity;

import java.util.Date;

/**
 * The {@code Payment} class represents a payment made in a car rental system
 * for a specific lease. It contains information about the payment, including
 * its identification, associated lease, payment date, and amount.
 */
public class Payment {

    /**
     * The unique identifier for the payment.
     */
    private int paymentID;

    /**
     * The identifier of the associated lease for which the payment is made.
     */
    private int leaseID;

    /**
     * The date when the payment was made.
     */
    private Date paymentDate;

    /**
     * The amount of the payment.
     */
    private double amount;

    /**
     * Constructs a new Payment object with the specified parameters.
     *
     * @param paymentID   The unique identifier for the payment.
     * @param leaseID     The identifier of the associated lease.
     * @param paymentDate The date when the payment was made.
     * @param amount      The amount of the payment.
     */
    public Payment(int paymentID, int leaseID, Date paymentDate, double amount) {
        this.paymentID = paymentID;
        this.leaseID = leaseID;
        this.paymentDate = paymentDate;
        this.amount = amount;
    }

    /**
     * Retrieves the unique identifier for the payment.
     *
     * @return The payment ID.
     */
    public int getPaymentID() {
        return paymentID;
    }

    /**
     * Sets the unique identifier for the payment.
     *
     * @param paymentID The payment ID to set.
     */
    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    /**
     * Retrieves the identifier of the associated lease.
     *
     * @return The lease ID.
     */
    public int getLeaseID() {
        return leaseID;
    }

    /**
     * Sets the identifier of the associated lease.
     *
     * @param leaseID The lease ID to set.
     */
    public void setLeaseID(int leaseID) {
        this.leaseID = leaseID;
    }

    /**
     * Retrieves the date when the payment was made.
     *
     * @return The payment date.
     */
    public Date getPaymentDate() {
        return paymentDate;
    }

    /**
     * Sets the date when the payment was made.
     *
     * @param paymentDate The payment date to set.
     */
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * Retrieves the amount of the payment.
     *
     * @return The payment amount.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the payment.
     *
     * @param amount The payment amount to set.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Returns a string representation of the Payment object.
     *
     * @return A string representation of the Payment object.
     */
    @Override
    public String toString() {
        return "Payment{" +
                "paymentID=" + paymentID +
                ", leaseID=" + leaseID +
                ", paymentDate=" + paymentDate +
                ", amount=" + amount +
                '}';
    }
}

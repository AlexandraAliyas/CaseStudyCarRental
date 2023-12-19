package com.dao;

import com.entity.Car;
import com.entity.Customer;
import com.entity.Lease;
import com.entity.Payment;
import com.exception.CarAlreadyExistsException;
import com.exception.CarNotFoundException;
import com.exception.CustomerAlreadyExistsException;
import com.exception.CustomerNotFoundException;
import com.exception.LeaseNotFoundException;

import java.util.Date;
import java.util.List;

/**
 * The {@code ICarLeaseRepository} interface defines methods for managing cars, customers, leases,
 * and payments in a car rental system.
 */
public interface ICarLeaseRepository {

    /**
     * Adds a new car to the repository.
     *
     * @param car The car to be added.
     * @throws CarAlreadyExistsException If a car with the same ID already exists in the repository.
     */
    void addCar(Car car) throws CarAlreadyExistsException;

    /**
     * Removes a car from the repository based on its ID.
     *
     * @param carID The ID of the car to be removed.
     */
    void removeCar(int carID);

    /**
     * Retrieves a list of available cars in the repository.
     *
     * @return List of available cars.
     */
    List<Car> listAvailableCars();

    /**
     * Retrieves a list of rented cars in the repository.
     *
     * @return List of rented cars.
     */
    List<Car> listRentedCars();

    /**
     * Finds and returns a car based on its ID.
     *
     * @param carID The ID of the car to find.
     * @return The found car.
     * @throws CarNotFoundException If the specified car ID is not found in the repository.
     */
    Car findCarById(int carID) throws CarNotFoundException;

    /**
     * Adds a new customer to the repository.
     *
     * @param customer The customer to be added.
     * @throws CustomerAlreadyExistsException If a customer with the same ID already exists in the repository.
     */
    void addCustomer(Customer customer) throws CustomerAlreadyExistsException;

    /**
     * Removes a customer from the repository based on their ID.
     *
     * @param customerID The ID of the customer to be removed.
     * @throws CustomerNotFoundException If the specified customer ID is not found in the repository.
     */
    void removeCustomer(int customerID) throws CustomerNotFoundException;

    /**
     * Retrieves a list of all customers in the repository.
     *
     * @return List of customers.
     */
    List<Customer> listCustomers();

    /**
     * Finds and returns a customer based on their ID.
     *
     * @param customerID The ID of the customer to find.
     * @return The found customer.
     * @throws CustomerNotFoundException If the specified customer ID is not found in the repository.
     */
    Customer findCustomerById(int customerID) throws CustomerNotFoundException;

    /**
     * Creates a new lease for a specified customer and car with given start and end dates.
     *
     * @param customerID The ID of the customer.
     * @param carID      The ID of the car.
     * @param startDate  The start date of the lease.
     * @param endDate    The end date of the lease.
     * @return The created lease.
     */
    public Lease createLease(int customerID, int carID, Date startDate, Date endDate) throws LeaseNotFoundException, CarNotFoundException, CustomerNotFoundException ;

    /**
     * Marks a lease as returned based on its ID.
     *
     * @param leaseID The ID of the lease to be marked as returned.
     * @return The returned lease.
     * @throws LeaseNotFoundException If the specified lease ID is not found in the repository.
     */
    public Lease returnCar(int leaseID) throws LeaseNotFoundException;

    /**
     * Retrieves a list of all active leases in the repository.
     *
     * @return List of active leases.
     */
    public List<Lease> listActiveLeases() throws CarNotFoundException, CustomerNotFoundException ;

    /**
     * Retrieves a list of all lease history in the repository.
     *
     * @return List of lease history.
     */
    List<Lease> listLeaseHistory() throws CarNotFoundException, CustomerNotFoundException;

    /**
     * Finds and returns a lease based on its ID.
     *
     * @param leaseID The ID of the lease to find.
     * @return The found lease.
     * @throws LeaseNotFoundException If the specified lease ID is not found in the repository.
     */
    Lease findLeaseById(int leaseID)  throws LeaseNotFoundException;

    /**
     * Records a payment for a specified lease with the given amount.
     *
     * @param lease  The lease for which the payment is recorded.
     * @param amount The amount of the payment.
     */
    void recordPayment(Lease lease, double amount);

    /**
     * Retrieves the payment history for a specified customer.
     *
     * @param customerID The ID of the customer.
     * @return List of payment history.
     */
    List<Payment> retrievePaymentHistory(int customerID);

    /**
     * Calculates the total revenue generated from all payments.
     *
     * @return The total revenue.
     */
    double calculateTotalRevenue();
}

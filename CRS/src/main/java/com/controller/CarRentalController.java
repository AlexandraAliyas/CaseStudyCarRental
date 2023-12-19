// Import statements for required classes
package com.controller;

import com.dao.*;
import com.entity.*;
import com.exception.*;

import java.util.Date;
import java.util.List;

/**
 * Controller class for managing car rental operations.
 */
public class CarRentalController {
    private ICarLeaseRepository carLeaseRepository;

    /**
     * Constructor to initialize the car lease repository.
     */
    public CarRentalController() {
        this.carLeaseRepository = new ICarLeaseRepositoryImpl();
    }

    /**
     * Adds a new car to the system.
     *
     * @param car The car to be added.
     */
    public void addCar(Car car) {
        try {
            carLeaseRepository.addCar(car);
            System.out.println("Car added successfully.");
        } catch (Exception e) {
            handleException(e);
        }
    }



    /**
     * Removes a car from the system.
     *
     * @param carID The ID of the car to be removed.
     */
    public void removeCar(int carID) {
        try {
            carLeaseRepository.removeCar(carID);
            System.out.println("Car removed successfully.");
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Lists all available cars in the system.
     *
     * @return List of available cars.
     */
    public List<Car> listAvailableCars() {
        try {
            return carLeaseRepository.listAvailableCars();
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }


    /**
     * Adds a new customer to the system.
     *
     * @param customer The customer to be added.
     */
    public void addCustomer(Customer customer) {
        try {
            carLeaseRepository.addCustomer(customer);
            System.out.println("Customer added successfully.");
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Removes a customer from the system.
     *
     * @param customerID The ID of the customer to be removed.
     */
    public void removeCustomer(int customerID) {
        try {
            carLeaseRepository.removeCustomer(customerID);
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Lists all customers in the system.
     *
     * @return List of customers.
     */
    public List<Customer> listCustomers() {
        try {
            return carLeaseRepository.listCustomers();
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    /**
     * Finds a lease by its ID.
     *
     * @param leaseID The ID of the lease to be found.
     * @return The found lease.
     * @throws LeaseNotFoundException If the lease with the specified ID is not found.
     * @throws CustomerNotFoundException 
     * @throws CarNotFoundException 
     */
  
    public Lease findLeaseById(int leaseID) throws  LeaseNotFoundException {
        try {
            Lease lease= carLeaseRepository.findLeaseById(leaseID);
            if(lease==null) {
            	throw new CarNotFoundException("lease with this lease id not found");
            }
            
        } catch (CarNotFoundException e) {
            handleException(e);
        }
		return null;
    }
    /**
     * Creates a new lease in the system.
     *
     * @param customerID The ID of the customer leasing the car.
     * @param carID      The ID of the leased car.
     * @param startDate  The start date of the lease.
     * @param endDate    The end date of the lease.
     * @return The created lease.
     */
    public Lease createLease(int customerID, int carID, Date startDate, Date endDate) {
        try {
            return carLeaseRepository.createLease(customerID, carID, startDate, endDate);
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    /**
     * Handles the return of a leased car.
     *
     * @param leaseID The ID of the lease for the returned car.
     * @return The lease object representing the returned car.
     * @throws LeaseNotFoundException If the lease with the specified ID is not found.
     * @throws CustomerNotFoundException 
     * @throws CarNotFoundException 
     */
    public Lease returnCar(int leaseID) throws LeaseNotFoundException {
        try {
            Lease lease= carLeaseRepository.returnCar(leaseID);;
            if(lease==null) {
            	throw new LeaseNotFoundException("customer with this customerId not found");
            }
            
        } catch (LeaseNotFoundException e) {
            handleException(e);
        }
		return null;
    }

    /**
     * Finds a customer by ID.
     *
     * @param customerId The ID of the customer to be found.
     * @return The found customer.
     * @throws CustomerNotFoundException If the customer with the specified ID is not found.
     */
    public Customer findCustomerById(int customerId)  {
        try {
            Customer cust= carLeaseRepository.findCustomerById(customerId);
            if(cust==null) {
            	throw new CustomerNotFoundException("customer with this customerId not found");
            }
            
        } catch (CustomerNotFoundException e) {
            handleException(e);
        }
		return null;
    }

    /**
     * Lists all rented cars in the system.
     *
     * @return List of rented cars.
     */
    public List<Car> listRentedCars() {
        List<Car> rentedCars = carLeaseRepository.listRentedCars();
        return rentedCars;
    }

    /**
     * Finds a car by its ID.
     *
     * @param carID The ID of the car to be found.
     * @return The found car.
     * @throws CarNotFoundException If the car with the specified ID is not found.
     */
   
    public Car findCarById(int carID) throws CarNotFoundException {
        try {
            Car car= carLeaseRepository.findCarById(carID);
            if(car==null) {
            	throw new CarNotFoundException("car with this car id not found");
            }
            
        } catch (CarNotFoundException e) {
            handleException(e);
        }
		return null;
    }

    /**
     * Lists all active leases in the system.
     *
     * @return List of active leases.
     */
    public List<Lease> listActiveLeases() {
        try {
            return carLeaseRepository.listActiveLeases();
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    /**
     * Records a payment for a lease.
     *
     * @param lease  The leased item for which the payment is recorded.
     * @param amount The payment amount.
     */
    public void recordPayment(Lease lease, double amount) {
        try {
            carLeaseRepository.recordPayment(lease, amount);
            System.out.println("Payment recorded successfully.");
        } catch (Exception e) {
            handleException(e);
        }
    }

    /**
     * Retrieves the payment history for a customer.
     *
     * @param customerID The ID of the customer for whom the payment history is retrieved.
     * @return List of payments in the history.
     */
    public List<Payment> retrievePaymentHistory(int customerID) {
        try {
            return carLeaseRepository.retrievePaymentHistory(customerID);
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    /**
     * Calculates the total revenue generated from payments.
     *
     * @return The total revenue.
     */
    public double calculateTotalRevenue() {
        try {
            return carLeaseRepository.calculateTotalRevenue();
        } catch (Exception e) {
            handleException(e);
            return 0.0;
        }
    }

    /**
     * Lists the entire lease history in the system.
     *
     * @return List of all leases in the history.
     */
    public List<Lease> listLeaseHistory() {
        try {
            return ((ICarLeaseRepositoryImpl) carLeaseRepository).listLeaseHistory();
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    /**
     * Handles exceptions by logging or performing any necessary handling.
     *
     * @param e The exception that occurred.
     */
    private void handleException(Exception e) {
        System.out.println("Error occured: " + e);
    }
}

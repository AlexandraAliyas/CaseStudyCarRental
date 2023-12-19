package com.test;

import static org.junit.Assert.*;
import com.controller.CarRentalController;
import com.entity.Car;
import com.entity.Lease;
import com.exception.CarNotFoundException;
import com.exception.CustomerNotFoundException;
import com.exception.LeaseNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;


/**
 * This class contains JUnit tests for the CarRentalController class.
 * It covers various functionalities such as adding cars, creating leases,
 * and handling exceptions for customer, car, and lease not found scenarios.
 */
public class CarRentalControllerTest {
    private CarRentalController carRentalController;

    /**
     * Setup method executed before each test.
     * Initializes the CarRentalController instance.
     */
    @Before
    public void setUp() {
        carRentalController = new CarRentalController();
    }

    /**
     * Test if a car is added successfully to the list of available cars.
     */
    @Test
    public void testAddCar() {
        Car car = new Car(0, "xyz", "abc", 2023, 100.0, "available", 5, 2000);

        carRentalController.addCar(car);
        System.out.println("Car to add: " + car);

        List<Car> availableCars = carRentalController.listAvailableCars();
        System.out.println("Available Cars: " + availableCars);

        assertTrue(availableCars.contains(car));
    }

    /**
     * Test if a lease is retrieved successfully by its ID.
     *
     * @throws LeaseNotFoundException if the lease is not found.
     * @throws CustomerNotFoundException 
     * @throws CarNotFoundException 
     */
    @Test
    public void testRetrieveLease() throws LeaseNotFoundException, CarNotFoundException, CustomerNotFoundException {
        int leaseID = 1;

        Lease lease = carRentalController.findLeaseById(leaseID);

        // Verify that the retrieved lease is not null
        assertNotNull(lease);
    }

    /**
     * Test if a CustomerNotFoundException is thrown when a customer is not found.
     *
     * @throws CustomerNotFoundException if the customer is not found.
     */
    @Test(expected = CustomerNotFoundException.class)
    public void testCustomerNotFoundException() throws CustomerNotFoundException {
        int customerID = 101;

        carRentalController.findCustomerById(customerID);
    }

    /**
     * Test if a CarNotFoundException is thrown when a car is not found.
     *
     * @throws CarNotFoundException if the car is not found.
     */
    @Test(expected = CarNotFoundException.class)
    public void testCarNotFoundException() throws CarNotFoundException {
        int vehicleID = 1;

        carRentalController.findCarById(vehicleID);
    }

    /**
     * Test if a LeaseNotFoundException is thrown when a lease is not found.
     *
     * @throws LeaseNotFoundException if the lease is not found.
     * @throws CustomerNotFoundException 
     * @throws CarNotFoundException 
     */
    @Test(expected = LeaseNotFoundException.class)
    public void testLeaseNotFoundException() throws LeaseNotFoundException, CarNotFoundException, CustomerNotFoundException {
        int leaseID = 123;

        carRentalController.findLeaseById(leaseID);
    }
}

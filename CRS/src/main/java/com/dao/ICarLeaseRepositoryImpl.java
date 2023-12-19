package com.dao;

import com.entity.*;

import com.exception.*;

import com.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Implementation of the ICarLeaseRepository interface for managing car leasing operations.
 */

public class ICarLeaseRepositoryImpl implements ICarLeaseRepository {
    private Connection connection;

    /**
     * Constructs a new ICarLeaseRepositoryImpl and initializes the database connection.
     */
    public ICarLeaseRepositoryImpl() {
        this.connection = DBConnection.getConnection();
    }

    // Car Management
    
    /**
     * Adds a new car to the database.
     *
     * @param car The car entity to be added.
     * @throws CarAlreadyExistsException If a car with the same details already exists.
     */
    @Override
    public void addCar(Car car) throws CarAlreadyExistsException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConnection.getConnection();

            String insertQuery = "INSERT INTO vehicle (make, model, year, dailyRate, status, passengerCapacity, engineCapacity) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

            // Set values for the parameters in the INSERT statement
            preparedStatement.setString(1, car.getMake());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setInt(3, car.getYear());
            preparedStatement.setDouble(4, car.getDailyRate());
            preparedStatement.setString(5, car.getStatus());
            preparedStatement.setInt(6, car.getPassengerCapacity());
            preparedStatement.setInt(7, car.getEngineCapacity());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating car failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    car.setVehicleID(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating car failed, no ID obtained.");
                }
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new CarAlreadyExistsException("Car with ID " + car.getVehicleID() + " already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding car", e);
        } 
    }
    /**
     * Removes a car from the database based on its vehicle ID.
     *
     * @param vehicleID The ID of the car to be removed.
     */
    @Override
    public void removeCar(int vehicleID) {
        String sql = "DELETE FROM Vehicle WHERE vehicleID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, vehicleID);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }


    /**
     * Retrieves a list of available cars from the database.
     *
     * @return A list of available cars.
     */
    @Override
    public List<Car> listAvailableCars() {
        List<Car> availableCars = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Vehicle WHERE status = 'available'");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                availableCars.add(mapResultSetToCar(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return availableCars;
    }

    /**
     * Retrieves a list of rented cars from the database.
     *
     * @return A list of rented cars.
     */
    @Override
    public List<Car> listRentedCars() {
        List<Car> rentedCars = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Vehicle WHERE status = 'notAvailable'");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                rentedCars.add(mapResultSetToCar(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return rentedCars;
    }

    /**
     * Finds and retrieves a car based on its vehicle ID.
     *
     * @param vehicleID The ID of the car to be retrieved.
     * @return The car entity.
     * @throws CarNotFoundException If the specified car is not found.
     */
    
    @Override
    public Car findCarById(int vehicleID) throws CarNotFoundException {
        String sql = "SELECT * FROM Vehicle WHERE vehicleID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, vehicleID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToCar(resultSet);
            } else {
                throw new CarNotFoundException("Car with ID " + vehicleID + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
            throw new RuntimeException("An error occurred while finding the car.");
        }
    }

    // Customer Management

    /**
     * Adds a new customer to the database.
     *
     * @param customer The customer entity to be added.
     * @throws CustomerAlreadyExistsException If a customer with the same details already exists.
     */
    
    @Override
    public void addCustomer(Customer customer) throws CustomerAlreadyExistsException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConnection.getConnection();

            String insertQuery = "INSERT INTO customer (customerID, firstName, lastName, email, phoneNumber) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setInt(1, customer.getCustomerID());
            preparedStatement.setString(2, customer.getFirstName());
            preparedStatement.setString(3, customer.getLastName());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setString(5, customer.getPhoneNumber());

            preparedStatement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new CustomerAlreadyExistsException("Customer with ID " + customer.getCustomerID() + " already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding customer", e);
        }
    }

    /**
     * Removes a customer from the database based on their customer ID.
     *
     * @param customerID The ID of the customer to be removed.
     * @throws CustomerNotFoundException If the specified customer is not found.
     */
    
    @Override
    public void removeCustomer(int customerID) throws CustomerNotFoundException {
        String checkCustomerSql = "SELECT * FROM Customer WHERE customerID = ?";
        String deleteCustomerSql = "DELETE FROM Customer WHERE customerID = ?";

        try (PreparedStatement checkStatement = connection.prepareStatement(checkCustomerSql)) {
            checkStatement.setInt(1, customerID);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                // Customer exists, proceed with deletion
                try (PreparedStatement deleteStatement = connection.prepareStatement(deleteCustomerSql)) {
                    deleteStatement.setInt(1, customerID);
                    deleteStatement.executeUpdate();
                    System.out.println("Customer removed successfully.");
                } catch (SQLException e) {
                    System.err.println("Error removing customer: " + e.getMessage());
                }
            } else {
                // Customer does not exist
                throw new CustomerNotFoundException("Customer with ID " + customerID + " not found.");
            }
        } catch (SQLException e) {
            System.err.println("Error checking customer existence: " + e.getMessage());
        }
    }


    /**
     * Retrieves a list of all customers from the database.
     *
     * @return A list of customers.
     */
    
    @Override
    public List<Customer> listCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Customer");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                customers.add(mapResultSetToCustomer(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return customers;
    }

    /**
     * Finds and retrieves a customer based on their customer ID.
     *
     * @param customerID The ID of the customer to be retrieved.
     * @return The customer entity.
     * @throws CustomerNotFoundException If the specified customer is not found.
     */
    @Override
    public Customer findCustomerById(int customerID) throws CustomerNotFoundException {
        String sql = "SELECT * FROM Customer WHERE customerID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToCustomer(resultSet);
            } else {
                throw new CustomerNotFoundException("Customer with ID " + customerID + " not found.");
            }
        } catch (SQLException e) {
            // Handle SQLException
            System.err.println("Error retrieving customer information: " + e.getMessage());
            throw new CustomerNotFoundException("Customer with ID " + customerID + " not found.");
        }
    }


    // Lease Management

    /**
     * Creates a new lease in the database.
     *
     * @param customerID The ID of the customer leasing the car.
     * @param carID      The ID of the leased car.
     * @param startDate  The start date of the lease.
     * @param endDate    The end date of the lease.
     * @return The created Lease entity.
     */
    @Override
    public Lease createLease(int customerID, int carID, Date startDate, Date endDate) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConnection.getConnection();

            String insertQuery = "INSERT INTO lease (leaseID, vehicleID, customerID, startDate, endDate, type) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setInt(1, getNextLeaseID()); 
            preparedStatement.setInt(2, carID);
            preparedStatement.setInt(3, customerID);
            preparedStatement.setDate(4, new java.sql.Date(startDate.getTime()));
            preparedStatement.setDate(5, new java.sql.Date(endDate.getTime()));
            preparedStatement.setString(6, "Daily");

            preparedStatement.executeUpdate();

            return new Lease(getNextLeaseID(), carID, customerID, startDate, endDate, "Daily"); // Replace with your logic
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
            throw new RuntimeException("Error creating lease", e);
        } 
    }

    /**
     * Retrieves the next unique leaseID by querying the maximum existing leaseID in the database.
     * If there are existing records, the method increments the maximum leaseID by 1.
     * If there are no existing records, it returns 1 as the starting leaseID.
     *
     * @return The next unique leaseID.
     * @throws RuntimeException If there is an error getting the next leaseID from the database.
     */
    
    private int getNextLeaseID() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();

            String query = "SELECT MAX(leaseID) FROM lease";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) + 1;
            } else {
                return 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception
            throw new RuntimeException("Error getting next leaseID", e);
        } 
    }


    /**
     * Returns a leased car based on the lease ID.
     *
     * @param leaseID The ID of the lease to be returned.
     * @return The returned Lease entity.
     * @throws LeaseNotFoundException If the specified lease is not found.
     */
    @Override
    public Lease returnCar(int leaseID) throws LeaseNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();

            String query = "SELECT * FROM lease WHERE leaseID = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, leaseID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Lease returnedLease = mapResultSetToLease(resultSet);
                return returnedLease;
            } else {
                throw new LeaseNotFoundException("Lease not found with ID: " + leaseID);
            }
        } catch (SQLException e) {
            // Handle SQLException
            e.printStackTrace();
            throw new RuntimeException("Error retrieving lease information", e);
        } 
    }
    
    /**
     * Retrieves a list of active leases from the database.
     *
     * @return A list of active leases.
     */
    @Override
    public List<Lease> listActiveLeases() {
        List<Lease> activeLeases = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Lease WHERE endDate >= CURRENT_DATE");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                activeLeases.add(mapResultSetToLease(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return activeLeases;
    }
    @Override
    public Lease findLeaseById(int leaseID) throws LeaseNotFoundException {
        String sql = "SELECT * FROM Lease WHERE leaseID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, leaseID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSetToLease(resultSet);
                } else {
                    throw new LeaseNotFoundException("Lease not found with ID: " + leaseID);
                }
            }
        } catch (SQLException e) {
            // Handle SQLException
            System.err.println("Error retrieving lease information: " + e.getMessage());
            throw new RuntimeException("Error retrieving lease information", e);
        }
    }

    
    /**
     * Retrieves a list of all leases from the database.
     *
     * @return A list of all leases.
     */
    
    @Override
    public List<Lease> listLeaseHistory() {
        List<Lease> leaseHistory = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM Lease");
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                leaseHistory.add(mapResultSetToLease(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return leaseHistory;
    }

    // Payment Handling

    /**
     * Records a payment for a lease in the database.
     *
     * @param lease  The Lease entity for which the payment is recorded.
     * @param amount The amount of the payment.
     */
    
    @Override
    public void recordPayment(Lease lease, double amount) {
        String sql = "INSERT INTO Payment (leaseID, paymentDate, amount) VALUES (?, CURRENT_DATE, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, lease.getLeaseID());
            statement.setDouble(2, amount);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    /**
     * Retrieves the payment history for a customer from the database.
     *
     * @param customerID The ID of the customer for whom the payment history is retrieved.
     * @return A list of Payment entities representing the payment history.
     */
    @Override
    public List<Payment> retrievePaymentHistory(int customerID) {
        List<Payment> paymentHistory = new ArrayList<>();
        String sql = "SELECT * FROM Payment p JOIN Lease l ON p.leaseID = l.leaseID WHERE l.customerID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customerID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                paymentHistory.add(mapResultSetToPayment(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return paymentHistory;
    }

    /**
     * Calculates the total revenue from payments in the database.
     *
     * @return The total revenue.
     */
    @Override
    public double calculateTotalRevenue() {
        double totalRevenue = 0;
        String sql = "SELECT SUM(amount) AS total FROM Payment";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                totalRevenue = resultSet.getDouble("total");
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        return totalRevenue;
    }

    // Helper methods to map ResultSets to entities
    
    /**
     * Maps a ResultSet to a Car entity.
     *
     * @param resultSet The ResultSet containing car information.
     * @return The mapped Car entity.
     * @throws SQLException If a database access error occurs.
     */

    private Car mapResultSetToCar(ResultSet resultSet) throws SQLException {
        return new Car(
                resultSet.getInt("vehicleID"),
                resultSet.getString("make"),
                resultSet.getString("model"),
                resultSet.getInt("year"),
                resultSet.getDouble("dailyRate"),
                resultSet.getString("status"),
                resultSet.getInt("passengerCapacity"),
                resultSet.getInt("engineCapacity")
        );
    }

    /**
     * Maps a ResultSet to a Customer entity.
     *
     * @param resultSet The ResultSet containing customer information.
     * @return The mapped Customer entity.
     * @throws SQLException If a database access error occurs.
     */
    private Customer mapResultSetToCustomer(ResultSet resultSet) throws SQLException {
        return new Customer(
                resultSet.getInt("customerID"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getString("email"),
                resultSet.getString("phoneNumber")
        );
    }

    /**
     * Maps a ResultSet to a Lease entity.
     *
     * @param resultSet The ResultSet containing lease information.
     * @return The mapped Lease entity.
     * @throws SQLException If a database access error occurs.
     */
    private Lease mapResultSetToLease(ResultSet resultSet) throws SQLException {
        return new Lease(
                resultSet.getInt("leaseID"),
                resultSet.getInt("vehicleID"),
                resultSet.getInt("customerID"),
                resultSet.getDate("startDate"),
                resultSet.getDate("endDate"),
                resultSet.getString("type")
        );
    }

    /**
     * Maps a ResultSet to a Payment entity.
     *
     * @param resultSet The ResultSet containing payment information.
     * @return The mapped Payment entity.
     * @throws SQLException If a database access error occurs.
     */
    private Payment mapResultSetToPayment(ResultSet resultSet) throws SQLException {
        return new Payment(
                resultSet.getInt("paymentID"),
                resultSet.getInt("leaseID"),
                resultSet.getDate("paymentDate"),
                resultSet.getDouble("amount")
        );
    }
    
}

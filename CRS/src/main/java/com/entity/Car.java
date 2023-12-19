package com.entity;

import java.util.Objects;

/**
 * The {@code Car} class represents a vehicle in a car rental system.
 * It encapsulates information about a car, including its identification,
 * make, model, manufacturing year, daily rental rate, status, passenger capacity,
 * and engine capacity.
 */
public class Car {

    /**
     * The unique identifier for the car.
     */
    private int vehicleID;

    /**
     * The make (brand) of the car.
     */
    private String make;

    /**
     * The model of the car.
     */
    private String model;

    /**
     * The manufacturing year of the car.
     */
    private int year;

    /**
     * The daily rental rate for the car.
     */
    private double dailyRate;

    /**
     * The status of the car (e.g., available, rented).
     */
    private String status;

    /**
     * The passenger capacity of the car.
     */
    private int passengerCapacity;

    /**
     * The engine capacity of the car.
     */
    private int engineCapacity;

    /**
     * Constructs a new Car object with the specified parameters.
     *
     * @param vehicleID         The unique identifier for the car.
     * @param make              The make (brand) of the car.
     * @param model             The model of the car.
     * @param year              The manufacturing year of the car.
     * @param dailyRate         The daily rental rate for the car.
     * @param status            The status of the car (e.g., available, rented).
     * @param passengerCapacity The passenger capacity of the car.
     * @param engineCapacity    The engine capacity of the car.
     */
    public Car(int vehicleID, String make, String model, int year, double dailyRate, String status, int passengerCapacity, int engineCapacity) {
        this.vehicleID = vehicleID;
        this.make = make;
        this.model = model;
        this.year = year;
        this.dailyRate = dailyRate;
        this.status = status;
        this.passengerCapacity = passengerCapacity;
        this.engineCapacity = engineCapacity;
    }

    /**
     * Retrieves the unique identifier for the car.
     *
     * @return The vehicle ID.
     */
    public int getVehicleID() {
        return vehicleID;
    }

    /**
     * Sets the unique identifier for the car.
     *
     * @param vehicleID The vehicle ID to set.
     */
    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    /**
     * Retrieves the make (brand) of the car.
     *
     * @return The make of the car.
     */
    public String getMake() {
        return make;
    }

    /**
     * Sets the make (brand) of the car.
     *
     * @param make The make to set.
     */
    public void setMake(String make) {
        this.make = make;
    }

    /**
     * Retrieves the model of the car.
     *
     * @return The model of the car.
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model of the car.
     *
     * @param model The model to set.
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Retrieves the manufacturing year of the car.
     *
     * @return The manufacturing year of the car.
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the manufacturing year of the car.
     *
     * @param year The manufacturing year to set.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Retrieves the daily rental rate for the car.
     *
     * @return The daily rental rate for the car.
     */
    public double getDailyRate() {
        return dailyRate;
    }

    /**
     * Sets the daily rental rate for the car.
     *
     * @param dailyRate The daily rental rate to set.
     */
    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    /**
     * Retrieves the status of the car.
     *
     * @return The status of the car.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the car.
     *
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Retrieves the passenger capacity of the car.
     *
     * @return The passenger capacity of the car.
     */
    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    /**
     * Sets the passenger capacity of the car.
     *
     * @param passengerCapacity The passenger capacity to set.
     */
    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    /**
     * Retrieves the engine capacity of the car.
     *
     * @return The engine capacity of the car.
     */
    public int getEngineCapacity() {
        return engineCapacity;
    }

    /**
     * Sets the engine capacity of the car.
     *
     * @param engineCapacity The engine capacity to set.
     */
    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    /**
     * Compares this car to another object for equality.
     *
     * @param obj The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Car otherCar = (Car) obj;

        return vehicleID == otherCar.vehicleID &&
                year == otherCar.year &&
                Double.compare(otherCar.dailyRate, dailyRate) == 0 &&
                passengerCapacity == otherCar.passengerCapacity &&
                engineCapacity == otherCar.engineCapacity &&
                Objects.equals(make, otherCar.make) &&
                Objects.equals(model, otherCar.model) &&
                Objects.equals(status, otherCar.status);
    }

    /**
     * Returns a string representation of the Car object.
     *
     * @return A string representation of the Car object.
     */
    @Override
    public String toString() {
        return "Car{" +
                "vehicleID=" + vehicleID +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", dailyRate=" + dailyRate +
                ", status='" + status + '\'' +
                ", passengerCapacity=" + passengerCapacity +
                ", engineCapacity=" + engineCapacity +
                '}';
    }

}

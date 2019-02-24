package com.mytaxi.datatransferobjects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mytaxi.api.AbstractData;
import com.mytaxi.entities.values.EngineType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Car extends AbstractData {

    private String licensePlate;
    private Integer seatCount;
    private Boolean convertible;
    private Float rating;
    private EngineType engineType;
    private String manufacturer;
    private Boolean isDriverAssigned;

    private Car()
    {
    }

    public static CarBuilder newBuilder() {
        return new CarBuilder();
    }

    public Car(Long id, String licensePlate, Integer seatCount, Boolean convertible, Float rating, EngineType engineType,
               String manufacturer, Boolean isDriverAssigned) {
        setId(id);
        this.licensePlate = licensePlate;
        this.seatCount = seatCount;
        this.convertible = convertible;
        this.rating = rating;
        this.engineType = engineType;
        this.manufacturer = manufacturer;
        this.isDriverAssigned = isDriverAssigned;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public Boolean getConvertible() {
        return convertible;
    }

    public Float getRating() {
        return rating;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public Boolean getDriverAssigned() {
        return isDriverAssigned;
    }

    public void setDriverAssigned(Boolean driverAssigned) {
        isDriverAssigned = driverAssigned;
    }

    public static class CarBuilder {
        private Long id;
        private String licensePlate;
        private Integer seatCount;
        private Boolean convertible;
        private Float rating;
        private EngineType engineType;
        private String manufacturer;
        private Boolean isDriverAssigned;

        public CarBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public CarBuilder setLicensePlate(String licensePlate) {
            this.licensePlate = licensePlate;
            return this;
        }

        public CarBuilder setSeatCount(Integer seatCount) {
            this.seatCount = seatCount;
            return this;
        }

        public CarBuilder setConvertible(Boolean convertible) {
            this.convertible = convertible;
            return this;
        }

        public CarBuilder setRating(Float rating) {
            this.rating = rating;
            return this;
        }

        public CarBuilder setEngineType(EngineType engineType) {
            this.engineType = engineType;
            return this;
        }

        public CarBuilder setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
            return this;
        }

        public CarBuilder setDriverAssigned(Boolean driverAssigned) {
            this.isDriverAssigned = driverAssigned;
            return this;
        }

        public Car createCar() {
            return new Car(id, licensePlate, seatCount, convertible, rating, engineType, manufacturer, isDriverAssigned);
        }
    }

}

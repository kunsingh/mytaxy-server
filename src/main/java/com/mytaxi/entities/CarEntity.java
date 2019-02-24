package com.mytaxi.entities;

import com.mytaxi.api.AbstractEntity;
import com.mytaxi.entities.values.EngineType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table( name = "car")
public class CarEntity extends AbstractEntity {



    @Column
    private Float rating;

    @Enumerated(EnumType.STRING)
    @Column
    private EngineType engineType;

    @Column(nullable = false)
    private String licensePlate;

    @Column(nullable = false)
    private Integer seatCount;

    @Column
    private Boolean convertible;

    @Column(nullable = false)
    private Boolean isDriverAssigned = false;

    @OneToOne
    @JoinColumn(name="manufacturer")
    private ManufacturerEntity manufacturer;

    public CarEntity(){

    }



    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }

    public Boolean getConvertible() {
        return convertible;
    }

    public void setConvertible(Boolean convertible) {
        this.convertible = convertible;
    }

    public ManufacturerEntity getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(ManufacturerEntity manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Boolean getDriverAssigned() {
        return isDriverAssigned;
    }

    public void setDriverAssigned(Boolean driverAssigned) {
        isDriverAssigned = driverAssigned;
    }
}

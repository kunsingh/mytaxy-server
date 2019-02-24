package com.mytaxi;

import com.mytaxi.datatransferobjects.Car;
import com.mytaxi.datatransferobjects.Driver;
import com.mytaxi.entities.CarEntity;
import com.mytaxi.entities.DriverEntity;
import org.junit.Assert;

public class TestResultVerifier {
    public static void verifyCarData(Car car, CarEntity entity){
        Assert.assertEquals(car.getId(), entity.getId());
        Assert.assertEquals(car.getLicensePlate(), entity.getLicensePlate());
        Assert.assertEquals(car.getRating(), entity.getRating());
        Assert.assertEquals(car.getDriverAssigned(), entity.getDriverAssigned());
        if(car.getEngineType() != null){
            Assert.assertEquals(car.getEngineType().name(), entity.getEngineType().name());
        }
        Assert.assertEquals(car.getConvertible(), entity.getConvertible());
        Assert.assertEquals(car.getManufacturer(), entity.getManufacturer().getName());
    }

    public static  void verifyDriverData(Driver driver, DriverEntity entity){
        Assert.assertEquals(driver.getId(), entity.getId());
        Assert.assertEquals(driver.getUsername(), entity.getUsername());
        Assert.assertEquals(driver.getCoordinate(), entity.getCoordinate());
        if(driver.getOnlineStatus() != null){
            Assert.assertEquals(driver.getOnlineStatus().name(), entity.getOnlineStatus().name());
        }
        Assert.assertEquals(driver.getAssignedCar().getEngineType().name(), entity.getCar().getEngineType().name());
    }
}

package com.mytaxi;

import com.mytaxi.datatransferobjects.Car;
import com.mytaxi.datatransferobjects.Driver;
import com.mytaxi.entities.CarEntity;
import com.mytaxi.entities.DriverEntity;
import com.mytaxi.entities.ManufacturerEntity;
import com.mytaxi.entities.values.EngineType;
import com.mytaxi.entities.values.GeoCoordinate;
import com.mytaxi.entities.values.OnlineStatus;

public class TestDataFactory {

    public static Car getCar(Long id){
        Car.CarBuilder builder = Car.newBuilder();
        builder.setId(id)
                .setConvertible(true)
                .setLicensePlate("LicensePlate-1")
                .setSeatCount(5)
                .setDriverAssigned(false)
                .setRating(4.0f)
                .setEngineType(EngineType.ELECTRIC)
                .setManufacturer("Nissan");
        return builder.createCar();
    }

    public static Driver getDriver(Long id)
    {
        Driver.DriverBuilder builder = Driver.newBuilder();
        builder.setId(id)
                .setUsername("test")
                .setPassword("test")
                .setCoordinate( new GeoCoordinate(90, 90))
                .setAssignedCar(getCar(id));

        return builder.createDriver();
    }

    public static CarEntity getCarEntity(Long id){
        CarEntity carEntity = new CarEntity();
        carEntity.setId(id);
        carEntity.setConvertible(true);
        carEntity.setLicensePlate("LicensePlate-1");
        carEntity.setSeatCount(5);
        carEntity.setDriverAssigned(false);
        carEntity.setRating(4.0f);
        carEntity.setEngineType(EngineType.ELECTRIC);
        return carEntity;
    }

    public static DriverEntity getDriverEntity(Long id)
    {
        DriverEntity driverEntity = new DriverEntity("test", "test");
        driverEntity.setId(id);
        driverEntity.setCoordinate( new GeoCoordinate(90, 90));
        driverEntity.setOnlineStatus(OnlineStatus.ONLINE);
        return driverEntity;
    }

    public static ManufacturerEntity getManufacturerEntity(Long id){
        ManufacturerEntity manufacturerEntity = new ManufacturerEntity();
        manufacturerEntity.setId(id);
        manufacturerEntity.setName("Nissan");
        return manufacturerEntity;
    }

}

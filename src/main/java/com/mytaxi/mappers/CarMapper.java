package com.mytaxi.mappers;

import com.mytaxi.api.Mapper;
import com.mytaxi.entities.CarEntity;
import com.mytaxi.entities.ManufacturerEntity;
import com.mytaxi.datatransferobjects.Car;
import com.mytaxi.repositories.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarMapper implements Mapper<Car, CarEntity> {

    @Autowired
    private ManufacturerRepository repository;

    @Override
    public Car entityToObject(CarEntity entity) {
        if (entity == null) {
            return null;
        }
        Car.CarBuilder builder = Car.newBuilder();
        builder.setId(entity.getId())
                .setConvertible(entity.getConvertible())
                .setLicensePlate(entity.getLicensePlate())
                .setSeatCount(entity.getSeatCount())
                .setDriverAssigned(entity.getDriverAssigned())
                .setRating(entity.getRating())
                .setEngineType(entity.getEngineType());
        if(entity.getManufacturer() != null){
            builder.setManufacturer(entity.getManufacturer().getName());
        }

        return builder.createCar();
    }

    @Override
    public CarEntity objectToEntity(Car object) {
        if (object == null) {
            return null;
        }
        CarEntity carEntity = new CarEntity();
        carEntity.setId(object.getId());
        carEntity.setConvertible(object.getConvertible());
        carEntity.setEngineType(object.getEngineType());
        carEntity.setDriverAssigned(object.getDriverAssigned());
        carEntity.setLicensePlate(object.getLicensePlate());
        carEntity.setRating(object.getRating());
        carEntity.setSeatCount(object.getSeatCount());
        ManufacturerEntity manufacturer = repository.findByName(object.getManufacturer());
        carEntity.setManufacturer(manufacturer);
        return carEntity;
    }

    @Override
    public List<CarEntity> objectsToEntities(List<Car> objects) {
        return objects.stream()
                .map((object) -> objectToEntity(object))
                .collect(Collectors.toList());
    }

    @Override
    public List<Car> entitiesToObjects(List<CarEntity> entities) {
        return entities.stream()
                .map((entity) -> entityToObject(entity))
                .collect(Collectors.toList());
    }
}

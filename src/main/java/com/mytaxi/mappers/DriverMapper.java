package com.mytaxi.mappers;

import com.mytaxi.api.Mapper;
import com.mytaxi.entities.DriverEntity;
import com.mytaxi.datatransferobjects.Driver;
import com.mytaxi.entities.values.GeoCoordinate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DriverMapper implements Mapper<Driver, DriverEntity> {

    @Autowired
    private CarMapper carMapper;

    @Override
    public Driver entityToObject(DriverEntity entity) {
        if (entity == null) {
            return null;
        }
        Driver.DriverBuilder builder = Driver.newBuilder();
        builder.setId(entity.getId())
                .setUsername(entity.getUsername())
                .setPassword(entity.getPassword())
                .setOnlineStatus(entity.getOnlineStatus());
        GeoCoordinate coordinate = entity.getCoordinate();
        if (coordinate != null) {
            builder.setCoordinate(coordinate);
        }

        builder.setAssignedCar(carMapper.entityToObject(entity.getCar()));
        return builder.createDriver();
    }

    @Override
    public DriverEntity objectToEntity(Driver object) {
        if (object == null) {
            return null;
        }
        DriverEntity entity = new DriverEntity(object.getUsername(), object.getPassword());
        entity.setId(object.getId());
        entity.setCoordinate(object.getCoordinate());
        entity.setOnlineStatus(object.getOnlineStatus());
        entity.setCar(carMapper.objectToEntity(object.getAssignedCar()));
        return entity;
    }

    @Override
    public List<DriverEntity> objectsToEntities(List<Driver> objects) {
        return objects.stream()
                .map((object) -> objectToEntity(object))
                .collect(Collectors.toList());
    }

    @Override
    public List<Driver> entitiesToObjects(List<DriverEntity> entities) {
        return entities.stream()
                .map((entity) -> entityToObject(entity))
                .collect(Collectors.toList());
    }

}

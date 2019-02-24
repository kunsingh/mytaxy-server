package com.mytaxi.services.impl;

import com.mytaxi.api.AbstractDataService;
import com.mytaxi.api.Mapper;
import com.mytaxi.entities.CarEntity;
import com.mytaxi.datatransferobjects.Car;
import com.mytaxi.repositories.CarRepository;
import com.mytaxi.services.CarService;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl extends AbstractDataService<Car, CarEntity, CarRepository> implements CarService {

    public CarServiceImpl(CarRepository repository, Mapper<Car, CarEntity> mapper) {
        super(repository, mapper);
    }
}

package com.mytaxi.controllers;

import com.mytaxi.exceptions.ConstraintsViolationException;
import com.mytaxi.exceptions.EntityNotFoundException;
import com.mytaxi.datatransferobjects.Car;
import com.mytaxi.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * All operations with a Car will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/cars")
public class CarController
{

    private final CarService carService;


    @Autowired
    public CarController(final CarService carService)
    {
        this.carService = carService;
    }


    @GetMapping("/{carId}")
    public Car getCar(@Valid @PathVariable long carId) throws EntityNotFoundException
    {
        return carService.find(carId);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Car createCar(@Valid @RequestBody Car car) throws ConstraintsViolationException
    {
        return carService.createOrUpdate(car);
    }

    @PutMapping
    public Car updateCar(@Valid @RequestBody Car car) throws ConstraintsViolationException
    {
        return carService.createOrUpdate(car);
    }


    @DeleteMapping("/{carId}")
    public void deleteCar(@Valid @PathVariable long carId) throws EntityNotFoundException
    {
        carService.delete(carId);
    }
}

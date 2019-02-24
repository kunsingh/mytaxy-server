package com.mytaxi.controllers;

import com.mytaxi.exceptions.CarAlreadyInUseException;
import com.mytaxi.exceptions.ConstraintsViolationException;
import com.mytaxi.exceptions.DriverIsNotOnlineException;
import com.mytaxi.exceptions.EntityNotFoundException;
import com.mytaxi.datatransferobjects.Driver;
import com.mytaxi.entities.values.OnlineStatus;
import com.mytaxi.datatransferobjects.SearchCriteria;
import com.mytaxi.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController {

    private final DriverService driverService;


    @Autowired
    public DriverController(final DriverService driverService) {
        this.driverService = driverService;
    }

    @GetMapping("/{driverId}")
    public Driver getDriver(@Valid @PathVariable long driverId) throws EntityNotFoundException {
        return driverService.find(driverId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Driver createDriver(@Valid @RequestBody Driver driver) throws ConstraintsViolationException {
        return driverService.createOrUpdate(driver);
    }

    @DeleteMapping("/{driverId}")
    public void deleteDriver(@Valid @PathVariable long driverId) throws EntityNotFoundException {
        driverService.delete(driverId);
    }

    @PutMapping("/{driverId}")
    public void updateLocation(
            @Valid @PathVariable long driverId, @RequestParam double longitude, @RequestParam double latitude)
            throws EntityNotFoundException {
        driverService.updateLocation(driverId, longitude, latitude);
    }

    @GetMapping
    public List<Driver> findDrivers(@RequestParam OnlineStatus onlineStatus) throws EntityNotFoundException {
        return driverService.find(onlineStatus);
    }

    @PostMapping("/selectCar")
    public Driver selectCar(@Valid @RequestParam long driverId, @Valid @RequestParam long carId)
            throws EntityNotFoundException, ConstraintsViolationException, DriverIsNotOnlineException, CarAlreadyInUseException {
        return driverService.selectCar(driverId, carId);
    }

    @PostMapping("/deselectCar")
    public Driver deselectCar(@Valid @RequestParam long driverId, @Valid @RequestParam long carId) throws EntityNotFoundException, ConstraintsViolationException {
        return driverService.deselectCar(driverId, carId);
    }

    @PostMapping("/search")
    public List<Driver> searchDrivers(@Valid @RequestBody SearchCriteria searchData) {
        return driverService.search(searchData);
    }
}

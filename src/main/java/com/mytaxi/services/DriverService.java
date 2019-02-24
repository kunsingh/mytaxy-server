package com.mytaxi.services;

import com.mytaxi.api.DataService;
import com.mytaxi.datatransferobjects.SearchCriteria;
import com.mytaxi.exceptions.CarAlreadyInUseException;
import com.mytaxi.exceptions.ConstraintsViolationException;
import com.mytaxi.exceptions.DriverIsNotOnlineException;
import com.mytaxi.exceptions.EntityNotFoundException;
import com.mytaxi.datatransferobjects.Driver;
import com.mytaxi.entities.values.OnlineStatus;

import java.util.List;

public interface DriverService extends DataService<Driver> {

    void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException;

    List<Driver> find(OnlineStatus onlineStatus) throws EntityNotFoundException;

    Driver selectCar(long driverId, long carId) throws EntityNotFoundException, ConstraintsViolationException,
            CarAlreadyInUseException, DriverIsNotOnlineException;

    Driver deselectCar(long driverId,  long carId) throws EntityNotFoundException, ConstraintsViolationException;


    List<Driver> search(SearchCriteria searchData) throws IllegalArgumentException, IllegalStateException;
}

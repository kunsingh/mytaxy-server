package com.mytaxi.services.impl;

import com.mytaxi.api.AbstractDataService;
import com.mytaxi.datatransferobjects.SearchCriteria;
import com.mytaxi.entities.DriverEntity;
import com.mytaxi.exceptions.CarAlreadyInUseException;
import com.mytaxi.exceptions.ConstraintsViolationException;
import com.mytaxi.exceptions.DriverIsNotOnlineException;
import com.mytaxi.exceptions.EntityNotFoundException;
import com.mytaxi.mappers.CarMapper;
import com.mytaxi.mappers.DriverMapper;
import com.mytaxi.datatransferobjects.Car;
import com.mytaxi.datatransferobjects.Driver;
import com.mytaxi.entities.values.GeoCoordinate;
import com.mytaxi.entities.values.OnlineStatus;
import com.mytaxi.repositories.DriverRepository;
import com.mytaxi.services.CarService;
import com.mytaxi.services.DriverService;
import com.mytaxi.utils.SearchSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class DriverServiceImpl extends AbstractDataService<Driver, DriverEntity, DriverRepository> implements DriverService {

    @Autowired
    private CarService carService;

    @Autowired
    private CarMapper carMapper;


    public DriverServiceImpl(DriverRepository repository, DriverMapper mapper) {
        super(repository, mapper);
    }

    @Override
    @Transactional
    public void updateLocation(long driverId, double longitude, double latitude) throws EntityNotFoundException {
        DriverEntity entity = findEntityChecked(driverId);
        entity.setCoordinate(new GeoCoordinate(latitude, longitude));
    }

    @Override
    @Transactional
    public List<Driver> find(OnlineStatus onlineStatus) throws EntityNotFoundException{
        List<DriverEntity> entities = getRepository().findByOnlineStatus(onlineStatus);
        if(entities == null || entities.isEmpty()){
            throw new EntityNotFoundException("Could not find any online drivers.");
        }
        return getMapper().entitiesToObjects(entities);
    }

    @Override
    @Transactional
    public synchronized Driver selectCar(long driverId, long carId) throws EntityNotFoundException, ConstraintsViolationException,
            DriverIsNotOnlineException, CarAlreadyInUseException {

        DriverEntity driverEntity = findEntityChecked(driverId);
        if(driverEntity.getOnlineStatus() == OnlineStatus.OFFLINE){
            throw new DriverIsNotOnlineException("Driver is offline! Can't select a car.");
        }
        Car car = carService.find(carId);
        if(car.getDriverAssigned() != null && car.getDriverAssigned()){
            throw new CarAlreadyInUseException("This car with id"+ carId +" is already assigned to driver.");
        }
        car.setDriverAssigned(true);
        driverEntity.setCar(carMapper.objectToEntity(car));
        carService.createOrUpdate(car);
        return getMapper().entityToObject(driverEntity);
    }

    @Override
    @Transactional
    public synchronized Driver  deselectCar(long driverId, long carId) throws EntityNotFoundException, ConstraintsViolationException {
        DriverEntity driverEntity = findEntityChecked(driverId);
        driverEntity.setCar(null);
        Car car = carService.find(carId);
        car.setDriverAssigned(false);
        carService.createOrUpdate(car);
        return getMapper().entityToObject(driverEntity);
    }

    @Override
    public List<Driver> search(SearchCriteria searchData) throws IllegalArgumentException, IllegalStateException{
        return getMapper().entitiesToObjects(getRepository().findAll(new SearchSpecification(searchData)));
    }
}

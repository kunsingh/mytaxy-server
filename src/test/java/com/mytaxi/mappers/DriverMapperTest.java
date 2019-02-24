package com.mytaxi.mappers;

import java.util.Arrays;
import java.util.List;

import com.mytaxi.TestDataFactory;
import com.mytaxi.TestResultVerifier;
import com.mytaxi.datatransferobjects.Driver;
import com.mytaxi.entities.CarEntity;
import com.mytaxi.entities.DriverEntity;
import com.mytaxi.repositories.ManufacturerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {DriverMapper.class, CarMapper.class})
public class DriverMapperTest {


    @MockBean
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private DriverMapper driverMapper;


    @Test
    public void shouldMapGivenObjectToEntity(){

        Driver driver = TestDataFactory.getDriver(1l);

        DriverEntity driverEntity = driverMapper.objectToEntity(driver);

        TestResultVerifier.verifyDriverData(driver, driverEntity);

    }

    @Test
    public void shouldReturnNullForGivenNullObjectToEntity(){

        DriverEntity driverEntity = driverMapper.objectToEntity(null);

        Assert.assertNull(driverEntity);

    }

    @Test
    public void shouldMapGivenEntityToObject(){

        DriverEntity driverEntity = TestDataFactory.getDriverEntity(1l);
        CarEntity carEntity = TestDataFactory.getCarEntity(1l);
        driverEntity.setCar(carEntity);
        Driver driver = driverMapper.entityToObject(driverEntity);

        TestResultVerifier.verifyDriverData(driver, driverEntity);

    }

    @Test
    public void shouldReturnNullForGivenNullEntityToObject(){

        Driver driver = driverMapper.entityToObject(null);

        Assert.assertNull(driver);

    }

    @Test
    public void shouldMapGivenObjectsToEntities(){

        Driver driver = TestDataFactory.getDriver(1l);

        List<DriverEntity> driverEntities = driverMapper.objectsToEntities(Arrays.asList(driver));

        TestResultVerifier.verifyDriverData(driver, driverEntities.get(0));

    }

    @Test
    public void shouldMapGivenEntitiesToObjects(){

        DriverEntity driverEntity = TestDataFactory.getDriverEntity(1l);
        CarEntity carEntity = TestDataFactory.getCarEntity(1l);
        driverEntity.setCar(carEntity);

        List<Driver> drivers = driverMapper.entitiesToObjects(Arrays.asList(driverEntity));

        TestResultVerifier.verifyDriverData(drivers.get(0), driverEntity);

    }

}

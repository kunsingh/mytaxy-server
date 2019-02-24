package com.mytaxi.mappers;

import java.util.Arrays;
import java.util.List;

import com.mytaxi.TestDataFactory;
import com.mytaxi.TestResultVerifier;
import com.mytaxi.datatransferobjects.Car;
import com.mytaxi.entities.CarEntity;
import com.mytaxi.entities.ManufacturerEntity;
import com.mytaxi.repositories.ManufacturerRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CarMapper.class})
public class CarMapperTest {

    @MockBean
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private CarMapper carMapper;


    @Test
    public void shouldMapGivenObjectToEntity(){

        Car car = TestDataFactory.getCar(1l);

        Mockito.when(manufacturerRepository.findByName(Mockito.anyString())).thenReturn(TestDataFactory.getManufacturerEntity(1l));

        CarEntity carEntity = carMapper.objectToEntity(car);

        TestResultVerifier.verifyCarData(car, carEntity);

    }

    @Test
    public void shouldReturnNullForGivenNullObjectToEntity(){

        CarEntity carEntity = carMapper.objectToEntity(null);

        Assert.assertNull(carEntity);

    }

    @Test
    public void shouldMapGivenEntityToObject(){

        CarEntity carEntity = TestDataFactory.getCarEntity(1l);
        ManufacturerEntity manufacturerEntity = TestDataFactory.getManufacturerEntity(1l);
        carEntity.setManufacturer(manufacturerEntity);

        Car car = carMapper.entityToObject(carEntity);

        TestResultVerifier.verifyCarData(car, carEntity);

    }

    @Test
    public void shouldReturnNullForGivenNullEntityToObject(){

        Car car = carMapper.entityToObject(null);

        Assert.assertNull(car);

    }


    @Test
    public void shouldMapGivenObjectsToEntities(){

        Car car = TestDataFactory.getCar(1l);

        Mockito.when(manufacturerRepository.findByName(Mockito.anyString())).thenReturn(TestDataFactory.getManufacturerEntity(1l));

        List<CarEntity> carEntities = carMapper.objectsToEntities(Arrays.asList(car));

        TestResultVerifier.verifyCarData(car, carEntities.get(0));

    }

    @Test
    public void shouldMapGivenEntitiesToObjects(){

        CarEntity carEntity = TestDataFactory.getCarEntity(1l);

        ManufacturerEntity manufacturerEntity = TestDataFactory.getManufacturerEntity(1l);
        carEntity.setManufacturer(manufacturerEntity);

        List<Car> cars = carMapper.entitiesToObjects(Arrays.asList(carEntity));

        TestResultVerifier.verifyCarData(cars.get(0), carEntity);

    }

}

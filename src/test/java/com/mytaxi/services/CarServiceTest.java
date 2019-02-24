package com.mytaxi.services;

import com.mytaxi.TestDataFactory;
import com.mytaxi.TestResultVerifier;
import com.mytaxi.datatransferobjects.Car;
import com.mytaxi.entities.CarEntity;
import com.mytaxi.entities.ManufacturerEntity;
import com.mytaxi.mappers.CarMapper;
import com.mytaxi.repositories.CarRepository;
import com.mytaxi.repositories.ManufacturerRepository;
import com.mytaxi.services.impl.CarServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {CarServiceImpl.class, CarMapper.class})
public class CarServiceTest {

    @MockBean
    private CarRepository repository;

    @MockBean
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private CarService carService;

    @Test
    public void shouldReturnACarById() throws Exception{
        CarEntity carEntity =TestDataFactory.getCarEntity(1l);
        ManufacturerEntity manufacturerEntity = TestDataFactory.getManufacturerEntity(1l);
        carEntity.setManufacturer(manufacturerEntity);
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(carEntity));
        Car result = carService.find(1);
        TestResultVerifier.verifyCarData(result, carEntity);
    }

    @Test
    public void shouldCreateOrUpdateACarForGivenCarDetails() throws Exception{
        CarEntity carEntity =TestDataFactory.getCarEntity(1l);
        ManufacturerEntity manufacturerEntity = TestDataFactory.getManufacturerEntity(1l);
        carEntity.setManufacturer(manufacturerEntity);
        Car car =TestDataFactory.getCar(1l);
        Mockito.when(repository.save(carEntity)).thenReturn(carEntity);
        Car result = carService.createOrUpdate(car);
        TestResultVerifier.verifyCarData(result, carEntity);
    }

    @Test
    public void shouldDeleteACarForGivenCarId() throws Exception{
        CarEntity carEntity =TestDataFactory.getCarEntity(1l);
        carEntity.setDeleted(true);
        Car car =TestDataFactory.getCar(1l);
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(carEntity));
        carService.delete(car.getId());
        verify(repository, times(1)).findById(car.getId());
    }


}

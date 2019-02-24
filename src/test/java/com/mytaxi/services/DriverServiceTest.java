package com.mytaxi.services;

import java.util.Arrays;
import java.util.List;

import com.mytaxi.TestDataFactory;
import com.mytaxi.TestResultVerifier;
import com.mytaxi.datatransferobjects.Car;
import com.mytaxi.datatransferobjects.Driver;
import com.mytaxi.datatransferobjects.SearchCriteria;
import com.mytaxi.datatransferobjects.values.CarSearchData;
import com.mytaxi.datatransferobjects.values.DriverSearchData;
import com.mytaxi.entities.CarEntity;
import com.mytaxi.entities.DriverEntity;
import com.mytaxi.entities.values.EngineType;
import com.mytaxi.entities.values.OnlineStatus;
import com.mytaxi.exceptions.CarAlreadyInUseException;
import com.mytaxi.exceptions.DriverIsNotOnlineException;
import com.mytaxi.exceptions.EntityNotFoundException;
import com.mytaxi.mappers.CarMapper;
import com.mytaxi.mappers.DriverMapper;
import com.mytaxi.repositories.DriverRepository;
import com.mytaxi.repositories.ManufacturerRepository;
import com.mytaxi.services.impl.DriverServiceImpl;
import com.mytaxi.utils.SearchSpecification;
import org.junit.Assert;
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
@ContextConfiguration(classes = {DriverServiceImpl.class, DriverMapper.class, CarMapper.class})
public class DriverServiceTest {

    @MockBean
    private DriverRepository repository;

    @Autowired
    private DriverService driverService;

    @MockBean
    private CarService carService;

    @MockBean
    private ManufacturerRepository manufacturerRepository;


    @Test
    public void shouldReturnADriverById() throws Exception{
        DriverEntity driverEntity = TestDataFactory.getDriverEntity(1l);
        CarEntity carEntity = TestDataFactory.getCarEntity(1l);
        driverEntity.setCar(carEntity);
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(driverEntity));
        Driver result = driverService.find(1);
        TestResultVerifier.verifyDriverData(result, driverEntity);
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowEntityNotFoundWhenNoDriverIsFoundById() throws Exception{
        DriverEntity driverEntity = TestDataFactory.getDriverEntity(1l);
        CarEntity carEntity = TestDataFactory.getCarEntity(1l);
        driverEntity.setCar(carEntity);
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(null));
        Driver result = driverService.find(1);
        TestResultVerifier.verifyDriverData(result, driverEntity);
    }

    @Test
    public void shouldCreateOrUpdateADriverForGivenDriverDetails() throws Exception{
        DriverEntity driverEntity = TestDataFactory.getDriverEntity(1l);
        CarEntity carEntity = TestDataFactory.getCarEntity(1l);
        driverEntity.setCar(carEntity);
        Driver driver =TestDataFactory.getDriver(1l);
        Mockito.when(repository.save(driverEntity)).thenReturn(driverEntity);
        Driver result = driverService.createOrUpdate(driver);
        TestResultVerifier.verifyDriverData(result, driverEntity);
    }

    @Test
    public void shouldDeleteADriverForGivenDriverId() throws Exception{
        DriverEntity driverEntity = TestDataFactory.getDriverEntity(1l);
        driverEntity.setDeleted(true);
        Driver driver =TestDataFactory.getDriver(1l);
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(driverEntity));
        driverService.delete(driver.getId());
        verify(repository, times(1)).findById(driver.getId());
    }

    @Test
    public void shouldSelectGivenCarForGivenDriver() throws Exception{
        DriverEntity driverEntity = TestDataFactory.getDriverEntity(1l);
        driverEntity.setCar(null);

        Driver driver =TestDataFactory.getDriver(1l);
        Car car = TestDataFactory.getCar(1l);

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(driverEntity));
        Mockito.when(carService.find(Mockito.anyLong())).thenReturn(car);
        Mockito.when(carService.createOrUpdate(car)).thenReturn(car);

        Driver result = driverService.selectCar(driver.getId(), car.getId());

        Assert.assertNotNull(result.getAssignedCar());
        Assert.assertEquals(result.getAssignedCar().getLicensePlate(), car.getLicensePlate());
    }

    @Test(expected = DriverIsNotOnlineException.class)
    public void shouldThrowDriverIsNotOnlineExceptionWhenGivenDriverIsOffline() throws Exception{
        DriverEntity driverEntity = TestDataFactory.getDriverEntity(1l);
        driverEntity.setCar(null);
        driverEntity.setOnlineStatus(OnlineStatus.OFFLINE);

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(driverEntity));

        driverService.selectCar(1, 1);
    }

    @Test(expected = CarAlreadyInUseException.class)
    public void shouldThrowCarAlreadyInUseExceptionWhenGivenCarIsAssignedToDriver() throws Exception{
        DriverEntity driverEntity = TestDataFactory.getDriverEntity(1l);
        driverEntity.setCar(null);

        Car car = TestDataFactory.getCar(1l);
        car.setDriverAssigned(true);

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(driverEntity));
        Mockito.when(carService.find(Mockito.anyLong())).thenReturn(car);

        driverService.selectCar(1, 1);
    }

    @Test
    public void shouldDeSelectGivenCarForGivenDriver() throws Exception{
        DriverEntity driverEntity = TestDataFactory.getDriverEntity(1l);

        Driver driver =TestDataFactory.getDriver(1l);
        Car car = TestDataFactory.getCar(1l);

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(driverEntity));
        Mockito.when(carService.find(Mockito.anyLong())).thenReturn(car);
        Mockito.when(carService.createOrUpdate(car)).thenReturn(car);

        Driver result = driverService.deselectCar(driver.getId(), car.getId());

        Assert.assertNull(result.getAssignedCar());
    }

    @Test
    public void shouldUpdateDriverLocation() throws Exception{
        DriverEntity driverEntity = TestDataFactory.getDriverEntity(1l);

        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(driverEntity));

        driverService.updateLocation(driverEntity.getId(), 40.3d, 50.2d);

        verify(repository, times(1)).findById(driverEntity.getId());
    }

    @Test
    public void shouldSearchDriversForGivenSearchCriteria(){
        DriverEntity driverEntity = TestDataFactory.getDriverEntity(1l);

        SearchCriteria searchCriteria = new SearchCriteria();
        DriverSearchData driverSearchData = new DriverSearchData();
        driverSearchData.setOnlineStatus(OnlineStatus.OFFLINE);
        driverSearchData.setUsername("test");

        CarSearchData carSearchData = new CarSearchData();
        carSearchData.setEngineType(EngineType.ELECTRIC);

        Mockito.when(repository.findAll(Mockito.any(SearchSpecification.class))).thenReturn(Arrays.asList(driverEntity));
        List<Driver> result = driverService.search(searchCriteria);

        Assert.assertTrue(result.size() > 0);
        Assert.assertEquals(result.get(0).getUsername(), driverEntity.getUsername());
    }

}

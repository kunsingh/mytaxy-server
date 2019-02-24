package com.mytaxi.repositories;

import java.util.Optional;

import com.mytaxi.H2TestProfileJPAConfig;
import com.mytaxi.MytaxiServerApplication;
import com.mytaxi.TestDataFactory;
import com.mytaxi.entities.CarEntity;
import com.mytaxi.entities.DriverEntity;
import com.mytaxi.entities.ManufacturerEntity;
import com.mytaxi.entities.values.OnlineStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { MytaxiServerApplication.class, H2TestProfileJPAConfig.class })
@ActiveProfiles("test")
@Transactional
public class DriverRepositoryTest {


    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Test
    public void shouldCreateAGivenDriver(){
        DriverEntity driverEntity = saveDriver();
        DriverEntity result = driverRepository.findById(driverEntity.getId()).get();
        Assert.assertTrue(result.getId()!=null);
        Assert.assertEquals(result.getUsername(), TestDataFactory.getDriver(null).getUsername());

    }

    @Test
    public void shouldUpdateExistingDriver(){

        DriverEntity driverEntity = saveDriver();
        Assert.assertTrue(driverEntity.getId()!=null);
        driverEntity.setOnlineStatus(OnlineStatus.ONLINE);

        DriverEntity result = driverRepository.findById(driverEntity.getId()).get();
        Assert.assertTrue(result.getId()!=null);
        Assert.assertEquals(result.getOnlineStatus(), OnlineStatus.ONLINE);

    }

    @Test
    public void shouldDeleteAnExistingDriver(){

        DriverEntity savedDriver = saveDriver();
        DriverEntity result = driverRepository.findById(savedDriver.getId()).get();
        Assert.assertTrue(result.getId()!=null);

        driverRepository.delete(result);

        Optional<DriverEntity> deletedCar = driverRepository.findById(result.getId());
        Assert.assertFalse(deletedCar.isPresent());

    }

    @Test
    public void shouldSelectACarForDriver(){

        DriverEntity savedDriver = saveDriver();
        Assert.assertNull(savedDriver.getCar());

        ManufacturerEntity savedManufacturer = saveManufacturerEntity();
        CarEntity savedCar = saveCar(savedManufacturer);
        savedDriver.setCar(savedCar);

        DriverEntity result = driverRepository.findById(savedDriver.getId()).get();
        Assert.assertNotNull(result.getCar());
    }

    @Test
    public void shouldDeSelectACarForDriver(){

        DriverEntity savedDriver = saveDriver();
        Assert.assertNull(savedDriver.getCar());

        ManufacturerEntity savedManufacturer = saveManufacturerEntity();
        CarEntity savedCar = saveCar(savedManufacturer);
        savedDriver.setCar(savedCar);

        DriverEntity result = driverRepository.findById(savedDriver.getId()).get();
        Assert.assertNotNull(result.getCar());

        result.setCar(null);

        DriverEntity result1 = driverRepository.findById(savedDriver.getId()).get();
        Assert.assertNull(result1.getCar());
    }



    private ManufacturerEntity saveManufacturerEntity() {
        ManufacturerEntity manufacturerEntity = TestDataFactory.getManufacturerEntity(null);
        manufacturerRepository.save(manufacturerEntity);
        return manufacturerRepository.findByName(manufacturerEntity.getName());
    }

    private DriverEntity saveDriver(){
        DriverEntity driverEntity = TestDataFactory.getDriverEntity(null);
        return driverRepository.save(driverEntity);
    }

    private CarEntity saveCar(ManufacturerEntity entity){
        CarEntity carEntity = TestDataFactory.getCarEntity(null);
        carEntity.setManufacturer(entity);
        return carRepository.save(carEntity);
    }
}

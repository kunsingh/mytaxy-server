package com.mytaxi.repositories;

import java.util.Optional;

import com.mytaxi.H2TestProfileJPAConfig;
import com.mytaxi.MytaxiServerApplication;
import com.mytaxi.TestDataFactory;
import com.mytaxi.entities.CarEntity;
import com.mytaxi.entities.ManufacturerEntity;
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
public class CarRepositoryTest {


    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Test
    public void shouldCreateGivenCar(){
        ManufacturerEntity savedManufacturer = saveManufacturerEntity();
        Assert.assertTrue(savedManufacturer.getId()!=null);

        CarEntity savedCar = saveCar(savedManufacturer);
        CarEntity result = carRepository.findById(savedCar.getId()).get();
        Assert.assertTrue(result.getId()!=null);
        Assert.assertEquals(result.getLicensePlate(), TestDataFactory.getCar(null).getLicensePlate());

    }

    @Test
    public void shouldUpdateExistingCar(){
        ManufacturerEntity savedManufacturer = saveManufacturerEntity();
        Assert.assertTrue(savedManufacturer.getId()!=null);

        CarEntity savedCar = saveCar(savedManufacturer);
        savedCar.setLicensePlate("TestLicensePlate");

        CarEntity result = carRepository.findById(savedCar.getId()).get();
        Assert.assertTrue(result.getId()!=null);
        Assert.assertEquals(result.getLicensePlate(), "TestLicensePlate");

    }

    @Test
    public void shouldDeleteAnExistingCar(){
        ManufacturerEntity savedManufacturer = saveManufacturerEntity();
        Assert.assertTrue(savedManufacturer.getId()!=null);

        CarEntity savedCar = saveCar(savedManufacturer);
        CarEntity result = carRepository.findById(savedCar.getId()).get();
        Assert.assertTrue(result.getId()!=null);

        carRepository.delete(result);

        Optional<CarEntity> deletedCar = carRepository.findById(savedCar.getId());
        Assert.assertFalse(deletedCar.isPresent());

    }

    private ManufacturerEntity saveManufacturerEntity() {
        ManufacturerEntity manufacturerEntity = TestDataFactory.getManufacturerEntity(null);
        manufacturerRepository.save(manufacturerEntity);
        return manufacturerRepository.findByName(manufacturerEntity.getName());
    }

    private CarEntity saveCar(ManufacturerEntity entity){
        CarEntity carEntity = TestDataFactory.getCarEntity(null);
        carEntity.setManufacturer(entity);
        return carRepository.save(carEntity);
    }
}

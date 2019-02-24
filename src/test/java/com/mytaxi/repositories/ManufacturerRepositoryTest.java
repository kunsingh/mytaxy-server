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
public class ManufacturerRepositoryTest {


    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Test
    public void shouldCreateGivenManufacturer(){
        ManufacturerEntity savedManufacturer = saveManufacturerEntity();
        Assert.assertTrue(savedManufacturer.getId()!=null);

        ManufacturerEntity result = manufacturerRepository.findById(savedManufacturer.getId()).get();
        Assert.assertTrue(result.getId()!=null);
        Assert.assertEquals(result.getName(), TestDataFactory.getManufacturerEntity(null).getName());

    }

    @Test
    public void shouldUpdateExistingManufacturer(){
        ManufacturerEntity savedManufacturer = saveManufacturerEntity();
        Assert.assertTrue(savedManufacturer.getId()!=null);

        savedManufacturer.setName("Test123");

        ManufacturerEntity result = manufacturerRepository.findById(savedManufacturer.getId()).get();
        Assert.assertEquals(result.getName(), "Test123");

    }

    @Test
    public void shouldDeleteAnExistingManufacturer(){
        ManufacturerEntity savedManufacturer = saveManufacturerEntity();
        Assert.assertTrue(savedManufacturer.getId()!=null);

        manufacturerRepository.delete(savedManufacturer);

        Optional<ManufacturerEntity> deletedManufacturer = manufacturerRepository.findById(savedManufacturer.getId());
        Assert.assertFalse(deletedManufacturer.isPresent());

    }

    private ManufacturerEntity saveManufacturerEntity() {
        ManufacturerEntity manufacturerEntity = TestDataFactory.getManufacturerEntity(null);
        manufacturerRepository.save(manufacturerEntity);
        return manufacturerRepository.findByName(manufacturerEntity.getName());
    }

}

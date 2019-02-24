package com.mytaxi.repositories;

import com.mytaxi.api.EntityBaseRepository;
import com.mytaxi.entities.ManufacturerEntity;

public interface ManufacturerRepository extends EntityBaseRepository<ManufacturerEntity> {
    ManufacturerEntity findByName(String name);
}

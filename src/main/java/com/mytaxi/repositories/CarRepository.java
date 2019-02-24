package com.mytaxi.repositories;

import java.util.Optional;

import com.mytaxi.api.EntityBaseRepository;
import com.mytaxi.entities.CarEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CarRepository extends EntityBaseRepository<CarEntity> {

    @Query("SELECT C FROM CarEntity C WHERE C.id = :#{#id} AND C.deleted = false")
    Optional<CarEntity> findById(@Param("id") Long id);
}

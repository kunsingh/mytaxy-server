package com.mytaxi.repositories;

import com.mytaxi.api.EntityBaseRepository;
import com.mytaxi.entities.CarEntity;
import com.mytaxi.entities.DriverEntity;
import com.mytaxi.entities.values.OnlineStatus;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends EntityBaseRepository<DriverEntity>, JpaSpecificationExecutor<DriverEntity> {

    @Query("SELECT D FROM DriverEntity D WHERE D.onlineStatus = :#{#onlineStatus} AND D.deleted = false")
    List<DriverEntity> findByOnlineStatus(@Param("onlineStatus") OnlineStatus onlineStatus);

    @Query("SELECT D FROM DriverEntity D WHERE D.id = :#{#id} AND D.deleted = false")
    Optional<DriverEntity> findById(@Param("id") Long id);
}

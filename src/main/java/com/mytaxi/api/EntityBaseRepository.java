package com.mytaxi.api;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityBaseRepository<E extends AbstractEntity> extends JpaRepository<E, Long> {

}

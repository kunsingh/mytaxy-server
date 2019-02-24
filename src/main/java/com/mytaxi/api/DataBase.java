package com.mytaxi.api;

import java.io.Serializable;

/**
 * Base interface for dtos
 */
public interface DataBase extends Serializable {

    Long getId();

    void setId(Long id);
}

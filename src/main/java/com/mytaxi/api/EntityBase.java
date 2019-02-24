package com.mytaxi.api;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * Base interface for entities
 */
@MappedSuperclass
public interface EntityBase extends Serializable {

    Long getId();
    void setId(Long id);
}

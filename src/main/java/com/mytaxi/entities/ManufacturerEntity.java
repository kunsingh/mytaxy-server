package com.mytaxi.entities;

import com.mytaxi.api.AbstractEntity;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(
        name = "manufacturer",
        uniqueConstraints = @UniqueConstraint(name = "uc_name", columnNames = {"name"})
)
public class ManufacturerEntity extends AbstractEntity
{

    @Column(nullable = false, name = "date_created")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column
    private String name;

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

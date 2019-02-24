package com.mytaxi.entities;

import com.mytaxi.api.AbstractEntity;
import com.mytaxi.entities.values.GeoCoordinate;
import com.mytaxi.entities.values.OnlineStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Table(
        name = "driver",
        uniqueConstraints = @UniqueConstraint(name = "uc_username", columnNames = {"username"})
)
public class DriverEntity extends AbstractEntity {


    @Column(nullable = false)
    @NotNull(message = "Username can not be null!")
    private String username;

    @Column(nullable = false)
    @NotNull(message = "Password can not be null!")
    private String password;

    @OneToOne
    @JoinColumn(name="car_id")
    private CarEntity car;

    @Embedded
    private GeoCoordinate coordinate;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCoordinateUpdated = ZonedDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OnlineStatus onlineStatus;


    private DriverEntity() {
    }


    public DriverEntity(String username, String password) {
        this.username = username;
        this.password = password;
        setDeleted(false);
        this.coordinate = null;
        this.dateCoordinateUpdated = null;
        this.onlineStatus = OnlineStatus.OFFLINE;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public OnlineStatus getOnlineStatus() {
        return onlineStatus;
    }


    public void setOnlineStatus(OnlineStatus onlineStatus) {
        this.onlineStatus = onlineStatus;
    }


    public GeoCoordinate getCoordinate() {
        return coordinate;
    }



    public void setCoordinate(GeoCoordinate coordinate) {
        this.coordinate = coordinate;
        this.dateCoordinateUpdated = ZonedDateTime.now();
    }

    public CarEntity getCar() {
        return car;
    }

    public void setCar(CarEntity car) {
        this.car = car;
    }
}

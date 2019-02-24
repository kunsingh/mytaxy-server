package com.mytaxi.datatransferobjects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mytaxi.api.AbstractData;
import com.mytaxi.entities.values.GeoCoordinate;
import com.mytaxi.entities.values.OnlineStatus;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Driver extends AbstractData{

    @NotNull(message = "Username can not be null!")
    private String username;

    @NotNull(message = "Password can not be null!")
    private String password;

    private GeoCoordinate coordinate;

    private OnlineStatus onlineStatus;

    @JsonIgnore
    private Car assignedCar;


    private Driver()
    {
    }


    private Driver(Long id, String username, String password, GeoCoordinate coordinate,
                   OnlineStatus onlineStatus, Car assignedCar)
    {
        setId(id);
        this.username = username;
        this.password = password;
        this.coordinate = coordinate;
        this.onlineStatus = onlineStatus;
        this.assignedCar = assignedCar;
    }


    public static DriverBuilder newBuilder()
    {
        return new DriverBuilder();
    }


    public String getUsername()
    {
        return username;
    }


    public String getPassword()
    {
        return password;
    }


    public GeoCoordinate getCoordinate()
    {
        return coordinate;
    }

    public OnlineStatus getOnlineStatus() {
        return onlineStatus;
    }


    public Car getAssignedCar() {
        return assignedCar;
    }

    public void setAssignedCar(Car assignedCar) {
        this.assignedCar = assignedCar;
    }

    public static class DriverBuilder
    {
        private Long id;
        private String username;
        private String password;
        private GeoCoordinate coordinate;
        private OnlineStatus onlineStatus;
        private Car assignedCar;

        public DriverBuilder setId(Long id)
        {
            this.id = id;
            return this;
        }

        public DriverBuilder setUsername(String username)
        {
            this.username = username;
            return this;
        }

        public DriverBuilder setPassword(String password)
        {
            this.password = password;
            return this;
        }

        public DriverBuilder setCoordinate(GeoCoordinate coordinate)
        {
            this.coordinate = coordinate;
            return this;
        }

        public DriverBuilder setOnlineStatus(OnlineStatus onlineStatus){
            this.onlineStatus = onlineStatus;
            return this;
        }

        public DriverBuilder setAssignedCar(Car assignedCar)
        {
            this.assignedCar = assignedCar;
            return this;
        }

        public Driver createDriver()
        {
            return new Driver(id, username, password, coordinate, onlineStatus, assignedCar);
        }

    }
}

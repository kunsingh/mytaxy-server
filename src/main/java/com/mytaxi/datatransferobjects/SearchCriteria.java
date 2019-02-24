package com.mytaxi.datatransferobjects;

import com.mytaxi.datatransferobjects.values.CarSearchData;
import com.mytaxi.datatransferobjects.values.DriverSearchData;

public class SearchCriteria {
    private CarSearchData carSearchData;
    private DriverSearchData driverSearchData;

    public SearchCriteria(){

    }
    public SearchCriteria(CarSearchData carSearchData, DriverSearchData driverSearchData) {
        this.carSearchData = carSearchData;
        this.driverSearchData = driverSearchData;
    }

    public CarSearchData getCarSearchData() {
        return carSearchData;
    }

    public void setCarSearchData(CarSearchData carSearchData) {
        this.carSearchData = carSearchData;
    }

    public DriverSearchData getDriverSearchData() {
        return driverSearchData;
    }

    public void setDriverSearchData(DriverSearchData driverSearchData) {
        this.driverSearchData = driverSearchData;
    }
}

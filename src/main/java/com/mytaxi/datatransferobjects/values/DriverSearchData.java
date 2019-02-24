package com.mytaxi.datatransferobjects.values;

import com.mytaxi.entities.values.OnlineStatus;

public class DriverSearchData {
    private String username;
    private OnlineStatus onlineStatus;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public OnlineStatus getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(OnlineStatus onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
}

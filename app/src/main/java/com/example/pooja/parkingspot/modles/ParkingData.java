package com.example.pooja.parkingspot.modles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParkingData {
    @SerializedName("userId")
    @Expose
    String userId;
    @SerializedName("blockId")
    @Expose
    String blockId;
    @SerializedName("sensorId")
    @Expose
    String sensorId;

    public ParkingData(String userId, String blockId, String sensorId) {
        this.userId = userId;
        this.blockId = blockId;
        this.sensorId = sensorId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

}

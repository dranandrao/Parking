package com.example.pooja.parkingspot.modles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class SensorInfo {
    @SerializedName("id")
    @Expose
    String sensorId;
    @SerializedName("parked")
    @Expose
    int parked;

    public SensorInfo(String sensorId, int parked) {
        this.sensorId = sensorId;
        this.parked = parked;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public int isParked() {
        return parked;
    }

    public void setParked(int parked) {
        this.parked = parked;
    }

    @NonNull
    @Override
    public String toString() {
        return "sensorId :" + this.getSensorId() + "parked :" + this.isParked();
    }
}

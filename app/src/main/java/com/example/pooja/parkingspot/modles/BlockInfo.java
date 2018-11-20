package com.example.pooja.parkingspot.modles;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BlockInfo {
    @SerializedName("id")
    String id;
    @SerializedName("blockId")
    String blockId;
    @SerializedName("locationName")
    String locationName;
    @SerializedName("price")
    String price;
    @SerializedName("location")
    String location;
    @SerializedName("sensorId")
    String sensorId;

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }


    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

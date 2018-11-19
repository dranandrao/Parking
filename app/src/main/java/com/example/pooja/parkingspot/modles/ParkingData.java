package com.example.pooja.parkingspot.modles;

import com.google.gson.annotations.SerializedName;

public class ParkingData {
    @SerializedName("blockId")
    String blockId;
    @SerializedName("parkingName")
    String parkingName;
    @SerializedName("price")
    String price;

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }


    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}

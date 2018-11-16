package com.example.pooja.parkingspot.modles;

public class ParkingInfo {

    public  ParkingInfo(String parking_name,String parking_status) {
        this.parkng_name = parking_name;
        this.parking_status = parking_status;
    }

    private String parkng_name;
    private String parking_status;

    public String getParkng_name() {
        return parkng_name;
    }

    public String getParking_status() {
        return parking_status;
    }

}

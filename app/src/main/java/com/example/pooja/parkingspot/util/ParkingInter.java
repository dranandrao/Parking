package com.example.pooja.parkingspot.util;

import com.example.pooja.parkingspot.modles.ParkingData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ParkingInter {

    @GET("/findParking.php")
    Call<List<ParkingData>> listParking(@Query("blockId") String blockId);

}

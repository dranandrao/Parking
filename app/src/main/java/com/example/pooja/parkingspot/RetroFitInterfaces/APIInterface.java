package com.example.pooja.parkingspot.RetroFitInterfaces;


import com.example.pooja.parkingspot.modles.ParkingData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("/demo.php")
    Call<List<ParkingData>> getParkingDetails(@Query("blockId") String blockId);

}

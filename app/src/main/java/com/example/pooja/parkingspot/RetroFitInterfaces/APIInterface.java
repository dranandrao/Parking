package com.example.pooja.parkingspot.RetroFitInterfaces;


import com.example.pooja.parkingspot.modles.BlockInfo;
import com.example.pooja.parkingspot.modles.ParkingData;
import com.example.pooja.parkingspot.modles.SensorInfo;
import com.example.pooja.parkingspot.modles.UserData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("/block_information.php")
    Call<List<BlockInfo>> getParkingDetails(@Query("blockId") String blockId);

    @POST("/book_parking.php")
    @FormUrlEncoded
    Call<Void> updateSensorData(@Field("id") String sensorId, @Field("parked") String parked);

    @POST("/parkingdata_insert.php")
    @FormUrlEncoded
    Call<Void> addParkingData(@Field("userId") String userId, @Field("blockId") String blockId, @Field("sensorId") String sensorId);

    @POST("/user_auth.php")
    @FormUrlEncoded
    Call<UserData> userAuthentication(@Field("username") String username, @Field("password") String password);

    @GET("/get_complete_block_details.php")
    Call<List<BlockInfo>> getBlocksInfo();
}

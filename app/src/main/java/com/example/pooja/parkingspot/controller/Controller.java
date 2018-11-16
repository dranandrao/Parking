package com.example.pooja.parkingspot.controller;

import android.util.Log;

import com.example.pooja.parkingspot.modles.ParkingData;
import com.example.pooja.parkingspot.util.ParkingInter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Controller implements Callback<List<ParkingData>> {

    static final String BASE_URL = "http://100.97.174.46/";
    private String blockId;
    private final static String TAG = "Controller";
    private boolean responseSuccessful = false;

    private List<ParkingData> parkingList;

    public Controller(String blockId){
        this.blockId = blockId;
    }

    public void start() {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();

        ParkingInter parkingInter = retrofit.create(ParkingInter.class);

        Call<List<ParkingData>> call = parkingInter.listParking(blockId);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<ParkingData>> call, Response<List<ParkingData>> response) {
        if (response.isSuccessful()) {
            responseSuccessful = true;
            parkingList = response.body();
            for (ParkingData parkingData : parkingList) {
                Log.d(TAG,"blockId :" + parkingData.getBlockId() + ", sensorId :" + parkingData.getSensorId() + "\n");
            }
        } else {
            Log.d(TAG,response.errorBody().toString());
        }
    }

    @Override
    public void onFailure(Call<List<ParkingData>> call, Throwable t) {
        t.printStackTrace();
    }

    public List<ParkingData> getParkingList() {
        if(responseSuccessful){
            return parkingList;
        } else {
            return null;
        }

    }

}

package com.example.pooja.parkingspot;

import android.app.Application;
import android.media.AsyncPlayer;

import com.example.pooja.parkingspot.RetroFitInterfaces.APIClient;
import com.example.pooja.parkingspot.RetroFitInterfaces.APIInterface;
import com.example.pooja.parkingspot.modles.UserData;

public class MyApplication extends Application {
    private UserData userData;
    private APIInterface apiInterface;
    public MyApplication(){
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public APIInterface getApiInterface() {
        return apiInterface;
    }

    public void setApiInterface(APIInterface apiInterface) {
        this.apiInterface = apiInterface;
    }


}

package com.example.pooja.parkingspot.modles;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class UserData {
    @SerializedName("username")
    String username;
    @SerializedName("name")
    String name;
    @SerializedName("password")
    String password;
    @SerializedName("role")
    String role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @NonNull
    @Override
    public String toString() {
        return "sensorId :" + this.getUsername() + "parked :" + this.getName();
    }
}

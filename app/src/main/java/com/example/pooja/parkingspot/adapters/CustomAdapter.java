package com.example.pooja.parkingspot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pooja.parkingspot.R;
import com.example.pooja.parkingspot.modles.ParkingInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.internal.Util;

public class CustomAdapter extends ArrayAdapter<ParkingInfo> {
    private ArrayList<ParkingInfo> parkingList;
    private Context mContext;


    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<ParkingInfo> objects) {
        super(context, resource, objects);
        this.parkingList = (ArrayList<ParkingInfo>) objects;
        this.mContext = context;
    }

    private static class ViewHolder {
        TextView parking_name;
        TextView parking_status;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ParkingInfo parkingInfo = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.parking_info, parent, false);
            viewHolder.parking_name = (TextView) convertView.findViewById(R.id.place_name);
            viewHolder.parking_status = (TextView) convertView.findViewById(R.id.parking_status);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.parking_name.setText(parkingInfo.getParkng_name());
        viewHolder.parking_status.setText(Util.format("Parking available : " + parkingInfo.getParking_status()));
        viewHolder.parking_name.setTag(position);


        return convertView;
    }
}

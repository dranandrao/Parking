package com.example.pooja.parkingspot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pooja.parkingspot.R;
import com.example.pooja.parkingspot.modles.ParkingData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.internal.Util;

public class CustomAdapter extends ArrayAdapter<ParkingData> {
    private ArrayList<ParkingData> parkingList;
    private Context mContext;


    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<ParkingData> objects) {
        super(context, resource, objects);
        this.parkingList = (ArrayList<ParkingData>) objects;
        this.mContext = context;
    }

    private static class ViewHolder {
        TextView parking_name;
        TextView parking_available;
        TextView price;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ParkingData parkingData = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.parking_info, parent, false);
            viewHolder.parking_name = (TextView) convertView.findViewById(R.id.place_name);
            viewHolder.parking_available = (TextView) convertView.findViewById(R.id.parking_available);
            viewHolder.price = (TextView)convertView.findViewById(R.id.price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.parking_name.setText(Util.format("Parking name : "+ parkingData.getParkingName()));
        viewHolder.parking_available.setText(Util.format("Parking available : " + parkingData.getBlockId()));
        viewHolder.price.setText(Util.format("Price :")+ parkingData.getPrice() + "$ / hr");
        viewHolder.parking_name.setTag(position);


        return convertView;
    }
}

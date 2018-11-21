package com.example.pooja.parkingspot.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pooja.parkingspot.R;
import com.example.pooja.parkingspot.modles.BlockInfo;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.internal.Util;

public class CustomAdapter extends ArrayAdapter<BlockInfo> {
    private ArrayList<BlockInfo> parkingList;
    private Context mContext;


    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<BlockInfo> objects) {
        super(context, resource, objects);
        this.parkingList = (ArrayList<BlockInfo>) objects;
        this.mContext = context;
    }

    private static class ViewHolder {
        TextView lcoation_Name;
        TextView parking_available;
        TextView price;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BlockInfo blockInfo = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.parking_info, parent, false);
            viewHolder.lcoation_Name = (TextView) convertView.findViewById(R.id.location_Name);
            viewHolder.parking_available = (TextView) convertView.findViewById(R.id.parking_available);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.lcoation_Name.setText(Util.format("Parking name : " + blockInfo.getLocationName()));
        viewHolder.parking_available.setText(Util.format("Parking available : " + 50));
        viewHolder.price.setText(Util.format("Price :") + " $ " + blockInfo.getPrice() + " / hr");
        viewHolder.lcoation_Name.setTag(position);


        return convertView;
    }
}

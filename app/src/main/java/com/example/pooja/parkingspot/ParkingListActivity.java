package com.example.pooja.parkingspot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.pooja.parkingspot.adapters.CustomAdapter;
import com.example.pooja.parkingspot.modles.ParkingInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParkingListActivity extends AppCompatActivity {

    @BindView(R.id.parkingList)
    ListView parkingList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ArrayList<ParkingInfo> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_parking);

        ButterKnife.bind(this);

        toolbar.setTitle("Choose parking");
        arrayList = new ArrayList<>();
        final String[] parkingLotNames = getResources().getStringArray(R.array.places);
        String[] parkingStatus = getResources().getStringArray(R.array.status);
        for (int i = 0; i < ((String[]) parkingLotNames).length; i++) {
            arrayList.add(new ParkingInfo(parkingLotNames[i], parkingStatus[i]));
        }

        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.parking_info, arrayList);
        parkingList.setAdapter(customAdapter);

        parkingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ParkingInfo parkingInfo = arrayList.get(position);
                Intent intent = new Intent(getApplicationContext(), BookingActivity.class);
                intent.putExtra("blockId", parkingInfo.getParkng_name());
                startActivity(intent);
            }
        });
    }
}

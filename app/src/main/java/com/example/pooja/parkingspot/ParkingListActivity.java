package com.example.pooja.parkingspot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.pooja.parkingspot.RetroFitInterfaces.APIClient;
import com.example.pooja.parkingspot.RetroFitInterfaces.APIInterface;
import com.example.pooja.parkingspot.adapters.CustomAdapter;
import com.example.pooja.parkingspot.modles.BlockInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingListActivity extends AppCompatActivity {

    @BindView(R.id.parkingList)
    ListView parkingList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    LinearLayout linearLayout;

    private ArrayList<BlockInfo> arrayList;
    private APIInterface apiInterface;
    PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_parking);

        ButterKnife.bind(this);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        toolbar.setTitle("Choose parking");


        final String blockId = getIntent().getStringExtra("blockId");

        if (String.valueOf(blockId).isEmpty()) {
            showPopUP();
        }

        Call<List<BlockInfo>> apiCall = apiInterface.getParkingDetails(blockId);
        apiCall.enqueue(new Callback<List<BlockInfo>>() {
            @Override
            public void onResponse(Call<List<BlockInfo>> call, Response<List<BlockInfo>> response) {
                arrayList = (ArrayList<BlockInfo>) response.body();
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), R.layout.parking_info, arrayList);
                parkingList.setAdapter(customAdapter);
            }

            @Override
            public void onFailure(Call<List<BlockInfo>> call, Throwable t) {
                showPopUP();
            }
        });


        parkingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BlockInfo blockInfo = arrayList.get(position);
                Intent intent = new Intent(getApplicationContext(), BookingActivity.class);
                intent.putExtra("blockId", blockInfo.getId());//We need only Id,not blockId
                intent.putExtra("sensorId", blockInfo.getSensorId());
                intent.putExtra("parkingName", blockInfo.getLocationName());
                startActivity(intent);
            }
        });
    }

    private void showPopUP() {
        LayoutInflater layoutInflater = (LayoutInflater) ParkingListActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.pop_up_window, null);
        popupWindow = new PopupWindow(customView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        findViewById(R.id.container).post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(findViewById(R.id.container), Gravity.CENTER, 0, 0);
            }
        });


        Button tryAgain = (Button) customView.findViewById(R.id.try_again);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button cancel = (Button) customView.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}

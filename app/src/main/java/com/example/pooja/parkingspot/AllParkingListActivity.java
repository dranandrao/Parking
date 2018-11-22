package com.example.pooja.parkingspot;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

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

public class AllParkingListActivity extends AppCompatActivity {
    @BindView(R.id.parkingList)
    ListView parkingList;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    LinearLayout linearLayout;
    PopupWindow popupWindow;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer)
    DrawerLayout drawerLayout;
    private int selectedMenuItemId;

    private ArrayList<BlockInfo> arrayList;
    private APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_parking_list);

        ButterKnife.bind(this);
        apiInterface = ((MyApplication) this.getApplication()).getApiInterface();
        toolbar.setTitle("Parking List");

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                int mPos = menuItem.getItemId();
                switch (mPos) {
                    case R.id.home:
                        selectedMenuItemId = R.id.home;
                        return true;
                    case R.id.my_profile:
                        selectedMenuItemId = R.id.my_profile;
                        startActivity(new Intent(AllParkingListActivity.this, ProfileActivity.class));
                        return true;
                    case R.id.logout:
                        selectedMenuItemId = R.id.logout;
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        return true;
                }
                return false;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getDataFromServer();

        parkingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BlockInfo blockInfo = arrayList.get(position);
                Intent intent = new Intent(getApplicationContext(), UpdatePriceActivity.class);
                intent.putExtra("blockId", blockInfo.getId());//We need only Id,not blockId
                intent.putExtra("parkingName", blockInfo.getLocationName());
                startActivity(intent);
            }
        });


    }

    private void showPopUP() {
        LayoutInflater layoutInflater = (LayoutInflater) AllParkingListActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
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
                //TODO try again feature.
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

    private void getDataFromServer() {
        Call<List<BlockInfo>> apiCall = apiInterface.getBlocksInfo();
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (apiInterface != null) {
            getDataFromServer();
        }
    }
}

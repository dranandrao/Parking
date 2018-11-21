package com.example.pooja.parkingspot;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pooja.parkingspot.RetroFitInterfaces.APIClient;
import com.example.pooja.parkingspot.RetroFitInterfaces.APIInterface;
import com.example.pooja.parkingspot.util.CustomMarker;
import com.example.pooja.parkingspot.util.CustomXAxisValueFormatter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity implements OnChartValueSelectedListener, DatePickerDialog.OnDateSetListener {
    @BindView(R.id.barchart)
    BarChart barChart;
    @BindView(R.id.book_button)
    Button book_button;
    @BindView(R.id.cancel_button)
    Button cancel_button;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.parking_name)
    TextView parkingName;
    @BindView(R.id.parking_date)
    ImageButton parkingDateButton;

    IMarker customMarker;
    IAxisValueFormatter customValueFormatter;
    private APIInterface apiInterface;
    final static String TAG = "BookingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        ButterKnife.bind(this);
        toolbar.setTitle("Book Parking");
        apiInterface = APIClient.getClient().create(APIInterface.class);

        String[] labels = getResources().getStringArray(R.array.labels);
        customMarker = new CustomMarker(this, R.layout.marker_layout);
        customValueFormatter = new CustomXAxisValueFormatter(labels);
        barChart.setMarker(customMarker);

        parkingName.setText(getIntent().getStringExtra("parkingName"));
        parkingDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(BookingActivity.this, BookingActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        //getting data from the extras.
        final String blockId = getIntent().getStringExtra("blockId");
        final String sensorId = getIntent().getStringExtra("sensorId");


        BarData barData = getBarData();
        barChart.setData(barData);
        barChart.setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(false);
        barChart.setHighlightFullBarEnabled(true);
        barChart.setDoubleTapToZoomEnabled(false);

        //Modifying yAxisLeft.
        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setDrawLabels(false);
        yAxisLeft.setEnabled(true);

        //Modifying yAxisRight.
        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setDrawLabels(false);
        yAxisRight.setEnabled(false);

        //Modifying xAxis.
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(customValueFormatter);
        xAxis.setGranularity(3f);
        xAxis.setSpaceMin(0.3f);

        barChart.invalidate();

        book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> sensorUpdate = apiInterface.updateSensorData(sensorId, "1");
                final Call<Void> addParkingData = apiInterface.addParkingData("1", blockId, sensorId);
                sensorUpdate.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            addParkingData.enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    Toast.makeText(getApplicationContext(), "Parking slot booked", Toast.LENGTH_LONG).show();
                                    Log.v(TAG, "Parking data added :" + response.message());
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    Log.v(TAG, " onParking data OnFailure");
                                    t.printStackTrace();
                                    call.cancel();
                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.v(TAG, " sensorUpdate OnFailure");
                        t.printStackTrace();
                        call.cancel();
                    }
                });


            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private BarData getBarData() {
        List<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1f, 2f));
        barEntries.add(new BarEntry(2f, 3f));
        barEntries.add(new BarEntry(3f, 20f));
        barEntries.add(new BarEntry(4f, 30f));
        barEntries.add(new BarEntry(5f, 10f));
        barEntries.add(new BarEntry(6f, 15f));
        barEntries.add(new BarEntry(7f, 6f));
        barEntries.add(new BarEntry(8f, 8f));
        barEntries.add(new BarEntry(9f, 4f));
        barEntries.add(new BarEntry(10f, 2f));
        barEntries.add(new BarEntry(11f, 3f));
        barEntries.add(new BarEntry(12f, 3f));


        BarDataSet barDataSet = new BarDataSet(barEntries, "Price");
        barDataSet.setColor(getResources().getColor(R.color.graphColor));

        BarData barData = new BarData(barDataSet);
        barData.setBarWidth(0.5f);
        barData.setDrawValues(false);

        return barData;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //TODO implement refreshing of the graph.
        //TODO book parking to that (year,month,dayOfMonth).
    }
}
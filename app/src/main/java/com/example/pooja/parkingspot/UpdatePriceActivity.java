package com.example.pooja.parkingspot;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePriceActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    @BindView(R.id.barchart)
    BarChart barChart;
    @BindView(R.id.update_button)
    Button update_button;
    @BindView(R.id.cancel_button)
    Button cancel_button;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.parking_name)
    TextView parkingName;
    @BindView(R.id.parking_date)
    ImageButton parkingDateButton;
    @BindView(R.id.priceSpinner)
    Spinner priceSpinner;

    IMarker customMarker;
    IAxisValueFormatter customValueFormatter;
    private APIInterface apiInterface;
    final static String TAG = "UpdatePriceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_price);

        ButterKnife.bind(this);
        toolbar.setTitle("Book Parking");
        apiInterface = ((MyApplication) this.getApplication()).getApiInterface();

        String[] labels = getResources().getStringArray(R.array.labels);
        customMarker = new CustomMarker(this, R.layout.marker_layout);
        customValueFormatter = new CustomXAxisValueFormatter(labels);
        barChart.setMarker(customMarker);

        parkingName.setText(getIntent().getStringExtra("parkingName"));

        parkingDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdatePriceActivity.this, UpdatePriceActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        //getting data from the extras.
        final String blockId = getIntent().getStringExtra("blockId");

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
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //getting prices from string file.
        List<String> prices = new ArrayList<String>();
        String[] pricesArray = getResources().getStringArray(R.array.prices);
        Collections.addAll(prices, pricesArray);

        //Setting spinner adapter.
        final ArrayAdapter<String> priceAdapter = new ArrayAdapter<String>(UpdatePriceActivity.this, android.R.layout.simple_spinner_item, prices);
        priceSpinner.setAdapter(priceAdapter);

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = (String) priceSpinner.getSelectedItem();
                apiInterface.updateParkingPrice(blockId, price).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getApplicationContext(),getResources().getText(R.string.price_updated),Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

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
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

}

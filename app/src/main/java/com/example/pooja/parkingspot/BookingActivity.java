package com.example.pooja.parkingspot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;


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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookingActivity extends AppCompatActivity implements OnChartValueSelectedListener {
    @BindView(R.id.barchart)
    BarChart barChart;
    @BindView(R.id.book_button)
    Button book_button;
    @BindView(R.id.cancel_button)
    Button cancel_button;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    IMarker customMarker;
    IAxisValueFormatter customValueFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        ButterKnife.bind(this);
        String[] labels = getResources().getStringArray(R.array.labels);
        toolbar.setTitle("Book Parking");
        customMarker = new CustomMarker(this, R.layout.marker_layout);
        customValueFormatter = new CustomXAxisValueFormatter(labels);
        barChart.setMarker(customMarker);

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
        barEntries.add(new BarEntry(6f, 7f));
        barEntries.add(new BarEntry(7f, 6f));
        barEntries.add(new BarEntry(8f, 8f));
        barEntries.add(new BarEntry(9f, 4f));
        barEntries.add(new BarEntry(10f, 1f));


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

}
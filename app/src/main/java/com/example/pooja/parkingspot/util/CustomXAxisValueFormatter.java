package com.example.pooja.parkingspot.util;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class CustomXAxisValueFormatter implements IAxisValueFormatter {
    private String[] mValues;

    public CustomXAxisValueFormatter(String[] mValues) {
        this.mValues = mValues;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mValues[(int)value];
    }
}

package com.example.pooja.parkingspot.util;

import android.content.Context;
import android.widget.TextView;

import com.example.pooja.parkingspot.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import okhttp3.internal.Util;

public class CustomMarker extends MarkerView {
    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    private TextView txContent;

    public CustomMarker(Context context, int layoutResource) {
        super(context, layoutResource);
        txContent = (TextView) findViewById(R.id.txContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        BarEntry barEntry = (BarEntry) e;
        txContent.setText(Util.format("$ " +String.valueOf(barEntry.getY()), 0, true));

        super.refreshContent(e, highlight);

    }

    private MPPointF mpPointF;

    @Override
    public MPPointF getOffset() {
        if (mpPointF == null) {
            mpPointF = new MPPointF(-(getWidth() / 2), -getHeight());
        }
        return mpPointF;
    }
}

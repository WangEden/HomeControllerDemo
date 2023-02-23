package com.example.demo.fragment.monitor.scene;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class MyXFormatter extends ValueFormatter {

    private String[] mValues;

    public MyXFormatter(String[] values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return mValues[(int) value];
    }
}

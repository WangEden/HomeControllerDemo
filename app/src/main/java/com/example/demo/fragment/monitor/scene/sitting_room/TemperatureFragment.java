package com.example.demo.fragment.monitor.scene.sitting_room;


import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.demo.MyApplication;
import com.example.demo.R;
import com.example.demo.base.BaseFragment;
import com.example.demo.diy_view.CustomSpinner;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class TemperatureFragment extends BaseFragment {

    private LineChart mChart;
    private List<Entry> entries1;
    private List<Entry> entries2;
    private LineDataSet lineDay;
    private LineDataSet lineWeek;
    private XAxis xAxis;
    private final int DATA_BY_DAY = 0;
    private final int DATA_BY_WEEK = 1;
    private int option = 0;
    private static final String[] labelDay = {
            " ", "00:00", "06:00", "12:00", "18:00", "24:00", " "
    };
    private static final String[] labelWeek = {
            "Mo", "Tu", "Wed", "Th", "Fr", "Sa", "Su"
    };

    // 测试用数据
    private static final float[] dataByDay = {
            14f, 15f, 12f, 13f, 16f, 14f,
            17f, 19f, 21f, 24f, 26f, 23f,
            20f, 19f, 22f, 18f, 16f, 13f
    };
    private static final float[] dataByWeek = {
            20f, 24f, 26f, 23f, 27f, 20f, 25f
    };
    private Legend legend;

    @Override
    protected void initViews() {
        setChartData();
        initLineChart();
        setChartMode();
        updateChart();

        {
            CustomSpinner spinner = find(R.id.spinner_data_way_selector);
            ImageView imgDataStatisticWay = find(R.id.img_data_statistic_way);
            // 设置数据源
            String[] mDataWay = {"按天统计", "按周统计"};
            // 设置初始状态下的spinnerItem
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(MyApplication.getInstance(), R.layout.item_spinner_data_selector, mDataWay);
            // 设置下拉菜单中的spinnerItem
            spinnerAdapter.setDropDownViewResource(R.layout.item_spinner_data_selector);
            spinner.setAdapter(spinnerAdapter);

            spinner.setBackgroundColor(0x000000);
            spinner.setPopupBackgroundResource(R.drawable.round_shape);
            spinner.setDropDownVerticalOffset(100);

            // 监听展开事件，决定箭头是否旋转
            spinner.setSpinnerEventsListener(new CustomSpinner.OnSpinnerEventsListener() {
                @Override
                public void onSpinnerOpened() {
                    imgDataStatisticWay.animate().setDuration(300).rotation(90).start();
                }

                @Override
                public void onSpinnerClosed() {
                    imgDataStatisticWay.animate().setDuration(300).rotation(0).start();
                }
            });

            // 监听被选中的选项
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case DATA_BY_DAY:
                            // TODO
                            option = 0;
                            break;
                        case DATA_BY_WEEK:
                            // TODO
                            option = 1;
                            break;
                    }
                    // 刷新图线
                    updateChart();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // TODO

                }
            });

        }
    }

    private void initLineChart() {
        mChart = find(R.id.line_chart_temperature_sitting_room);
        lineDay = new LineDataSet(entries1, "日温度");
        lineWeek = new LineDataSet(entries2, "周温度");
    }

    private void setChartData() {
        entries1 = new ArrayList<>();
        entries2 = new ArrayList<>();
        // 测试用数据
        for (int i = 0; i < dataByDay.length; i++) {
            entries1.add(new Entry(i, dataByDay[i]));
        }

        for (int i = 0; i < dataByWeek.length; i++) {
            entries2.add(new Entry(i, dataByWeek[i]));
        }
    }

    private void setChartMode() {
        // 关闭右轴
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        YAxis yAxis = mChart.getAxisLeft();
        // 网格横线
        yAxis.setDrawGridLines(false);

        // 设置横坐标
        xAxis = mChart.getXAxis();
        // 设置横坐标标签文字颜色
        xAxis.setTextColor(Color.parseColor("#9A000000"));
        // 设置横坐标标签文字大小
        xAxis.setTextSize(15f);
        // 设置横轴线是否显示
        xAxis.setDrawAxisLine(true);
        // 设置横轴网格线是否显示
        xAxis.setDrawGridLines(false);
        //
        xAxis.setDrawLabels(true);
        // 设置横轴位于底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAvoidFirstLastClipping(false);

        // 日温度线样式
        // 设置该图线颜色
        lineDay.setColor(Color.parseColor("#EE6002"));
        // 设置折线上有无转折点
        lineDay.setDrawCircles(true);
        // 设置图线的宽度
        lineDay.setLineWidth(3f);
        // 设置图线上数值的颜色
        lineDay.setValueTextColor(Color.parseColor("#EE6002"));
        // 设置图线上数值数字大小
        lineDay.setValueTextSize(0);

        // 周温度线样式
        // 设置该图线颜色
        lineWeek.setColor(Color.parseColor("#6200EE"));
        // 设置折线上有无转折点
        lineWeek.setDrawCircles(true);
        // 设置图线的宽度
        lineWeek.setLineWidth(3f);
        // 设置图线上数值的颜色
        lineWeek.setValueTextColor(Color.parseColor("#6200EE"));
        // 设置图线上数值数字大小
        lineWeek.setValueTextSize(0);

        // 让描述为空
//        mChart.setDescription("");

        // 背景色
        mChart.setBackgroundColor(Color.parseColor("#CAFFFFFF"));
//        mChart.setBackgroundResource(R.drawable.ic_scene_data_panel_background);
        // 网格背景色
        mChart.setGridBackgroundColor(Color.parseColor("#CAFFFFFF"));
        mChart.setHorizontalScrollBarEnabled(false);
        mChart.setTouchEnabled(false);

        // 设置图线参考标签
        legend = mChart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setFormSize(15f);
        legend.setTextSize(15f);

    }

    private void updateChart() {
        if(option == 0){
            // 设置横坐标标签数据
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return labelDay[(int)(value / 3)];
                }
            });

            // 设置图例颜色
            legend.setTextColor(Color.parseColor("#EE6002"));

            LineData lineDateDay = new LineData(lineDay);
            mChart.setData(lineDateDay);
        }
        else if(option == 1){
            // 设置横坐标标签数据
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return labelWeek[(int)(value)];
                }
            });

            // 设置图例颜色
            legend.setTextColor(Color.parseColor("#6200EE"));

            LineData lineDateWeek = new LineData(lineWeek);
            mChart.setData(lineDateWeek);
        }
        mChart.animateX(400, Easing.EaseInBack);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_temperature;
    }
}
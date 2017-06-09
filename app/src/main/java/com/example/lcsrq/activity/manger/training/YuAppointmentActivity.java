package com.example.lcsrq.activity.manger.training;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.example.lcsrq.adapter.AppointmentAdapter;
import com.example.lcsrq.base.BaseActivity;

/**
 * 预约考试
 */

public class YuAppointmentActivity extends BaseActivity {

    private ListView lv_appointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yu_appointment);
    }

    @Override
    protected void addAction() {

    }

    @Override
    protected void findViews() {

        ImageView left_btn = (ImageView) findViewById(R.id.left_btn);
        left_btn.setVisibility(View.VISIBLE);
        TextView commonTitleTv = (TextView) findViewById(R.id.commonTitleTv);
        commonTitleTv.setText("选题预约");

        lv_appointment = (ListView) findViewById(R.id.lv_appointment);

        AppointmentAdapter appointmentAdapter = new AppointmentAdapter(this);
        lv_appointment.setAdapter(appointmentAdapter);
    }

    @Override
    public void onClick(View v) {

    }
}

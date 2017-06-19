package com.example.lcsrq.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

public class TimerCount extends CountDownTimer {
   private TextView v;
    public TimerCount(long millisInFuture, long countDownInterval, TextView v) {
        super(millisInFuture, countDownInterval);
        this.v=v;
    }

    @Override
    public void onFinish() {
        v.setText("发送验证码");
        v.setClickable(true);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        v.setClickable(false);
        v.setText("           " +millisUntilFinished / 1000 + "s" + "   ");
    }
}
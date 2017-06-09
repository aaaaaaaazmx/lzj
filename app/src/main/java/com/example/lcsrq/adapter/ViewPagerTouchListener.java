package com.example.lcsrq.adapter;

import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 苏毅 on 2017/4/21.
 */

public class ViewPagerTouchListener implements View.OnTouchListener {

    private Handler handler;
    private  Message msggg = new Message();
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("按下");
                msggg.arg2 = 4;
                handler.removeCallbacksAndMessages(null);// 删除Handler中的所有消息
                // mHandler.postDelayed(new Runnable() {
                //
                // @Override
                // public void run() {
                //
                // }
                // }, 3000);
                break;
            case MotionEvent.ACTION_CANCEL:
                System.out.println("事件取消");
                msggg.arg2 = 4;
                handler.sendMessageDelayed(msggg,3000);

//                    handler.sendEmptyMessageDelayed(0, 3000);
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("抬起");
                msggg.arg2 = 4;
                handler.sendMessageDelayed(msggg,3000);
//                    handler.sendEmptyMessageDelayed(0, 3000);
                break;

            default:
                break;
        }

        return true;
    }
}

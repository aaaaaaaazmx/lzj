package com.example.lcsrq.xiangce;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.example.lcsrq.crame.CustomDialog;
import com.example.lcsrq.crame.StringTool;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 苏毅 on 2017/4/13.
 */

public class UiTool {

    /**
     * 获取屏幕的参数
     *
     * @param activity
     */
    public static void getScreenConfig(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
    }

    public static void hideKeyboard(Activity context) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            // 得到InputMethodManager的实例
            if (imm.isActive()) {
                // 如果开启
                imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0); // 强制隐藏键盘
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void setDialog(Activity context, Dialog dialog, int position, int animId, double scaleW, double scaleH) {
        Window window = dialog.getWindow();
        window.setGravity(position); // 此处可以设置dialog显示的位置
        if (animId != -1) {
            window.setWindowAnimations(animId); // 添加动画
        }
        dialog.show();
        WindowManager windowManager = context.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * scaleW); // 设置宽度
        if (scaleH > 0) {
            lp.height = (int) (display.getHeight() * scaleH);
        }
        dialog.getWindow().setAttributes(lp);
        dialog.setCancelable(true);

    }

    public static Bitmap revitionImageSize(String path) {
        Bitmap bitmap = null;
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(path)));
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            in.close();
            int i = 0;
            while (true) {
                if ((options.outWidth >> i <= 1000) && (options.outHeight >> i <= 1000)) {
                    in = new BufferedInputStream(new FileInputStream(new File(path)));
                    options.inSampleSize = (int) Math.pow(2.0D, i);
                    options.inJustDecodeBounds = false;
                    bitmap = BitmapFactory.decodeStream(in, null, options);
                    break;
                }
                i += 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return bitmap;
        }
    }


    /**
     * 从给定的路径加载图片，并指定是否自动旋转方向
     */
    public static Bitmap loadBitmap(String imgpath) throws Exception {
        Bitmap bm = revitionImageSize(imgpath);
        int digree = 0;
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imgpath);
        } catch (IOException e) {
            e.printStackTrace();
            exif = null;
        }
        if (exif != null) {
            // 读取图片中相机方向信息
            int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);
            // 计算旋转角度
            switch (ori) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    digree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    digree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    digree = 270;
                    break;
                default:
                    digree = 0;
                    break;
            }
        }
        if (digree != 0) {
            // 旋转图片
            Matrix m = new Matrix();
            m.postRotate(digree);
            bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                    bm.getHeight(), m, true);
        }
        saveBitmap(imgpath, bm);
        return bm;
    }

    public static void showPic(final Context context, String url) {
        if (StringTool.isNotNull(url)) {
            final CustomDialog customDialog = new CustomDialog(context);
                customDialog.bindImgLayout(url);
                UiTool.setDialog((Activity) context, customDialog, Gravity.CENTER, -1, 1, 1);
        }
    }

    public static void showPic(final Context context, String url, SimpleDraweeView.ScaleType scaleType) {
        if (StringTool.isNotNull(url)) {
            final CustomDialog customDialog = new CustomDialog(context);
            customDialog.bindImgLayout(url, scaleType);
            UiTool.setDialog((Activity) context, customDialog, Gravity.CENTER, -1, 1, 1);
        }
    }

    public static void showPic(final Context context, Bitmap bmp) {
        if (bmp != null) {
            final CustomDialog customDialog = new CustomDialog(context);
            customDialog.bindImgLayout(bmp);
            UiTool.setDialog((Activity) context, customDialog, Gravity.CENTER, -1, 1, 1);
        }
    }

    public static void showPic(final Context context, int id) {
        final CustomDialog customDialog = new CustomDialog(context);
        customDialog.bindImgLayout(id);
        UiTool.setDialog((Activity) context, customDialog, Gravity.CENTER, -1, 1, 1);
    }


    public static void saveBitmap(String path, Bitmap bm) {
        File f = new File(path);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

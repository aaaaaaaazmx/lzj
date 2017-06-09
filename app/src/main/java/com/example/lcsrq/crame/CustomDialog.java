package com.example.lcsrq.crame;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lcsrq.R;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 通用提示对话框
 */
public class CustomDialog extends Dialog {
    private Context context;
    private TextView cancleBtn, sureBtn;
    private DismissIF dismissIF;
    private EditText et;

    public CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    public CustomDialog(Context context, int theme) {
        super(context, theme);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        this.context = context;
    }

    public CustomDialog(Context context) {
        super(context, R.style.custom_dialog);// 固定样式
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        this.context = context;
    }

//    //弹出新手指引
//    public void bindLayout(int drawableId, DismissIF dismissIF) {
//        this.dismissIF = dismissIF;
//        LinearLayout pop_layout = (LinearLayout) LayoutInflater.from(context).inflate(
//                R.layout_marker_bitmap.view_popwindow_img, null);
//        pop_layout.setBackgroundResource(drawableId);
//        pop_layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//        addContentView(pop_layout,
//                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//    }

    //弹出网络图片
    public void bindImgLayout(String url) {
        LinearLayout pop_layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_url_img, null);
        SimpleDraweeView img = (SimpleDraweeView) pop_layout.findViewById(R.id.img);
        img.setImageURI(Uri.parse(url));
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        addContentView(pop_layout,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    //弹出网络图片
    public void bindImgLayout(String url, SimpleDraweeView.ScaleType scaleType) {
        LinearLayout pop_layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_url_img, null);
        SimpleDraweeView img = (SimpleDraweeView) pop_layout.findViewById(R.id.img);
        img.setImageURI(Uri.parse(url));
        img.setScaleType(scaleType);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        addContentView(pop_layout,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public void bindImgLayout(Bitmap bmp) {
        LinearLayout pop_layout = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.dialog_url_img, null);
        SimpleDraweeView img = (SimpleDraweeView) pop_layout.findViewById(R.id.img);
        img.setImageBitmap(bmp);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        addContentView(pop_layout,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    //弹出网络图片
    public void bindImgLayout(int id) {
        LinearLayout pop_layout = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.dialog_url_img, null);
        SimpleDraweeView img = (SimpleDraweeView) pop_layout.findViewById(R.id.img);
        img.setImageResource(id);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        addContentView(pop_layout,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

//    //异地登录
//    public void bindLoginLayout() {
//        LinearLayout layout_marker_bitmap = (LinearLayout) LayoutInflater.from(context).inflate(
//                R.layout_marker_bitmap.view_dialog_login, null);
//        layout_marker_bitmap.findViewById(R.id.closeBtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferencesConfig.saveStringConfig(context, "token", "");
//                Class<?> descClass = UiTool.getUiClass((Activity) context, UiConfig.G_UIKEY_PWD_LOGIN);
//                if (descClass == null) {
//                    return;
//                }
//                Intent intent = new Intent(context, descClass);
//                context.startActivity(intent);
//                dismiss();
//                ((Activity) context).finish();
//            }
//        });
//        layout_marker_bitmap.findViewById(R.id.loginBtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Class<?> descClass = UiTool.getUiClass((Activity) context, UiConfig.G_UIKEY_MAIN);
//                if (descClass == null) {
//                    return;
//                }
//                Intent intent = new Intent(context, descClass);
//                context.startActivity(intent);
//                ((Activity) context).finish();
//                dismiss();
//            }
//        });
//        ((TextView) layout_marker_bitmap.findViewById(R.id.txt)).setText("您的账号已在异地登录");
//        addContentView(layout_marker_bitmap,
//                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//    }


//    public void bindListLayout(String[] text, AdapterView.OnItemClickListener listener) {
//        LinearLayout layout_marker_bitmap = (LinearLayout) LayoutInflater.from(context).inflate(
//                R.layout_marker_bitmap.view_dialog_list, null);
//        ListView listView = (ListView) layout_marker_bitmap.findViewById(R.id.list);
//        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout_marker_bitmap.simple_list_item_1, text);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(listener);
//        addContentView(layout_marker_bitmap,
//                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//    }

        //  相册相机弹窗
    public void bindCameraLayout(View.OnClickListener l) {
        View layout = LayoutInflater.from(context).inflate(R.layout.dialog_camera, null);
        Button item_popupwindows_cancel = (Button) layout.findViewById(R.id.item_popupwindows_cancel);
        Button item_popupwindows_two = (Button) layout.findViewById(R.id.item_popupwindows_two);
        Button item_popupwindows_one = (Button) layout.findViewById(R.id.item_popupwindows_one);
        item_popupwindows_cancel.setOnClickListener(l);
        item_popupwindows_two.setOnClickListener(l);
        item_popupwindows_one.setOnClickListener(l);
        layout.findViewById(R.id.parent).setOnClickListener(l);
        addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    //  相册相机弹窗
    public void bindBDMapLayout(View.OnClickListener l) {
        View layout = LayoutInflater.from(context).inflate(R.layout.bdmap_dialog, null);
        Button item_popupwindows_cancel = (Button) layout.findViewById(R.id.item_popupwindows_cancel);
        Button item_popupwindows_two = (Button) layout.findViewById(R.id.item_popupwindows_two);
        item_popupwindows_cancel.setOnClickListener(l);
        item_popupwindows_two.setOnClickListener(l);
        layout.findViewById(R.id.parent).setOnClickListener(l);
        addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    //  输入框弹窗
    public void bindEdittextLayout(View.OnClickListener l) {
        View layout = LayoutInflater.from(context).inflate(R.layout.edittext_dialog, null);
        layout.findViewById(R.id.cancleTv).setOnClickListener(l);
        layout.findViewById(R.id.okTv).setOnClickListener(l);
        layout.findViewById(R.id.parent).setOnClickListener(l);
        addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


    /**
     * 分享
     * @param l
     */
//    public void bindShareLayout(View.OnClickListener l) {
//        View layout = LayoutInflater.from(context).inflate(R.layout.dialog_share, null);
//        LinearLayout wxhyLayout = (LinearLayout) layout.findViewById(R.id.wxhyLayout);
//        LinearLayout qqhyLayout = (LinearLayout) layout.findViewById(R.id.qqhyLayout);
//        LinearLayout pyqLayout = (LinearLayout) layout.findViewById(R.id.pyqLayout);
//        LinearLayout xlwbLayout = (LinearLayout) layout.findViewById(R.id.xlwbLayout);
//        wxhyLayout.setOnClickListener(l);
//        qqhyLayout.setOnClickListener(l);
//        pyqLayout.setOnClickListener(l);
//        xlwbLayout.setOnClickListener(l);
//        layout.findViewById(R.id.parentShare).setOnClickListener(l);
//        addContentView(layout,
//                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//
//    }


    /**
     *  选择男女
     * @param l
     */
//    public void bindChoiceSexLayout(View.OnClickListener l) {
//        View layout = LayoutInflater.from(context).inflate(R.layout.dialog_choice_sex, null);
//        TextView maleTv = (TextView) layout.findViewById(R.id.maleTv);
//        TextView femaleTv = (TextView) layout.findViewById(R.id.femaleTv);
//        TextView cancleTv = (TextView) layout.findViewById(R.id.cancleTv);
//
//        maleTv.setOnClickListener(l);
//        femaleTv.setOnClickListener(l);
//        cancleTv.setOnClickListener(l);
//        layout.findViewById(R.id.parent).setOnClickListener(l);
//        addContentView(layout,
//                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//
//    }

    /**
     * 选择支付方式
     * @param l
     */
//    public void bindChoiceTixianLayout(View.OnClickListener l) {
//        View layout = LayoutInflater.from(context).inflate(R.layout.dialog_choice_tixian, null);
//        TextView wxTv = (TextView) layout.findViewById(R.id.wxTv);
//        TextView zhifubaoTv = (TextView) layout.findViewById(R.id.zhifubaoTv);
//        TextView bankTv = (TextView) layout.findViewById(R.id.bankTv);
//        TextView cancleTv = (TextView) layout.findViewById(R.id.cancleTv);
//
//        wxTv.setOnClickListener(l);
//        zhifubaoTv.setOnClickListener(l);
//        bankTv.setOnClickListener(l);
//        cancleTv.setOnClickListener(l);
//        layout.findViewById(R.id.parent).setOnClickListener(l);
//        addContentView(layout,
//                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//
//    }

    /**
     * 是否举报
     * @param str
     */
//    public void bindJubaoLayout(View.OnClickListener l) {
//        View layout = LayoutInflater.from(context).inflate(R.layout.dialog_jubao, null);
//        TextView jubaoBtn1 = (TextView) layout.findViewById(R.id.jubaoBtn1);
//        TextView jubaoBtn2 = (TextView) layout.findViewById(R.id.jubaoBtn2);
//        TextView jubaoBtn3 = (TextView) layout.findViewById(R.id.jubaoBtn3);
//        TextView cancleBtn = (TextView) layout.findViewById(R.id.cancleBtn);
//        jubaoBtn1.setOnClickListener(l);
//        jubaoBtn2.setOnClickListener(l);
//        jubaoBtn3.setOnClickListener(l);
//        cancleBtn.setOnClickListener(l);
//        layout.findViewById(R.id.parent).setOnClickListener(l);
//        addContentView(layout,
//                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//
//    }

    //  正在加载
    public void bindLoadingLayout(String str) {
        View layout = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        TextView text = (TextView) layout.findViewById(R.id.text);
        if (StringTool.isNotNull(str)) {
            text.setText(str);
        } else {
            text.setVisibility(View.GONE);
        }
        addContentView(layout,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

    }

    // 退出
    public void bindTipsLayout(String tips, String okStr, String cancleStr, final ViewClick mviewClick) {
        View layout = LayoutInflater.from(context).inflate(R.layout.dialog_exit_login, null);
        TextView tipsTv = (TextView) layout.findViewById(R.id.tipsTv);
        TextView okTv = (TextView) layout.findViewById(R.id.okTv);
        okTv.setText(okStr);
        TextView cancleTv = (TextView) layout.findViewById(R.id.cancleTv);
        cancleTv.setText(cancleStr);
        tipsTv.setText(tips);
        View.OnClickListener l = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.okTv) {
                    mviewClick.okClick();
                } else if (v.getId() == R.id.cancleTv) {
                    dismiss();
                }

            }
        };
        okTv.setOnClickListener(l);
        cancleTv.setOnClickListener(l);
        addContentView(layout,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    int star = -1;


    /**
     *  发起预约课程
     * @param okStr
     * @param cancleStr
     * @param mviewClick
     */
//    public void bindCommentLayout(String okStr, String cancleStr, final ViewClick mviewClick) {
//        View layout = LayoutInflater.from(context).inflate(R.layout.dialog_kecheng_comment, null);
//        final ImageView[] stars = new ImageView[5];
//        stars[0] = (ImageView) layout.findViewById(R.id.star1);
//        stars[1] = (ImageView) layout.findViewById(R.id.star2);
//        stars[2] = (ImageView) layout.findViewById(R.id.star3);
//        stars[3] = (ImageView) layout.findViewById(R.id.star4);
//        stars[4] = (ImageView) layout.findViewById(R.id.star5);
//
//        View.OnClickListener l = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (v.getId() == R.id.star1) {
//                    star = 1;
//                } else if (v.getId() == R.id.star2) {
//                    star = 2;
//                } else if (v.getId() == R.id.star3) {
//                    star = 3;
//                } else if (v.getId() == R.id.star4) {
//                    star = 4;
//                } else if (v.getId() == R.id.star5) {
//                    star = 5;
//                }
//                for (int i = 0; i < stars.length; i++) {
//                    if (i < star) {
//                        stars[i].setImageResource(R.mipmap.ic_xing_green);
//                    } else {
//                        stars[i].setImageResource(R.mipmap.ic_xing_green_small);
//                    }
//                }
//            }
//        };
//        for (int i = 0; i < stars.length; i++) {
//            stars[i].setOnClickListener(l);
//        }
//        TextView okTv = (TextView) layout.findViewById(R.id.okTv);
//        okTv.setText(okStr);
//        TextView cancleTv = (TextView) layout.findViewById(R.id.cancleTv);
//        cancleTv.setText(cancleStr);
//        View.OnClickListener ll = new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (v.getId() == R.id.okTv) {
//                    mviewClick.okClick(star + "");
//                } else if (v.getId() == R.id.cancleTv) {
//                    dismiss();
//                }
//
//            }
//        };
//        okTv.setOnClickListener(ll);
//        cancleTv.setOnClickListener(ll);
//        addContentView(layout,
//                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//    }


    /**
     *   课程请假
     */
//    public void bindFinishLayout(String okStr, String cancleStr, final ViewClick mviewClick) {
//        View layout = LayoutInflater.from(context).inflate(R.layout.dialog_finish_kecheng, null);
//        final LinearLayout weishangLayout= (LinearLayout) layout.findViewById(R.id.weishangLayout);
//        final LinearLayout yishangLayout= (LinearLayout) layout.findViewById(R.id.yishangLayout);
//        final LinearLayout guoqiLayout= (LinearLayout) layout.findViewById(R.id.guoqiLayout);
//        final LinearLayout qingjiaLayout= (LinearLayout) layout.findViewById(R.id.qingjiaLayout);
//        final ImageView weishangIv= (ImageView) layout.findViewById(R.id.weishangIv);
//        final ImageView yishangIv= (ImageView) layout.findViewById(R.id.yishangIv);
//        final ImageView guoqiIv= (ImageView) layout.findViewById(R.id.guoqiIv);
//        final ImageView qingjiaIv= (ImageView) layout.findViewById(R.id.qingjiaIv);
//
//        View.OnClickListener l = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                weishangIv.setImageResource(R.mipmap.ic_checkbox_uncheck);
//                yishangIv.setImageResource(R.mipmap.ic_checkbox_uncheck);
//                guoqiIv.setImageResource(R.mipmap.ic_checkbox_uncheck);
//                qingjiaIv.setImageResource(R.mipmap.ic_checkbox_uncheck);
//                if (v.getId() == R.id.weishangLayout) {
//                    weishangIv.setImageResource(R.mipmap.ic_checkbox_check);
//                    star = 0;
//                } else if (v.getId() == R.id.yishangLayout) {
//                    yishangIv.setImageResource(R.mipmap.ic_checkbox_check);
//                    star = 1;
//                } else if (v.getId() == R.id.guoqiLayout) {
//                    guoqiIv.setImageResource(R.mipmap.ic_checkbox_check);
//                    star = 2;
//                } else if (v.getId() == R.id.qingjiaLayout) {
//                    qingjiaIv.setImageResource(R.mipmap.ic_checkbox_check);
//                    star = 3;
//                }
//
//            }
//        };
//        weishangLayout.setOnClickListener(l);
//        yishangLayout.setOnClickListener(l);
//        guoqiLayout.setOnClickListener(l);
//        qingjiaLayout.setOnClickListener(l);
//
//        TextView okTv = (TextView) layout.findViewById(R.id.okTv);
//        okTv.setText(okStr);
//        TextView cancleTv = (TextView) layout.findViewById(R.id.cancleTv);
//        cancleTv.setText(cancleStr);
//        View.OnClickListener ll = new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (v.getId() == R.id.okTv) {
//                    mviewClick.okClick(star + "");
//                } else if (v.getId() == R.id.cancleTv) {
//                    dismiss();
//                }
//
//            }
//        };
//        okTv.setOnClickListener(ll);
//        cancleTv.setOnClickListener(ll);
//        addContentView(layout,
//                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (dismissIF != null) dismissIF.dialogDismiss();
    }

    public interface ViewClick {
        public void okClick(String... agrs);
        public void cancleClick();
    }

    public interface DismissIF {
        public void dialogDismiss();
    }
}

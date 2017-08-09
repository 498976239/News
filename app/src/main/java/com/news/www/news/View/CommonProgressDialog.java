package com.news.www.news.View;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.news.www.news.R;

/**
 * Created by Administrator on 16-12-4.
 */
public class CommonProgressDialog extends Dialog {
    private CommonProgressDialog dialog;
    public CommonProgressDialog(Context context) {
        super(context,R.style.CommonProgressDialog);//将样式放入父组件
        setContentView(R.layout.commonprogressdialog);
        //显示在屏幕中间
        getWindow().getAttributes().gravity = Gravity.CENTER;
    }
    public void setMessage(String s){
        TextView tv = (TextView) this.findViewById(R.id.loadingTv);
        tv.setText(s);
    }


    public void showDialog(Context context,String msg){
        if(dialog ==null){
            dialog = new CommonProgressDialog(context);
        }
        dialog.show();
        dialog.setMessage(msg);

    }
    public void closeDialog(){
        if(dialog!=null){
            dialog.dismiss();
            dialog = null;
        }
    }

}

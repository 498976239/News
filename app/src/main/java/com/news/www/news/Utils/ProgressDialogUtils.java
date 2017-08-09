package com.news.www.news.Utils;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.news.www.news.View.CommonProgressDialog;

/**
 * Created by Administrator on 16-12-4.
 * 弹窗工具类
 */
public class ProgressDialogUtils {
    private CommonProgressDialog mdialog;
    private FragmentActivity activity;
    //显示方法
    /*public void showDialog(FragmentActivity activity,String msg){
           this.activity = activity;
            if(mdialog == null){
               mdialog = new CommonProgressDialog(activity);
            }
               mdialog.setMessage(msg);
        if(!activity.isFinishing()&&!mdialog.isShowing()){
            mdialog.showDialod(activity,msg);
        }

    }
    //关闭方法
    public void closeDialog(){
        if(mdialog!=null&&!activity.isFinishing()){
            mdialog.dismiss();
            mdialog = null;
        }
    }*/


}

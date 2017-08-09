package com.news.www.news.Utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.news.www.news.Model.Model;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SS on 16-12-7.
 */
public class JsonUtils {
    //明确访问的路劲http://news.ifeng.com/
    //返回的数据格式是[[{},{},{}],[{},{},{}],[{},{},{}]]
    public final String TAG =this.getClass().getSimpleName();
    private OkHttpClient okHttpClient;
    private callBack listener;
    private List<Model>[] model_list;

    public void setUpDateListener(callBack listener) {
        this.listener = listener;
    }

   /*private JsonUtils(Context mContext){
       this.mContext = mContext;
        okHttpClient = new OkHttpClient();
    }
    public static JsonUtils getInstance(Context mContext){
        JsonUtils instance = null;
        if(jsonUtils == null){
            synchronized (JsonUtils.class){
                if(instance == null){
                    instance= new JsonUtils(mContext);
                    jsonUtils = instance;
                }
            }
        }
        return jsonUtils;
    }*/
    public void getResult(){
        //发送请求,需要在一个线程里请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url("http://news.ifeng.com/").build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        e.printStackTrace();

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        if(response.isSuccessful()){
                            String str = response.body().string();
                            Message msg = new Message();
                            msg.obj = str;
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }
                    }
                });
            }
        }).start();

    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                String str =msg.obj.toString();
                getJSON(str);

            }
        }
    };

    public void getJSON(String str){
        if(str!=null){
            String s = str.substring(str.indexOf("[[{"),str.indexOf("}]]")+3);
            initJSON(s);
        }
    }
    /*解析JSON字符串，一层一层的解析数组*/
    public void initJSON(String s){
        try {/*创建JSONArray数组用来存放截取的字符串s，是字符串数组*/
            JSONArray array = new JSONArray(s);
            /*定义一个集合数组*/
            model_list = new ArrayList[array.length()];
            /*遍历array，取出里面的元素，根据返回结果知道还是数组*/
            for (int i = 0; i <array.length() ; i++) {
                JSONArray arr = array.getJSONArray(i);//JSONArray里面有个getJSONArray方法，获取数组
                model_list[i] = new ArrayList<>();//将第一个数组内容放入集合中
                for (int j = 0; j <arr.length() ; j++) {
                    JSONObject obj = arr.getJSONObject(j);
                    Model model = new Model();
                    model.setThumbnail(obj.getString("thumbnail"));
                    model.setTitle(obj.getString("title"));
                    model.setUrl(obj.getString("url"));
                    model_list[i].add(model);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listener.update(model_list);
    }
    public interface callBack{
        void update(List<Model>[] model_list);
    }
}

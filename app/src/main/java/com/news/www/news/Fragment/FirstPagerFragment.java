package com.news.www.news.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.news.www.news.Model.BannerBean;
import com.news.www.news.Model.Model;
import com.news.www.news.R;
import com.news.www.news.Utils.JsonUtils;
import com.news.www.news.Utils.ProgressDialogUtils;
import com.news.www.news.View.CommonProgressDialog;
import com.news.www.news.adapter.FirstPagerAdapter;
import com.youth.banner.Banner;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 16-12-3.
 * 主界面的Fragment
 */
public class FirstPagerFragment extends Fragment {
    private CommonProgressDialog mCommonProgressDialog;
    private List<Model> banner_url;//用来存放model对象的
    private List<Model>[] th_banner;//将解析过来的对象暂时存放在这里
    private RecyclerView mRecyclerView;
    private FirstPagerAdapter myAdapter;
    private JsonUtils jsonUtils;
    private BannerBean bannerBean;
    private String[] image;
    private String[] title;
    private String[] toUrl;
    private int nowNumber = 7;
    private int LastNumber;
    private int mPosition;
    private SwipeRefreshLayout swipe;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_layout,
                (ViewPager)getActivity().findViewById(R.id.view_pager),false);
        /*mCommonProgressDialog = new CommonProgressDialog(getActivity());
        mCommonProgressDialog.showDialog(getActivity(),"马上就炸了");*/
        swipe = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);//下拉刷新
        swipe.setColorSchemeResources(android.R.color.holo_blue_light);//设置下拉刷新颜色
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {//设置下拉监听事件
            @Override
            public void onRefresh() {
                jsonUtils.getResult();

            }
        });
        banner_url = new ArrayList<>();
        bannerBean = new BannerBean();
        image = new String[3];//用来存放图片的数组
        title = new String[3];
        toUrl = new String[3];
        //banner_url.clear();
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myAdapter = new FirstPagerAdapter(getActivity(),banner_url,bannerBean);
        mRecyclerView.setAdapter(myAdapter);
        myAdapter.setAdapterListener(new FirstPagerAdapter.onChildClickListener() {
            @Override
            public void onChildClick(RecyclerView parent, View v, int position, Model model) {
                Log.i("FirstPagerFragment-----", String.valueOf(position));
            }
        });
        /*为RecyclerView设定滑动监听事件，我写在另外一个方法里，方便阅读*/
        listenerRecyclerView();
        jsonUtils = new JsonUtils();
        //jsonUtils =  JsonUtils.getInstance(getActivity());
        jsonUtils.getResult();
        jsonUtils.setUpDateListener(new JsonUtils.callBack() {
            @Override
            public void update(final List<Model>[] model_list) {
                /*写这个延时的目的是为了代替弹出窗口的，因为一般的设计是
                * 返回数据后弹窗消失而显示数据，因为我没有写好弹窗，就用了一个
                * 延时加载，防止还没来得及读新数据，老数据自己就已经显示*/
                mhandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        th_banner = model_list;//将返回的集合赋值给新的集合，方便后面操作
                        // mCommonProgressDialog.closeDialog()
                        getDate();
                        swipe.setRefreshing(false);//让下拉刷新图标消失
                    }
                },800);

            }
        });
        return v;
    }
    public void setPosition(int position) {
        this.mPosition = position;
         getDate();
    }
    public void getDate(){
        if(th_banner!=null){
            List<Model> model = th_banner[mPosition];
            for (int i = 0; i <3 ; i++) {
                Model m = model.get(i);
                image[i] = m.getThumbnail();
                title[i] = m.getTitle();
                toUrl[i] = m.getUrl();
            }
            bannerBean.setImage(image);
            bannerBean.setTitle(title);
            bannerBean.setToUrl(toUrl);
            banner_url.clear();//防止缓存
            for (int i = 3; i <nowNumber ; i++) {
                Model m = model.get(i);
                banner_url.add(m);
            }
            myAdapter.notifyDataSetChanged();
        }
    }

    public void listenerRecyclerView(){
        /*RecyclerView添加滑动监听*/
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //发生状态变化时监听
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                /*判断得到的底部位置和adapter的item数是不是相等
                * newState == RecyclerView.SCROLL_STATE_IDLE
                * 设置当滚动条停止的时候才刷新数据*/
                if(newState == RecyclerView.SCROLL_STATE_IDLE &&LastNumber+1 == myAdapter.getItemCount()){
                    mhandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            nowNumber = nowNumber+4;
                            getDate();
                        }
                    },2000);
                }
            }
            //滑动时监听
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //判断是否已经滑到底部，记录底部的位置(最后一条数据)
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                LastNumber =lm.findLastVisibleItemPosition();
            }
        });
    }
    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };



}


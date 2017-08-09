package com.news.www.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.news.www.news.Model.BannerBean;
import com.news.www.news.Model.Model;
import com.news.www.news.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;


/**
 * Created by SS on 16-12-5.
 */
public class FirstPagerAdapter extends RecyclerView.Adapter{
    private final int TYPE_HEAD = 0;//表示首个位置显示轮播图片
    private final int TYPE_NORMAL = 1;//表示正常的item布局
    private final int TYPE_FOOTER = 2;//表示底部的布局
    private List<Model> banner_url;
    private BannerBean date;
    private Context context;
    private RecyclerView recyclerView;
    private onChildClickListener listener;
    public FirstPagerAdapter(Context context,List<Model> banner_url,BannerBean date){
        this.context = context;
        this.banner_url = banner_url;
        this.date = date;

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }
/*这个函数的作用就是实例化一个item的View，并新建ViewHolder传入该view作为rootView，最后返回ViewHolder*/
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if(viewType == TYPE_HEAD){
            View v = LayoutInflater.from(context).inflate(R.layout.itemheader_banner,parent,false);
           holder = new BannerViewHolder(v);
        }else if(viewType == TYPE_FOOTER){
            View v = LayoutInflater.from(context).inflate(R.layout.item_footer,parent,false);
            holder = new FooterViewHolder(v);
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.itemfirstfragment,parent,false);
            holder = new ItemViewHolder(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null&&recyclerView!=null){
                        int position = recyclerView.getChildAdapterPosition(v);
                        listener.onChildClick(recyclerView,v,position,banner_url.get(position));
                    }
                }
            });
        }
        return holder;
    }
    //和viewHolder进行绑定.这个函数的作用是将数据与view关联。
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof BannerViewHolder){
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
            bannerViewHolder.banner.setImages(date.getImage());//设置轮播图片参数填入数组或者集合
            bannerViewHolder.banner.setBannerTitle(date.getTitle());
        }else if(holder instanceof ItemViewHolder){
            ItemViewHolder itemViewHolder = (ItemViewHolder)holder;
            itemViewHolder.simpleDraweeView.setImageURI(banner_url.get(position-1).getThumbnail());
            itemViewHolder.textView.setText(banner_url.get(position-1).getTitle());
        }else if(holder instanceof FooterViewHolder){
            FooterViewHolder footerViewHolder = ( FooterViewHolder)holder;
        }

    }

    @Override
    public int getItemCount() {
        return banner_url.size()+1+1;
    }

    //通过这个方法来确定我们需要创建什么类型的viewHolder
    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_HEAD;
        }else if(position+1 == getItemCount()){//position表示当前的item数。如果加1刚好等于总得item数（包括banner和底部的）
            //特别注意是position+1；
            return TYPE_FOOTER;
        }else {
            return TYPE_NORMAL;
        }
    }
    //用它来管理正常的item布局
    class ItemViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView simpleDraweeView;
        TextView textView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            simpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.simpleView);
            textView= (TextView) itemView.findViewById(R.id.news_text);
        }
    }
    //首位的banner的viewHolder
    class BannerViewHolder extends RecyclerView.ViewHolder{
        Banner banner;
        public BannerViewHolder(View itemView) {
            super(itemView);
            banner = (Banner) itemView.findViewById(R.id.banner);
        }
    }
    class FooterViewHolder extends RecyclerView.ViewHolder{

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
    public interface onChildClickListener{
        void onChildClick(RecyclerView parent,View v,int position,Model model);
    }

    public void setAdapterListener(onChildClickListener listener) {
        this.listener = listener;
    }
}

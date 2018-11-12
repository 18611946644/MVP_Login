package com.bwie.lx06_07login.fragment.fragmentmydata.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.lx06_07login.R;
import com.bwie.lx06_07login.bean.MyDataBean;

import java.util.List;

/**
 * date:2018/11/10
 * author:张自力(DELL)
 * function:
 */

public class MyDataAdapter extends BaseAdapter {
    private Context mContext;
    private List<MyDataBean.DataBean> list;

    public MyDataAdapter(Context context, List<MyDataBean.DataBean> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            convertView = View.inflate(mContext, R.layout.item_data_adapter,null);
            holder = new ViewHolder();
            holder.Img1 = convertView.findViewById(R.id.img1);
            holder.Img2 = convertView.findViewById(R.id.img2);
            holder.Img3 = convertView.findViewById(R.id.img3);
            holder.txtTitle = convertView.findViewById(R.id.txt_title);
            holder.txtAuther = convertView.findViewById(R.id.txt_auther);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load(list.get(position).getThumbnailPicS()).into(holder.Img1);
        Glide.with(mContext).load(list.get(position).getThumbnailPicS02()).into(holder.Img2);
        Glide.with(mContext).load(list.get(position).getThumbnailPicS03()).into(holder.Img3);

        holder.txtTitle.setText(list.get(position).getTitle());
        holder.txtAuther.setText(list.get(position).getAuthorName());

        return convertView;
    }


    class ViewHolder{
        ImageView Img1;
        ImageView Img2;
        ImageView Img3;

        TextView txtTitle;
        TextView txtAuther;
    }

}

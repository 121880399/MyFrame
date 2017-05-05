package com.viking.myframe.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.viking.myframe.holder.ListViewHolder;

import java.util.List;

/**
 * ListView Adapter基类
 * Created by zzy on 2017/5/1.
 */

public abstract class BaseListViewAdapter<T> extends BaseAdapter{
    protected List<T> mDatas;
    protected Context mContext;
    protected int mLayoutId;

    public BaseListViewAdapter(List<T> datas, Context context, int layoutId){
        this.mContext=context;
        this.mLayoutId=layoutId;
        this.mDatas=datas;
    }

    @Override
    public int getCount() {
        return mDatas==null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas==null ? null:mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewHolder holder= ListViewHolder.getViewHolder(mContext,convertView,parent,mLayoutId,position);
        T t=mDatas.get(position);
        bindData(holder,t);
        return holder.getConvertView();
    }

    /**
     * 让继承BaseListAdapter的类去实现binData方法，把控件绑定数据
     * */
    public abstract void bindData(ListViewHolder holder, T t);
}

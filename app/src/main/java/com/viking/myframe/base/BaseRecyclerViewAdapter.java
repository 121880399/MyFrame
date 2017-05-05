package com.viking.myframe.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viking.myframe.holder.RecyclerViewHolder;
import com.viking.myframe.inter.MutipleTypeSupport;

import java.util.List;

/**
 * Created by adm on 2017/5/1.
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder>{
    protected List<T> mDatas;
    protected int mLayoutId;
    protected Context mContext;
    protected LayoutInflater mInflater;
    private MutipleTypeSupport<T> mMutipleType;

    public BaseRecyclerViewAdapter(Context context,int layoutId,List<T> datas){
        this.mContext=context;
        this.mInflater=LayoutInflater.from(context);
        this.mLayoutId=layoutId;
        this.mDatas=datas;
    }

    public BaseRecyclerViewAdapter(Context context,List<T> datas,MutipleTypeSupport type){
        this(context,-1,datas);
        this.mMutipleType=type;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mMutipleType!=null){
            mLayoutId=viewType;
        }
        View view=mInflater.inflate(mLayoutId,parent,false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        bindData(holder,mDatas.get(position),position);
    }

    protected  abstract void bindData(RecyclerViewHolder holder,T t,int position);

    @Override
    public int getItemCount() {
        return mDatas==null ? 0 : mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mMutipleType!=null){
            mMutipleType.getLayoutId(mDatas.get(position));
        }
        return super.getItemViewType(position);
    }
}

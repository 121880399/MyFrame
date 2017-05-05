package com.viking.myframe.holder;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * ListView 基类ViewHolder
 * Created by zzy on 2017/4/30.
 */

public class ListViewHolder {
    //可复用的View
    private final View mConvertView;
    //所有控件合集
    private SparseArray<View> mViews;
    //记录当前位置
    private int position;

    /**
     * BaseViewHolder 构造函数
     * @param context 上下文对象
     * @param parent 父类容器
     * @param layoutId 布局 Id
     * @param position item位置信息
     */
    public ListViewHolder(Context context, ViewGroup parent, int layoutId, int position){
        this.position=position;
        this.mViews=new SparseArray<View>();
        //得到布局View
        mConvertView= LayoutInflater.from(context).inflate(layoutId,parent,false);
        mConvertView.setTag(this);
    }

    /**
     * 通过 viewId 获取控件
     * @param viewId 控件id
     * @param <T> View 子类
     * @return 返回 View
     */
    public <T extends View> T getView(int viewId){
        View view=mViews.get(viewId);
        if(view==null){
            //如果不存在就初始化view
            view=mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T)view;
    }

    public static ListViewHolder getViewHolder(Context context, View convertView, ViewGroup parent, int layoutId, int position){
        if(convertView==null){
            return new ListViewHolder(context,parent,layoutId,position);
        }else{
            ListViewHolder holder= (ListViewHolder) convertView.getTag();
            holder.position=position;
            return holder;
        }
    }


    /**
     * 获取convertView
     * */
    public View getConvertView(){
        return mConvertView;
    }

    /**
     * 设置 TextView 的值
     * @param viewId
     * @param text
     * @return
     */
    public ListViewHolder setText(int viewId, String text){
        TextView tv=getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置TImageView的值
     * @param viewId
     * @param resId
     * @return
     */
    public ListViewHolder setImageResource(int viewId, int resId){
        ImageView iv=getView(viewId);
        iv.setImageResource(resId);
        return this;
    }

    /**
     * 设置是否可见
     * @param viewId
     * @param visible
     * @return
     */
    public ListViewHolder setVisible(int viewId, boolean visible){
        View view=getView(viewId);
        view.setVisibility(visible ? View.VISIBLE:View.GONE);
        return this;
    }

    /**
     * 设置tag
     * @param viewId
     * @param tag
     * @return
     */
    public ListViewHolder setTag(int viewId, Object tag){
        View view=getView(viewId);
        view.setTag(tag);
        return this;
    }

    public ListViewHolder setTag(int viewId, int key, Object tag){
        View view=getView(viewId);
        view.setTag(key,tag);
        return this;
    }

    /**
     * 设置 Checkable
     * @param viewId
     * @param checked
     * @return
     */
    public ListViewHolder setChecked(int viewId, boolean checked){
        Checkable view=(Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    /**
     * 点击事件
     * */
    public ListViewHolder setOnClickListner(int viewId, View.OnClickListener listener){
        View view=getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * 触摸事件
     * */
    public ListViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener){
        View view=getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    /**
     * 设置长按事件
     * */
    public ListViewHolder setOnLongClickListner(int viewId, View.OnLongClickListener listener){
        View view=getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }
}

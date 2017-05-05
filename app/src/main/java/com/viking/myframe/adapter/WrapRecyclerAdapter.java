package com.viking.myframe.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 周正一 on 2017/5/4.
 */

public class WrapRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private RecyclerView.Adapter mAdapter;
    private int BASE_HEADER_KEY=0;
    private int BASE_FOOTER_KEY=0;


    private SparseArray<View> mHeaderViews;
    private SparseArray<View> mFooterViews;

    public WrapRecyclerAdapter(RecyclerView.Adapter mAdatper) {
        this.mAdapter = mAdatper;
        mHeaderViews=new SparseArray<>();
        mFooterViews=new SparseArray<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //判断是否为头部
        if(isHeaderViewType(viewType)){
            View headerView=mHeaderViews.get(viewType);
            return createHeaderOrFooterViewHolder(headerView);
        }

        //判断是否为底部
        if(isFooterViewType(viewType)){
            View footerView=mFooterViews.get(viewType);
            return createHeaderOrFooterViewHolder(footerView);
        }
        return mAdapter.onCreateViewHolder(parent,viewType);
    }

    private RecyclerView.ViewHolder createHeaderOrFooterViewHolder(View view) {
        return new RecyclerView.ViewHolder(view){};
    }

    private boolean isFooterViewType(int viewType) {
        int footerPosition=mFooterViews.indexOfKey(viewType);
        return footerPosition>=0;
    }

    private boolean isHeaderViewType(int viewType) {
        int headerPosition=mHeaderViews.indexOfKey(viewType);
        return headerPosition>=0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //头部，底部不需要绑定数据
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return;
        }
        position=position-mHeaderViews.size();
        mAdapter.onBindViewHolder(holder,position);
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount()+mHeaderViews.size()+mFooterViews.size();
    }

    //是否为底部位置
    private boolean isFooterPosition(int position) {
        return position >= (mHeaderViews.size() + mAdapter.getItemCount());
    }

    //是否为头部位置
    private boolean isHeaderPosition(int position) {
        return position < mHeaderViews.size();
    }

    /**
     * 添加头部
     * */
    public void addHeaderView(View view){
        //先判断是否已经包含
        int position=mHeaderViews.indexOfValue(view);
        if(position<0){
            mHeaderViews.put(BASE_HEADER_KEY++,view);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加底部
     * */
    public void addFooterView(View view){
        int position=mFooterViews.indexOfValue(view);
        if(position<0){
            mFooterViews.put(BASE_FOOTER_KEY++,view);
        }
        notifyDataSetChanged();
    }
    //移除头部,底部
    public void removeHeaderView(View view) {
        //没有包含头部
        int index = mHeaderViews.indexOfValue(view);
        if (index < 0) return;
        //集合没有就添加，不能重复添加
        mHeaderViews.removeAt(index);
        notifyDataSetChanged();
    }

    public void removeFooterView(View view) {
        //没有包含底部
        int index = mFooterViews.indexOfValue(view);
        if (index < 0) return;
        //集合没有就添加，不能重复添加
        mFooterViews.removeAt(index);
        notifyDataSetChanged();
    }

    /**
     * 解决GridLayoutManager添加头部和底部不占用一行的问题
     * @param recycler
     */
    public void adjustSpanSize(RecyclerView recycler) {
        if (recycler.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager layoutManager = (GridLayoutManager) recycler.getLayoutManager();
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    boolean isHeaderOrFooter = isHeaderPosition(position) || isFooterPosition(position);
                    return isHeaderOrFooter ? layoutManager.getSpanCount() : 1;
                }
            });
        }
    }
}

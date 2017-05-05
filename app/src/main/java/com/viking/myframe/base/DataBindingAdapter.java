package com.viking.myframe.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viking.myframe.holder.CommonViewHolder;
import com.viking.myframe.models.RecyclerModel;

import java.util.ArrayList;
import java.util.List;


/**
 * DataBinding通用Adapter，添加头部，尾部。
 * Created by 周正一 on 2017/5/1.
 */

public abstract class DataBindingAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Default Mode
    private final int TYPE_NORMAL = 3;

    //Custom Mode
    private final int TYPE_CUSTOM = 4;

    //header type
    private final int VIEW_HEADER = 0;

    //bodyer type
    private final int VIEW_BODYER = 1;

    //footer type
    private final int VIEW_FOOTER = 2;

    //head data
    private final List<RecyclerModel<T>> headDatas;

    //body data
    protected final List<T> mDatas;

    //foot data
    private final List<RecyclerModel<T>> footDatas;

    //header count
    private int headerCount;

    //footer count
    private int footerCount;

    //bodyer click listener
    private ItemClickListener myItemClickListener;

    //bodyer long click listener
    private ItemLongClickListener myItemLongClickListener;

    //header click listener
    private OnHeaderClickListener headerClickListener;

    //footer click listener
    private OnFooterClickListener footerClickListener;

    private int mMode;

    private Context mContext;

    public DataBindingAdapter(List<T> datas) {
        this.mDatas = (datas != null) ? datas : new ArrayList<T>();
        headDatas = new ArrayList<>();
        footDatas = new ArrayList<>();
        headerCount = 0;
        footerCount = 0;
        if (0 != getStartMode()) {
            mMode = TYPE_CUSTOM;
        } else {
            mMode = TYPE_NORMAL;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = null;
        //判断头部是否显示
        if (null != headDatas && viewType == VIEW_HEADER && getHeaderVisible()) {
            dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    getHeaderLayoutId(headerCount),
                    parent,
                    false
            );
            headerCount++;
        } else if (null != footDatas && viewType == VIEW_FOOTER && getFooterVisible()) {
            dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    getFooterLayoutId(footerCount),
                    parent,
                    false
            );
            footerCount++;
        } else {
            dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    getItemLayoutId(viewType),
                    parent,
                    false
            );
        }
        CommonViewHolder holder = new CommonViewHolder(dataBinding.getRoot());
        holder.setBinding(dataBinding);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CommonViewHolder viewHolder = (CommonViewHolder) holder;
        switch (getItemViewType(position)) {
            case VIEW_HEADER:
                bindData(viewHolder, headDatas.get(position).getVariableId(), headDatas.get(position).getData());
                viewHolder.itemView.setOnClickListener(getHeaderClickListener(viewHolder.itemView, position));
                break;
            case VIEW_FOOTER:
                bindData(viewHolder, footDatas.get(position).getVariableId(), footDatas.get(position).getData());
                viewHolder.itemView.setOnClickListener(getFooterClickListener(viewHolder.itemView, getFooterPosition(viewHolder)));
                break;
            default:
                //设置点击事件
                viewHolder.itemView.setOnClickListener(getClickListener(viewHolder.itemView, getRealPosition(viewHolder)));
                //设置长点击事件
                viewHolder.itemView.setOnLongClickListener(getLongClickListener(viewHolder.itemView, getRealPosition(viewHolder)));
                switch (mMode) {
                    case TYPE_NORMAL:
                        bindData(viewHolder, getVariableId(getItemViewType(position)), mDatas.get(getRealPosition(viewHolder)));
                        break;
                    case TYPE_CUSTOM:
                        //如果是自定义类型，除了正常的绑定之外，还需要回调一个接口，让用户自己去定义
                        bindData(viewHolder, getVariableId(getItemViewType(position)), mDatas.get(getRealPosition(viewHolder)));
                        bindCustomData(viewHolder, position, mDatas.get(getRealPosition(viewHolder)));
                        break;
                }
                break;
        }

    }


    /**
     * all data
     *
     * @return header + bodyer + footer
     */
    @Override
    synchronized public int getItemCount() {
        int size = 0;
        if (null != headDatas) {
            size += headDatas.size();
        }
        if (null != footDatas) {
            size += footDatas.size();
        }
        size += mDatas.size();
        return size;
    }

    private void bindData(CommonViewHolder holder, int variableId, T item) {
        holder.getBinding().setVariable(variableId, item);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemViewType(int position) {
        if (null == headDatas) {
            //没有头部，有主体和尾部的情况
            if (position < getRealListSize()) {
                return getItemTypePosition(position);
            } else {
                return VIEW_FOOTER;
            }
        }
        //如果有头部，并且当前显示位置小于头部的个数，返回头部
        if (position < headDatas.size()) return VIEW_HEADER;
        //当前显示位置大于头部，小于头部+主题的个数，返回主体（主体又分自定义和普通，所以要交给子类来判断）
        if (position < (getRealListSize() + headDatas.size()))
            return getItemTypePosition(position - headDatas.size());
        //能执行到这说明已经执行完了头部和主体，如果尾部有数据那么就返回尾部
        if (null != footDatas && position < getItemCount()) return VIEW_FOOTER;

        return VIEW_BODYER;
    }

    /**
     * 得到头部点击Listener
     */
    private View.OnClickListener getHeaderClickListener(final View view, final int pos) {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != headerClickListener) {
                    headerClickListener.headerClick(view, pos);
                }
            }
        };
    }

    /**
     * 得到尾部点击Listener
     */
    private View.OnClickListener getFooterClickListener(final View view, final int pos) {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != footerClickListener) {
                    footerClickListener.footerClick(view, pos);
                }
            }
        };
    }

    /**
     * 得到长按Listener
     */
    private View.OnLongClickListener getLongClickListener(final View view, final int pos) {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != myItemLongClickListener) {
                    myItemLongClickListener.onItemLongClick(view, pos);
                }
                return true;
            }
        };
    }

    /**
     * 得到点击事件
     */
    private View.OnClickListener getClickListener(final View view, final int pos) {

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != myItemClickListener) {
                    myItemClickListener.onItemClick(view, pos);
                }
            }
        };
    }


    /**
     * 得到Body的真正position
     * 如果有head的情况下，要用当前Position的位置减去head的个数
     */
    public int getRealPosition(RecyclerView.ViewHolder holder) {

        int position = holder.getLayoutPosition();

        return headDatas == null ? position : position - headDatas.size();
    }

    /**
     * 因为是尾部，所以Position要特殊处理
     * 没有尾部的情况下直接返回position
     * 如果有尾部的情况下，要用当前position的位置减去头部数量+body数量
     */
    private int getFooterPosition(RecyclerView.ViewHolder holder) {

        int position = holder.getLayoutPosition();

        return footDatas == null ? position : position - (headDatas.size() + getRealListSize());
    }

    /**
     * 往body层的某个位置添加数据
     *
     * @param pos  position
     * @param item content
     */
    public void add(int pos, T item) {
        mDatas.add(pos, item);
        notifyDataSetChanged();
    }

    /**
     * 往Body层添加一个List
     *
     * @param items content
     */
    public void addBodyerList(List<T> items) {
        mDatas.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * 删除body层某个位置的数据
     *
     * @param pos position
     */
    public void delete(int pos) {
        mDatas.remove(pos);
        notifyDataSetChanged();
    }

    /**
     * 设置body层的数据，注意跟add的区别，设置需要先清空
     *
     * @param items content
     */
    public void setBodyerListData(List<T> items) {
        clearData();
        mDatas.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * 清空所有数据
     */
    public void clearData() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    /**
     * 得到body层某个位置的数据
     *
     * @param pos position
     * @return T content
     */
    public T getItemObject(int pos) {

        return mDatas.get(pos);
    }

    /**
     * 添加头部数据
     *
     * @param data view layoutid
     */
    public void addHeadView(RecyclerModel data) {
        headDatas.add(data);
        notifyDataSetChanged();
    }

    /**
     * 从head层的某个位置删除数据
     *
     * @param pos position
     */
    public void removeHeadView(int pos) {
        if (0 == headDatas.size()) {
            return;
        }
        headerCount = 0;
        headDatas.remove(pos);
        notifyDataSetChanged();
    }

    /**
     * 得到头部某个位置的布局id
     *
     * @param position position
     * @return layoutid
     */
    private int getHeaderLayoutId(int position) {
        return headDatas.get(position).getLayoutId();
    }

    /**
     * 添加foot层数据
     *
     * @param recyclerModel recyclermodel model
     */
    public void addFootView(RecyclerModel recyclerModel) {

        footDatas.add(recyclerModel);
        notifyDataSetChanged();
    }

    /**
     * 删除foot层某条数据
     *
     * @param pos position
     */
    public void removeFootView(int pos) {
        if (0 == footDatas.size()) {
            return;
        }
        //footerCount = 0;
        footDatas.remove(pos);
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == VIEW_HEADER ? gridManager.getSpanCount() : getItemViewType(position) == VIEW_FOOTER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams && holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    public void setMyItemClickListener(ItemClickListener myItemClickListener) {
        this.myItemClickListener = myItemClickListener;
    }

    public void setMyItemLongClickListener(ItemLongClickListener myItemLongClickListener) {
        this.myItemLongClickListener = myItemLongClickListener;
    }

    public void setHeaderClickListener(OnHeaderClickListener headerClickListener) {
        this.headerClickListener = headerClickListener;
    }

    public void setFooterClickListener(OnFooterClickListener footerClickListener) {
        this.footerClickListener = footerClickListener;
    }

    /**
     * 得到模式
     *
     * @return 0 default
     */
    abstract public int getStartMode();

    /**
     * 得到body布局id
     *
     * @param viewType type
     * @return layoutid
     */
    abstract public int getItemLayoutId(int viewType);

    abstract public int getItemTypePosition(int position);

    /**
     * bind  reference
     *
     * @return BR id
     */
    abstract public int getVariableId(int viewType);

    /**
     * custom
     *
     * @param holder   viewholder
     * @param position position 这里的position是整体的position包括了head
     * @param item     item
     */
    abstract public void bindCustomData(CommonViewHolder holder, int position, T item);

    /**
     * header click interface
     */
    public interface OnHeaderClickListener {
        void headerClick(View view, int position);
    }

    /**
     * footer click interface
     */
    public interface OnFooterClickListener {
        void footerClick(View view, int position);
    }

    /**
     * bodyer long click interface
     */
    public interface ItemLongClickListener {
        public void onItemLongClick(View view, int position);
    }

    /**
     * bodyer click interface
     */
    public interface ItemClickListener {
        public void onItemClick(View view, int position);
    }


    /**
     * bodyer data
     *
     * @return body data size
     */
    public int getRealListSize() {
        return mDatas.size();
    }

    /**
     * 判断头部是否显示
     */
    private boolean getHeaderVisible() {
        return headerCount < headDatas.size();
    }


    /**
     * 判断尾部是否显示
     */
    private boolean getFooterVisible() {
        return footerCount < footDatas.size();
    }

    /**
     * 得到尾部布局id，可能有多个尾部
     *
     * @param position position
     * @return layoutid
     */
    private int getFooterLayoutId(int position) {
        return footDatas.get(position).getLayoutId();
    }
}

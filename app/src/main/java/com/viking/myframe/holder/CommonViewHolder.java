package com.viking.myframe.holder;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * DataBinding 通用的ViewHolder
 * Created by adm on 2017/5/1.
 */

public class CommonViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding binding;

    public CommonViewHolder(View itemView) {
        super(itemView);
    }

    public ViewDataBinding getBinding() {
        return binding;
    }

    public void setBinding(ViewDataBinding binding) {
        this.binding = binding;
    }
}

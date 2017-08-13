package com.baimeng.recyclerviewdecoration;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.baimeng.recyclerviewdecoration.commonadapter.RecyclerViewCommonAdapter;
import com.baimeng.recyclerviewdecoration.commonadapter.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/8/12.
 */

public class MyAdapter<T> extends RecyclerViewCommonAdapter<T> {

    public MyAdapter(int mLayoutId, List mData, Context context) {
        super(mLayoutId, mData, context);
    }

    @Override
    protected void convert(ViewHolder holder, T itemData , int position) {
        //绑定数据
        //holder.setText()

    }

}

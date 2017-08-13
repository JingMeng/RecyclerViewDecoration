package com.baimeng.recyclerviewdecoration;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baimeng.recyclerviewdecoration.commonadapter.RecyclerViewCommonAdapter;
import com.baimeng.recyclerviewdecoration.commonadapter.ViewHolder;
import com.baimeng.recyclerviewdecoration.commonadapter.wrapper.WrapRecyclerAdapter;
import com.baimeng.recyclerviewdecoration.commonadapter.wrapper.WrapperRecyclerView;
import com.baimeng.recyclerviewdecoration.decoration.GridItemDecoration;
import com.baimeng.recyclerviewdecoration.decoration.LinearItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/8/12.
 */

public class LinearRecyclerviewActivity extends AppCompatActivity {

    private WrapperRecyclerView mLinearRecycler;
    private ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            //响应滑动的方向
            int swipeFlags = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT ;
            //相应拖动的方向
            int dragFlags = ItemTouchHelper.UP|ItemTouchHelper.DOWN ;
            //GridLayoutManager 继承自LinearLayoutManager
            if(recyclerView.getLayoutManager() instanceof GridLayoutManager){
                dragFlags = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT|ItemTouchHelper.UP|ItemTouchHelper.DOWN ;
            }
            return makeMovementFlags(dragFlags,swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int targetPosition = target.getAdapterPosition();
            mAdapter.notifyItemMoved(fromPosition,targetPosition);
            //更改数据源排序
            if (fromPosition > targetPosition){
                for (int i = fromPosition ; i < targetPosition ; i++){
                    Collections.swap(datas,i,i+1);
                }
            }else {
                for (int i = fromPosition ; i> targetPosition ; i--){
                    Collections.swap(datas,i,i-1);
                }
            }
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            //当前滑动条目的position
            int currSwipePosition = viewHolder.getAdapterPosition();
            mAdapter.notifyItemRemoved(currSwipePosition);
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            //super.onSelectedChanged(viewHolder, actionState);
            //状态发生改变时回调
            //如果不是空闲状态才改变颜色，否则会报空指针异常
           if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
               viewHolder.itemView.setBackgroundColor(Color.GREEN);
           }

        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            //super.clearView(recyclerView, viewHolder);
            //动画执行完毕回调,清除背景
            viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
            //swipe和drag的动画都是有ViewCompat类来执行的，
            // 当删除完成恢复原位，否则复用的页面会出现空白，
            //
            // view此时在屏幕左边之外
            ViewCompat.setTranslationX(viewHolder.itemView,0);
        }
    }) ;
    private RecyclerViewCommonAdapter<String> mAdapter;
    private List<String> datas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear_recyclerview_activity);
        mLinearRecycler = (WrapperRecyclerView)findViewById(R.id.linear_recyclerview);
        initRecycler();
    }

    private void initRecycler() {
        datas = getDatas();
        mLinearRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new RecyclerViewCommonAdapter<String>(R.layout.recycler_item, datas, this){

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_item,s);
            }
        };
        mLinearRecycler.setAdapter(mAdapter);
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_view, mLinearRecycler, false);
        View footerView = LayoutInflater.from(this).inflate(R.layout.footer_view, mLinearRecycler, false);
        mLinearRecycler.addHeaderView(headerView);
        mLinearRecycler.addFooterView(footerView);
        mItemTouchHelper.attachToRecyclerView(mLinearRecycler);
        //mLinearRecycler.setAdapter(new LinearRecyclerViewAdapter(datas));
//        RecyclerViewCommonAdapter adapter = new RecyclerViewCommonAdapter(R.layout.recycler_item,datas,this) {
//            @Override
//            protected void convert(ViewHolder holder, Object o, int position) {
//                holder.setText(R.id.tv_item , datas.get(position));
//            }
//        };
//        WrapRecyclerAdapter wrapRecyclerAdapter = new WrapRecyclerAdapter(adapter);
//        View headerView = LayoutInflater.from(this).inflate(R.layout.header_view, mLinearRecycler, false);
//        View footerView = LayoutInflater.from(this).inflate(R.layout.footer_view, mLinearRecycler, false);
//        wrapRecyclerAdapter.addHeaderView(headerView);
//        wrapRecyclerAdapter.addFooterView(footerView);
//        mLinearRecycler.setAdapter(wrapRecyclerAdapter);
        mLinearRecycler.addItemDecoration(new GridItemDecoration(this,R.drawable.linear_divide));
    }

    private List<String> getDatas() {
        List<String> items = new ArrayList<>() ;
        for (int i = 0 ; i < 30 ; i ++){
            items.add(i+"");
        }
        return items;
    }

    private class LinearRecyclerViewAdapter extends RecyclerView.Adapter<LinearRecyclerViewAdapter.ViewHolder>{

        private final List<String> datas;

        public LinearRecyclerViewAdapter(List<String> datas) {
            this.datas = datas ;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView tv = new TextView(LinearRecyclerviewActivity.this);
            tv.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(0,30,0,30);
            //tv.setBackgroundColor(getColor(R.color.colorPrimary));
            return new ViewHolder(tv);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv.setText(datas.get(position));
        }

        @Override
        public int getItemCount() {
            return datas.size() ;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tv ;
            public ViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView ;
            }
        }
    }
}

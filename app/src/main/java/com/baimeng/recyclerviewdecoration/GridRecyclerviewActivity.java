package com.baimeng.recyclerviewdecoration;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baimeng.recyclerviewdecoration.decoration.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/12.
 */

public class GridRecyclerviewActivity extends AppCompatActivity {

    private RecyclerView mLinearRecycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear_recyclerview_activity);
        mLinearRecycler = (RecyclerView)findViewById(R.id.linear_recyclerview);
        initRecycler();
    }

    private void initRecycler() {
        List<String> datas = getDatas();
        //mLinearRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mLinearRecycler.setLayoutManager(new GridLayoutManager(this,3));
        mLinearRecycler.setAdapter(new LinearRecyclerViewAdapter(datas));
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
            TextView tv = new TextView(GridRecyclerviewActivity.this);
            tv.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(0,30,0,30);
            tv.setBackgroundColor(getColor(R.color.colorPrimary));
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

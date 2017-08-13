package com.baimeng.recyclerviewdecoration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.baimeng.recyclerviewdecoration.decoration.GridItemDecoration;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mShu;
    private Button mHeng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShu = (Button)findViewById(R.id.shu);
        mShu.setOnClickListener(this);
        mHeng = (Button)findViewById(R.id.heng);
        mHeng.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shu :
                startActivity(new Intent(MainActivity.this,LinearRecyclerviewActivity.class));
                break ;
            case R.id.heng :
                startActivity(new Intent(MainActivity.this,GridRecyclerviewActivity.class));
                break ;
        }
    }
}

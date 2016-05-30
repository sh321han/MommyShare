package com.sh321han.mommyshare.MyProductDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sh321han.mommyshare.Main.MainActivity;
import com.sh321han.mommyshare.R;
import com.sh321han.mommyshare.data.MyProductDetailData;

public class MyProductDetailActivity extends AppCompatActivity {

    int home = 0;
    RecyclerView recyclerView;
    MyProductDetailAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    MyProductDetailData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        home = intent.getExtras().getInt("home");


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mAdapter = new MyProductDetailAdapter();
        recyclerView.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        initData(name);
    }

    private void initData(String name) {

        //네트워크 매니저를 받아온 데이터

        data.setProduct_name(name);
//        data.setPrice(20000);
//        data.setDeposit(10000);
//        data.setPeriod(20);
//        data.setLocation("서울시 서초구 서초동");
//        data.setDetail("~~~~~~~~~~~~~~~~");
//        data.setReview_profile(ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
//        data.setReview_name("이름");
//        data.setReview_time("시간");
//        data.setReview_content("이 상품 어쩌고저쩌고");
        mAdapter.add(data);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {

            switch (home) {
                case 0:
                    finish();
                    break;

                case 1:
                    Intent i = new Intent(MyProductDetailActivity.this, MainActivity.class);
                    startActivity(i);
                    break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
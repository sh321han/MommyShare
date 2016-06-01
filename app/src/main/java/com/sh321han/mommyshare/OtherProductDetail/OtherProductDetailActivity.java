package com.sh321han.mommyshare.OtherProductDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sh321han.mommyshare.Manager.NetworkManager;
import com.sh321han.mommyshare.R;
import com.sh321han.mommyshare.data.ProductDetailData;

import java.io.IOException;

import okhttp3.Request;

public class OtherProductDetailActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    OtherProductDetailAdapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        initData(id);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mAdapter = new OtherProductDetailAdapter();
        recyclerView.setAdapter(mAdapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void initData(String id) {


        NetworkManager.getInstance().ProductDetail(id, new NetworkManager.OnResultListener<ProductDetailData>() {
            @Override
            public void onSuccess(Request request, ProductDetailData result) {

                mAdapter.add(result);

            }

            @Override
            public void onFail(Request request, IOException exception) {


            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

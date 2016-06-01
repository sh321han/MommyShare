package com.sh321han.mommyshare.Main;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sh321han.mommyshare.Manager.NetworkManager;
import com.sh321han.mommyshare.OtherProductDetail.OtherProductDetailActivity;
import com.sh321han.mommyshare.R;
import com.sh321han.mommyshare.data.MainProduct;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    RecyclerView listView;
    MainProductAdapter mAdapter;


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        listView = (RecyclerView) view.findViewById(R.id.main_rv_list);
        mAdapter = new MainProductAdapter();
        listView.setAdapter(mAdapter);

        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mAdapter.getItemViewType(position)) {
                    case MainProductAdapter.VIEW_TYPE_SPINNER:
                        return 2;
                }
                return 1;
            }
        });

        listView.setLayoutManager(manager);


        mAdapter.setOnItemClickListener(new MainProductViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(View view, MainProduct product) {
                Intent intent = new Intent(getContext(), OtherProductDetailActivity.class);
                intent.putExtra("id", product.get_id());
                startActivity(intent);
            }
        });
        setData();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    private void setData() {


        NetworkManager.getInstance().MainProductList(new NetworkManager.OnResultListener<List<MainProduct>>() {
            @Override
            public void onSuccess(Request request, List<MainProduct> result) {

                mAdapter.clear();
                mAdapter.addAll(result);
            }

            @Override
            public void onFail(Request request, IOException exception) {


            }
        });
    }
}

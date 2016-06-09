package com.sh321han.mommyshare.OtherProductDetail;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sh321han.mommyshare.R;


/**
 * Created by Administrator on 2016-05-24.
 */
public class OtherPagerViewHolder extends RecyclerView.ViewHolder {

//    private static final String MOMMYSHARE_SERVER = "http://52.79.57.157:3000";
    OtherPagerAdapter mAdapter;
    ViewPager pager;



    public OtherPagerViewHolder(View itemView) {
        super(itemView);

        pager = (ViewPager)itemView.findViewById(R.id.pager);

        mAdapter = new OtherPagerAdapter();
        pager.setAdapter(mAdapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setData();

        pager.setCurrentItem(0);

    }

    public void setData() {
        for (int i = 0; i < 3; i++) {
            mAdapter.add("item : " + i);
        }
    }
}

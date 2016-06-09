package com.sh321han.mommyshare.OtherProductDetail;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-05-24.
 */
public class OtherPagerAdapter extends PagerAdapter {

    List<String> items = new ArrayList<String>();
    List<View> scrappedView = new ArrayList<View>();

    public static final int VIEW_COUNT = 3;

    public void add(String item) {
        items.add(item);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return VIEW_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TextView textView = null;


        textView = new TextView(container.getContext());
        textView.setBackgroundColor(Color.GREEN);


        textView.setText(items.get(position));

        container.addView(textView);
        return textView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
        scrappedView.add(view);

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

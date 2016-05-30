package com.sh321han.mommyshare.MyProductDetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sh321han.mommyshare.R;
import com.sh321han.mommyshare.data.MyProductDetailData;

/**
 * Created by Administrator on 2016-05-21.
 */
public class MyDescViewHolder extends RecyclerView.ViewHolder {
    TextView nameView, priceView, depositView, periodView, locationView;
    MyProductDetailData data;

    public MyDescViewHolder(View itemView) {
        super(itemView);
        nameView = (TextView)itemView.findViewById(R.id.text_name);
        priceView = (TextView)itemView.findViewById(R.id.text_price);
        depositView = (TextView)itemView.findViewById(R.id.text_deposit);
        periodView = (TextView)itemView.findViewById(R.id.text_period);
        locationView = (TextView)itemView.findViewById(R.id.text_location);
    }

    public void setData(MyProductDetailData data) {
        this.data = data;
        nameView.setText(data.getProduct_name());
        priceView.setText("대여료 | " + data.getPrice());
        depositView.setText("보증금 | " + data.getDeposit());
        periodView.setText("대여기간 | " + data.getPeriod());
        locationView.setText("대여장소 | " + data.getLocation());

    }

}

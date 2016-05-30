package com.sh321han.mommyshare.OtherProductDetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sh321han.mommyshare.R;
import com.sh321han.mommyshare.data.ProductReview;

/**
 * Created by Tacademy on 2016-05-23.
 */
public class OtherReviewViewHolder extends RecyclerView.ViewHolder {
    ProductReview data;
    ImageView profileView;
    TextView nameView, timeView, reviewView;

    public OtherReviewViewHolder(View itemView) {
        super(itemView);
        profileView = (ImageView)itemView.findViewById(R.id.image_profile);
        nameView = (TextView)itemView.findViewById(R.id.text_name);
        timeView = (TextView)itemView.findViewById(R.id.text_time);
        reviewView = (TextView)itemView.findViewById(R.id.text_review);
    }

    public void setData(ProductReview data) {
        this.data = data;

        reviewView.setText(data.getReview());
        nameView.setText(data.getReview_name());


    }
}

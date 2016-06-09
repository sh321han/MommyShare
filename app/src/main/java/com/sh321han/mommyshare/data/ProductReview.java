package com.sh321han.mommyshare.data;

import android.graphics.drawable.Drawable;

/**
 * Created by Tacademy on 2016-05-27.
 */
public class ProductReview {

    public Drawable review_profile;
    public String review_name;
    public String review_time;
    public String review;

    public Drawable getReview_profile() {
        return review_profile;
    }

    public void setReview_profile(Drawable review_profile) {
        this.review_profile = review_profile;
    }

    public String getReview_name() {
        return review_name;
    }

    public void setReview_name(String review_name) {
        this.review_name = review_name;
    }

    public String getReview_time() {
        return review_time;
    }

    public void setReview_time(String review_time) {
        this.review_time = review_time;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}

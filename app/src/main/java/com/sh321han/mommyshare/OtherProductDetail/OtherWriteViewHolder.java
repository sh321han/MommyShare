package com.sh321han.mommyshare.OtherProductDetail;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.sh321han.mommyshare.R;

/**
 * Created by Tacademy on 2016-05-23.
 */
public class OtherWriteViewHolder extends RecyclerView.ViewHolder {

    EditText editReview;
    public OtherWriteViewHolder(View itemView) {
        super(itemView);
        editReview = (EditText)itemView.findViewById(R.id.editReview);
    }
}

package com.sh321han.mommyshare.Write;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.sh321han.mommyshare.Manager.NetworkManager;
import com.sh321han.mommyshare.MyProductDetail.MyProductDetailActivity;
import com.sh321han.mommyshare.R;
import com.sh321han.mommyshare.data.WriteData;

import java.io.IOException;

import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class WriteDetailFragment extends Fragment {

    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_GALLERY = 2;
    private ImageView imgview1, imgview2;


    EditText editText;

    public WriteDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final String name = getArguments().getString("name");
        final int rent_fee = getArguments().getInt("rent_fee");
        final int rent_deposit = getArguments().getInt("rent_deposit");
        final int rent_min_period = getArguments().getInt("rent_min_period");
        final int rent_max_period = getArguments().getInt("rent_max_period");


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_write_detail, container, false);


        editText = (EditText) view.findViewById(R.id.edit_detail);
        editText.requestFocus();


        Button btn = (Button) getActivity().findViewById(R.id.btn_next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //네트워크 매니저


                NetworkManager.getInstance().ProductWrite(name, rent_fee, rent_deposit, rent_min_period, rent_max_period,
                        new NetworkManager.OnResultListener<WriteData>() {
                            @Override
                            public void onSuccess(Request request, WriteData result) {
                                Intent i = new Intent(getActivity(), MyProductDetailActivity.class);
                                i.putExtra("content", editText.getText().toString());
                                i.putExtra("name", name);
                                i.putExtra("rent_fee", rent_fee);
                                i.putExtra("rent_deposit", rent_deposit);
                                i.putExtra("rent_min_period", rent_min_period);
                                i.putExtra("rent_max_period", rent_max_period);
                                i.putExtra("home", 1);
                                startActivity(i);

                            }

                            @Override
                            public void onFail(Request request, IOException exception) {

                            }
                        });
            }
        });


        imgview1 = (ImageView) view.findViewById(R.id.image1);
        imgview2 = (ImageView) view.findViewById(R.id.image2);

        ImageButton btn_camera = (ImageButton) view.findViewById(R.id.btn_camera);
        ImageButton btn_gallery = (ImageButton) view.findViewById(R.id.btn_gallery);


        btn_camera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // 카메라 호출
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());

                // 이미지 잘라내기 위한 크기
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 0);
                intent.putExtra("aspectY", 0);
                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 150);

                try {
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, PICK_FROM_CAMERA);
                } catch (ActivityNotFoundException e) {
                    // Do nothing for now
                }
            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent();
                // Gallery 호출
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // 잘라내기 셋팅
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 0);
                intent.putExtra("aspectY", 0);
                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 150);
                try {
                    intent.putExtra("return-data", true);
                    startActivityForResult(Intent.createChooser(intent,
                            "Complete action using"), PICK_FROM_GALLERY);
                } catch (ActivityNotFoundException e) {
                    // Do nothing for now
                }
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_FROM_CAMERA) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                imgview1.setImageBitmap(photo);
            }
        }
        if (requestCode == PICK_FROM_GALLERY) {
            Bundle extras2 = data.getExtras();
            if (extras2 != null) {
                Bitmap photo1 = extras2.getParcelable("data");
                imgview1.setImageBitmap(photo1);
            }

        }

    }
}



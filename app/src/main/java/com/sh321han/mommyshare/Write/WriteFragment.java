package com.sh321han.mommyshare.Write;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sh321han.mommyshare.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WriteFragment extends Fragment {

    EditText edit_name, edit_price, edit_deposit, edit_max_period, edit_min_period;


    public WriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_write, container, false);

        edit_name = (EditText)v.findViewById(R.id.edit_name);
        edit_price = (EditText)v.findViewById(R.id.edit_price);
        edit_deposit = (EditText)v.findViewById(R.id.edit_deposit);
        edit_max_period = (EditText)v.findViewById(R.id.edit_max_period);
        edit_min_period = (EditText)v.findViewById(R.id.edit_min_period);

        Button btn = (Button)getActivity().findViewById(R.id.btn_next);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle  b = new Bundle();
                b.putString("name",edit_name.getText().toString());
                b.putInt("rent_fee", Integer.parseInt(edit_price.getText().toString()));
                b.putInt("rent_deposit", Integer.parseInt(edit_deposit.getText().toString()));
                b.putInt("rent_min_period", Integer.parseInt(edit_min_period.getText().toString()));
                b.putInt("rent_max_period", Integer.parseInt(edit_max_period.getText().toString()));
                WriteLocationFragment nextFragment = new WriteLocationFragment();
                nextFragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.container, nextFragment).commit();
            }
        });

        return v;
    }

}

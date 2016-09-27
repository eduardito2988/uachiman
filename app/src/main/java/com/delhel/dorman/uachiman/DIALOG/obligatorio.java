package com.delhel.dorman.uachiman.DIALOG;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;


import com.delhel.dorman.uachiman.R;

import java.util.ArrayList;

/**
 * Created by Usuario on 26/08/2016.
 */
public class obligatorio extends DialogFragment {


    ListView mylist;
    public ArrayList<String> stringArrayList = new ArrayList<String>();

    ImageView close_icon;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment, null, false);
        mylist = (ListView) view.findViewById(R.id.list_obligatorio);

        close_icon = (ImageView) view.findViewById(R.id.close_icon);

        close_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });


        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, stringArrayList);

        mylist.setAdapter(adapter);


    }

}

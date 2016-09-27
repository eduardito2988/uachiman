package com.delhel.dorman.uachiman.Fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delhel.dorman.uachiman.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ingreso_operario extends Fragment {


    public Ingreso_operario() {
        // Required empty public constructor
    }

    View rooView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         rooView=inflater.inflate(R.layout.fragment_ingreso_operario, container, false);
        return  rooView;
    }

}

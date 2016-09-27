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
public class Salida_novedad extends Fragment {


    public Salida_novedad() {
        // Required empty public constructor
    }
  View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_salida_novedad, container, false);
      return  rootView;
    }

}

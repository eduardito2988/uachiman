package com.delhel.dorman.uachiman.Fragmentos;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.delhel.dorman.uachiman.Custom.MiFragmentPagerAdapter;
import com.delhel.dorman.uachiman.Interface.Comunicacion;
import com.delhel.dorman.uachiman.Interface.RecyclerViewOnClickListener3;
import com.delhel.dorman.uachiman.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_principal_encargo extends Fragment{

    View rootview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_fragment_principal_encargo, container, false);

        getActivity().setTitle("ENCARGOS");// TITULO FRAGMENT

        String[] titulos = {"REGISTRO DE ENCARGO", "ENTREGA DE ENCARGO"};
        Fragment[] fragment = {new Ingreso_encargo1(), new Salida_encargo()};

        final ViewPager viewPager = (ViewPager) rootview.findViewById(R.id.viewpagertabencargo);
        viewPager.setAdapter(new MiFragmentPagerAdapter(getChildFragmentManager(), titulos, fragment)); //pasar datos a adaptador
        TabLayout tabLayout = (TabLayout) rootview.findViewById(R.id.appbartabsencargo);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);// tipo de tab
        tabLayout.setupWithViewPager(viewPager);



        return rootview;
    }

}

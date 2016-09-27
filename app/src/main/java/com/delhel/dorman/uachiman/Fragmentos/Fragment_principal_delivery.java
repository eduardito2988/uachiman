package com.delhel.dorman.uachiman.Fragmentos;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.delhel.dorman.uachiman.Custom.MiFragmentPagerAdapter;
import com.delhel.dorman.uachiman.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_principal_delivery extends Fragment {


    public Fragment_principal_delivery() {
        // Required empty public constructor
    }

 View rooView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rooView= inflater.inflate(R.layout.fragment_fragment_principal_delivery, container, false);


        getActivity().setTitle("DELIVERY");// TITULO FRAGMENT

        String[] titulos = {"INGRESO DELIVERY", "SALIDA DELIVERY"};

        Fragment[] fragment = {new Ingreso_delivery(),new Salida_Invitado()};

        int iconos[] = {
                R.drawable.ingreso_delivery,
                R.drawable.salida_delivery,
        };

        ViewPager viewPager = (ViewPager) rooView.findViewById(R.id.viewpagertabdelivery);
        viewPager.setAdapter(new MiFragmentPagerAdapter(getChildFragmentManager(), titulos, fragment)); //pasar datos a adaptador
        TabLayout tabLayout = (TabLayout) rooView.findViewById(R.id.appbartabdelivery);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);// tipo de tab
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(iconos[0]);//agregar icono
        tabLayout.getTabAt(1).setIcon(iconos[1]);


        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#97233F"), PorterDuff.Mode.SRC_ATOP);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#4D4E4F"), PorterDuff.Mode.SRC_ATOP);
        //  tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#4D4E4F"), PorterDuff.Mode.SRC_ATOP);
        //  tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor(String.valueOf(R.color.lineaGris_Oscuro)), PorterDuff.Mode.SRC_ATOP);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor(String.valueOf("#97233F")), PorterDuff.Mode.SRC_ATOP);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor(String.valueOf("#4D4E4F")), PorterDuff.Mode.SRC_ATOP);
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor(String.valueOf("#999999")), PorterDuff.Mode.SRC_ATOP);
            }
        });



        return  rooView;
    }

}

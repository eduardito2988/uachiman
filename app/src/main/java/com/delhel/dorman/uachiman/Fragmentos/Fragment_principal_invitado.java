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


public class Fragment_principal_invitado extends Fragment {


    public Fragment_principal_invitado() {
        // Required empty public constructor
    }

    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview= inflater.inflate(R.layout.fragment_fragment_principal_invitado, container, false);


        getActivity().setTitle("INVITADO");// TITULO FRAGMENT

        String[] titulos = {"ENTRADA INVITADO ", "SALIDA INVITADO "};

        Fragment[] fragment = {new Ingreso_Invitado(),new Salida_Invitado()};

        int iconos[] = {
                R.drawable.ingreso_user,
                R.drawable.emergency_exit,
            //    R.drawable.add_usuario
        };


        ViewPager viewPager = (ViewPager) rootview.findViewById(R.id.viewpagertabinvitado);
        viewPager.setAdapter(new MiFragmentPagerAdapter(getChildFragmentManager(), titulos, fragment)); //pasar datos a adaptador

        TabLayout tabLayout = (TabLayout) rootview.findViewById(R.id.appbartabsinvitado);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);// tipo de tab
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(iconos[0]);//agregar icono
        tabLayout.getTabAt(1).setIcon(iconos[1]);
       // tabLayout.getTabAt(2).setIcon(iconos[2]);

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



        return  rootview;
    }

}

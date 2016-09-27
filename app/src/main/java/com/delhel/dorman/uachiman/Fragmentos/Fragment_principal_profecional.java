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
public class Fragment_principal_profecional extends Fragment {


    public Fragment_principal_profecional() {
        // Required empty public constructor
    }

  View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_principal_profecional, container, false);


        getActivity().setTitle("PROFESIONAL");// TITULO FRAGMENT

        String[] titulos = {"INGRESO PROFESIONALES", "SALIDA PROFESIONAL"};
        Fragment[] fragment = {new Ingreso_profesional(),new Salida_operario()};
        int iconos[] = {R.drawable.ingreso_invitado, R.drawable.salir_puerta};

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpagertabprofesional);
        viewPager.setAdapter(new MiFragmentPagerAdapter(getChildFragmentManager(), titulos, fragment)); //pasar datos a adaptador

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.appbartabsprofesional);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);// tipo de tab
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(iconos[0]);//agregar icono
        tabLayout.getTabAt(1).setIcon(iconos[1]);
        tabLayout.getTabTextColors();

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



        return  rootView;
    }

}

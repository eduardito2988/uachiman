package com.delhel.dorman.uachiman.Fragmentos;


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
public class Fragment_principal_operario extends Fragment {


    public Fragment_principal_operario() {
        // Required empty public constructor
    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_principal_operario, container, false);

        getActivity().setTitle("OPERARIO");// TITULO FRAGMENT

        String[] titulos = {"INGRESO OPERARIO", "SALIDA OPERARIO"};
        Fragment[] fragment = {new Ingreso_operario(),new Salida_operario()};
        int iconos[] = {R.drawable.ingreso_operario, R.drawable.salida_operario,
        };

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.viewpagertaboperario);
        viewPager.setAdapter(new MiFragmentPagerAdapter(getChildFragmentManager(), titulos, fragment)); //pasar datos a adaptador

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.appbartabsoperario);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);// tipo de tab
        tabLayout.setupWithViewPager(viewPager);
      // tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"),Color.parseColor("#4D4E4F"));
       // tabLayout.getTabAt(0).setIcon(iconos[0]);//agregar icono
       // tabLayout.getTabAt(1).setIcon(iconos[1]);


      //  tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
       // tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#EEEEEE"), PorterDuff.Mode.SRC_ATOP);
        //  tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#4D4E4F"), PorterDuff.Mode.SRC_ATOP);
        //  tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor(String.valueOf(R.color.lineaGris_Oscuro)), PorterDuff.Mode.SRC_ATOP);



        return  rootView;
    }

}

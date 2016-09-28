package com.delhel.dorman.uachiman.Fragmentos;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

    private MiFragmentPagerAdapter miFragmentPagerAdapter;
    private Ingreso_encargo1 ingreso_encargo1;
    private Salida_encargo salida_encargo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_fragment_principal_encargo, container, false);
        getActivity().setTitle("ENCARGOS");// TITULO FRAGMENT
        return rootview;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] titulos = {"REGISTRO DE ENCARGO", "ENTREGA DE ENCARGO"};
        ingreso_encargo1= new Ingreso_encargo1();
        salida_encargo= new Salida_encargo();
        Fragment[] fragment = {ingreso_encargo1, salida_encargo};

        final ViewPager viewPager = (ViewPager) getView().findViewById(R.id.viewpagertabencargo);
        miFragmentPagerAdapter= new MiFragmentPagerAdapter(getChildFragmentManager(), titulos, fragment);
        viewPager.setAdapter(miFragmentPagerAdapter); //pasar datos a adaptador
        TabLayout tabLayout = (TabLayout) getView().findViewById(R.id.appbartabsencargo);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);// tipo de tab
        tabLayout.setupWithViewPager(viewPager);
    }

    public void updateSalida(){
        if(salida_encargo!=null){
            salida_encargo.refresh();
        }
    }

}

package com.delhel.dorman.uachiman.Fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.delhel.dorman.uachiman.Custom.AdaptadorRecycle_add_profecional;
import com.delhel.dorman.uachiman.Custom.CustomerAdapter_profesional;
import com.delhel.dorman.uachiman.R;
import com.delhel.dorman.uachiman.Clases.Modelo_Profesionales;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ingreso_profesional extends Fragment {


    public Ingreso_profesional() {
        // Required empty public constructor
    }

   AdaptadorRecycle_add_profecional adapter_add_prof ;
  ArrayList<Modelo_Profesionales> lista;
  ArrayList<Modelo_Profesionales>lista_agrego;
  AdaptadorRecycle_add_profecional adaptadorRecycle_add_pro;
  View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =inflater.inflate(R.layout.fragment_ingreso_profesional, container, false);

        String[]gerencias ={"GERENCIA 1","GERENCIA2","GERENCIA3","GERENCIA4"};
        Spinner  spinner = (Spinner)rootView.findViewById(R.id.spinner3);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,gerencias);
        spinner.setAdapter(adapter);


        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recicle_profecional);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        lista= new ArrayList<>();
        lista.add(new Modelo_Profesionales("1","EDUARDO ROSALES","GERENCIA1","42553636","ASISTENSIA"));
        lista.add(new Modelo_Profesionales("2","CAROLINA PEREZ","GERENCIA2","53662626","ASISTENSIA"));
        lista.add(new Modelo_Profesionales("3","ANTONIO SISTEMAS","GERENCIA1","98996969","AYUDA MEDICA"));

        lista_agrego = new ArrayList<>();

        Button boton_agregar = (Button)rootView.findViewById(R.id.btn_agregar);
        final EditText nombre = (EditText)rootView.findViewById(R.id.txtnom_invit);
        final AutoCompleteTextView dni = (AutoCompleteTextView) rootView.findViewById(R.id.autocompletDNI);

        boton_agregar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String nom = nombre.getText().toString();
                String dnis = dni.getText().toString();

                if(nom.length()==0 || dnis.length()==0) {
                    Toast.makeText(getActivity(), "Ingrese Datos", Toast.LENGTH_SHORT).show();
                }else{

                    lista.add(new Modelo_Profesionales("3",nom,"GERENCIA1",dnis,"AYUDA MEDICA"));

                    adapter_add_prof.notifyDataSetChanged();
                    nombre.setText("");
                    dni.setText("");


                }
            }
        });

     /**** mostrar recycle ***/
        adaptadorRecycle_add_pro = new AdaptadorRecycle_add_profecional(lista,getActivity());
        recyclerView.setAdapter(adaptadorRecycle_add_pro);

   /* autocomplet dni*/



        AutoCompleteTextView customerAutoComplete = (AutoCompleteTextView)rootView.findViewById(R.id.autocompletDNI);

        CustomerAdapter_profesional customerAdapter = new CustomerAdapter_profesional(getActivity(), R.layout.customer_auto, lista);
        customerAutoComplete.setThreshold(0);
        customerAutoComplete.setAdapter(customerAdapter);

        customerAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               // adapterView.getItemAtPosition(i);
                Toast.makeText(view.getContext(), "click aqui"+adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();

            }
        });

       return  rootView;
    }

}

package com.delhel.dorman.uachiman.Fragmentos;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.delhel.dorman.uachiman.Clases.Modelo_departamentos;
import com.delhel.dorman.uachiman.Custom.AdaptadorRecycle_add_invitado;
import com.delhel.dorman.uachiman.Custom.CustomerAdapter;
import com.delhel.dorman.uachiman.Clases.Modelo_invitado;
import com.delhel.dorman.uachiman.Custom.CustomerAdapter_departamentos;
import com.delhel.dorman.uachiman.Dao.Dencargos;
import com.delhel.dorman.uachiman.R;

import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Ingreso_Invitado extends Fragment {

    public Ingreso_Invitado() {
        // Required empty public constructor
    }

    View rootview;
    TextView editBuscar;
    ArrayList<Modelo_invitado> list;
    ListView listas;
    ArrayAdapter  mLeadsAdapter;
    AutoCompleteTextView autoCompleteTextView ;
    List<Modelo_departamentos> list_departamento;
    Dencargos myDaoSQLite ;
    EditText txtNombre;
    AdaptadorRecycle_add_invitado adaptadorRecycle_add_invitado ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview=inflater.inflate(R.layout.ingreso__invitado, container, false);

        myDaoSQLite = new Dencargos(getActivity());
        autoCompleteTextView = (AutoCompleteTextView) rootview.findViewById(R.id.spinner_gerencia);
        txtNombre = (EditText) rootview.findViewById(R.id.txtnom_invit);
       final EditText nombre = (EditText)rootview.findViewById(R.id.txtnom_invit);
        final  AutoCompleteTextView dni = (AutoCompleteTextView) rootview.findViewById(R.id.autocompletDNI);


        //////
        /* autocompletador */
        list_departamento= new ArrayList<>();
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                list_departamento = myDaoSQLite.llenarCombo_departamentos(charSequence.toString());
                CustomerAdapter_departamentos customerAdapter = new CustomerAdapter_departamentos(getActivity(), R.layout.customer_auto, list_departamento);
                autoCompleteTextView.setThreshold(0);
                autoCompleteTextView.setAdapter(customerAdapter);
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //   adapterView.getItemAtPosition(i);
                Modelo_departamentos productos = list_departamento.get(0);
                String nombre = productos.getUs_pel_ch100()+" "+productos.getUsu_nomb_ch100();
                autoCompleteTextView.setText(nombre);
               // numerodep.setText(productos.getUni_codi_in20());
                //  list_departamento.get(i).getUsu_nomb_ch100();

            }
        });

        /*****************/

        RecyclerView recyclerView = (RecyclerView)rootview.findViewById(R.id.lista_invitado);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        list= new ArrayList<>();
       /* list.add(new Modelo_invitado("1", "JOSE", "42456868", "02"));
        list.add(new Modelo_invitado("2", "EDUARDO ROSALES CARLOS", "40558685", "03"));
        list.add(new Modelo_invitado("3", "BRYAN", "78098846", "04"));*/
        list.add(new Modelo_invitado("4", "ANTONIO", "304150858", "03"));
        list.add(new Modelo_invitado("5", "NESTOR", "40558685", "03"));

       /* ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, valores);
        destino = (AutoCompleteTextView)rootview.findViewById(R.id.autocompletDNI);

        destino.setThreshold(0);
        destino.setAdapter(adapter);

        listas = (ListView) rootview.findViewById(R.id.lista_invitado);
        listado = new ArrayList<>();
        */

        /* boton agregar */
        Button btnagregar = (Button)rootview.findViewById(R.id.btn_agregar);
        btnagregar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String nom = nombre.getText().toString();
                String dnis = dni.getText().toString();

                if(nom.length()==0 || dnis.length()==0) {
                    Toast.makeText(getActivity(), "Ingrese Datos", Toast.LENGTH_SHORT).show();
                }else{

                    list.add(new Modelo_invitado("1", nom, dnis, "02"));
                    adaptadorRecycle_add_invitado.notifyDataSetChanged();
                    nombre.setText("");
                    dni.setText("");

                }


            }
        });

        adaptadorRecycle_add_invitado = new AdaptadorRecycle_add_invitado(list,getActivity());
        recyclerView.setAdapter(adaptadorRecycle_add_invitado);

        /* boton fin */

        AutoCompleteTextView customerAutoComplete = (AutoCompleteTextView)rootview.findViewById(R.id.autocompletDNI);
        CustomerAdapter customerAdapter = new CustomerAdapter(getActivity(), R.layout.customer_auto, list);
        customerAutoComplete.setThreshold(1);
        customerAutoComplete.setAdapter(customerAdapter);

        customerAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                adapterView.getItemAtPosition(i);
                Toast.makeText(view.getContext(), "click aqui"+adapterView.getItemAtPosition(i), Toast.LENGTH_SHORT).show();


            }
        });


        return  rootview;
    }


 public  void LLamarBuscador (final View view){

        AlertDialog.Builder builder;
        AlertDialog alertDialog;

        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_buscador_invitado, null);

        SearchView  searchView = (SearchView)layout.findViewById(R.id.txtdni);
        searchView.setQueryHint("Buscar DNI");
        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(view.getContext(), "--->"+query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Toast.makeText(view.getContext(), "--->"+newText, Toast.LENGTH_SHORT).show();


                return true;
            }
        });

       // TextView text = (TextView) layout.findViewById(R.id.texto_dialog);
       // text.setText("hola");

      //  ImageView image = (ImageView) layout.findViewById(R.id.imagen_dialog);
        // image.setImageResource(R.drawable.avatar_roy);
       // Picasso.with(view.getContext()).load(listado.get(position).getFoto()).into(image);
        builder = new AlertDialog.Builder(view.getContext());
        builder.setView(layout);
        alertDialog = builder.create();
        alertDialog.show();

    }


}

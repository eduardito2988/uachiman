package com.delhel.dorman.uachiman.Fragmentos;


import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.delhel.dorman.uachiman.Clases.Acordeon.ChildrenEncargo;
import com.delhel.dorman.uachiman.Clases.Acordeon.HeaderEncargo;
import com.delhel.dorman.uachiman.Constantes.Constantes;
import com.delhel.dorman.uachiman.Custom.AdaptadorAcordeon;
import com.delhel.dorman.uachiman.Dao.Dencargos;
import com.delhel.dorman.uachiman.Interface.RecyclerViewOnClickListener2;
import com.delhel.dorman.uachiman.Interface.RecyclerViewOnClickListener3;
import com.delhel.dorman.uachiman.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.TokenIterator;

/**
 * A simple {@link Fragment} subclass.
 */
public class Salida_encargo extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View rootView;
    public static AdaptadorAcordeon listAdapter;
    ExpandableListView expListView;
    List<HeaderEncargo> listDataHeader;
    HashMap<HeaderEncargo, List<ChildrenEncargo>> listDataChild;
    List<ChildrenEncargo> top250;
    SearchView searchView;
    Dencargos daoSQLite;


    public Salida_encargo() {
        // Required empty public constructor
    }


    public static Salida_encargo newInstance(String param1, String param2) {
        Salida_encargo fragment = new Salida_encargo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.salida_encargo, container, false);

        seteo();

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View clickedView, int groupPosition, long id) {

                ImageView groupIndicator = (ImageView) clickedView.findViewById(R.id.help_group_indicator);
                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                    groupIndicator.setImageResource(R.drawable.plus);
                } else {
                    parent.expandGroup(groupPosition);
                    groupIndicator.setImageResource(R.drawable.minus);
                }
                return true;
            }
        });


        prepareListData();

        searchView = (SearchView) rootView.findViewById(R.id.buscador_encargo);
        searchView.setQueryHint("Buscar Encargo");
        searchView.setFocusable(true);
        searchView.onActionViewExpanded();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                List<HeaderEncargo> filtros = filter(listDataHeader, newText);
                listAdapter.SetFiltrado(filtros);
                return true;
            }
        });

        return rootView;
    }


    private List<HeaderEncargo> filter(List<HeaderEncargo> listDataHeader, String newText) {

        String texto = newText.toLowerCase();

        final List<HeaderEncargo> filtrando = new ArrayList<>();

        for (HeaderEncargo mod : listDataHeader) {

            final String dato = mod.getUni_numi_vc20().toLowerCase();
            final String numero = mod.getUsuario().toLowerCase();

            if (dato.contains(texto)) {
                filtrando.add(mod);
            } else if (numero.contains(texto)) {
                filtrando.add(mod);
            }
        }

        return filtrando;
    }


    public void seteo() {
        // rootView = inflater.inflate(R.layout.salida_encargo, container, false);
        expListView = (ExpandableListView) this.rootView.findViewById(R.id.expandableListView);
        expListView.setGroupIndicator(null);
    }


    public void re() {

        Log.e("mensaje", "-->" + listDataHeader.size());

        //List<HeaderEncargo> filtros2 = listDataHeader;
        //listAdapter.refrescar2(filtros2);
    }

    public void prepareListData() {

        listDataChild = new HashMap<>();
        listDataHeader = new ArrayList<>();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
        requestParams.add("entidad", String.valueOf(2));

        RequestHandle post = client.post(Constantes.URL_ENCARGO, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject dataBD = new JSONObject(new String(responseBody));
                    JSONArray jsonArray = dataBD.optJSONArray("listado");


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        HeaderEncargo headerEncargo = new HeaderEncargo();

                        headerEncargo.setUni_codi_in20(jsonObject.getInt("uni_codi_in20"));
                        headerEncargo.setUni_numi_vc20(jsonObject.getString("uni_numi_vc20"));
                        headerEncargo.setUsuario(jsonObject.getString("usuario"));
                        headerEncargo.setCantidad(jsonObject.getInt("cantidad"));
                        listDataHeader.add(headerEncargo);


                        JSONArray jsonArray1 = jsonObject.optJSONArray("encargos");
                        top250 = new ArrayList<ChildrenEncargo>();
                        for (int x = 0; x < jsonArray1.length(); x++) {

                            JSONObject jsonObject1 = jsonArray1.getJSONObject(x);
                            ChildrenEncargo childrenEncargo = new ChildrenEncargo();
                            childrenEncargo.setCod_unidad(jsonObject.getInt("uni_codi_in20"));
                            childrenEncargo.setUnidad(jsonObject.getString("uni_numi_vc20"));
                            childrenEncargo.setCod_usuario(jsonObject1.getInt("cod_usuario"));
                            childrenEncargo.setUsuario(jsonObject1.getString("usuario"));
                            childrenEncargo.setFoto(jsonObject1.getString("usu_foto_long"));
                            childrenEncargo.setCantida(jsonObject1.getString("cantidad"));

                            top250.add(childrenEncargo);

                        }

                        listDataChild.put(listDataHeader.get(i), top250);
                    }

                    listAdapter = new AdaptadorAcordeon(getActivity(), listDataHeader, listDataChild);
                    expListView.setAdapter(listAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //   Toast.makeText(getActivity(), "->" + error, Toast.LENGTH_SHORT).show();
                Log.e("mensaje", "->Quinto!" + error.toString());
            }
        });
    }

    public void refrescarq() {

        Log.e("mensaje", "-->eso!");
        //Toast.makeText(getActivity(), "estoy aqui!", Toast.LENGTH_SHORT).show();
    }

}

package com.delhel.dorman.uachiman.Fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.delhel.dorman.uachiman.Constantes.Constantes;
import com.delhel.dorman.uachiman.Custom.AdaptadorAcordeon;
import com.delhel.dorman.uachiman.Dao.Dencargos;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class Salida_Invitado extends Fragment {


    public Salida_Invitado() {
        // Required empty public constructor
    }

    View rootView;
    AdaptadorAcordeon listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    List<String> top250;

    Dencargos daoSQLite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.salida_invitado, container, false);


        expListView = (ExpandableListView) rootView.findViewById(R.id.expandableListView);
        prepareListData();
       // listAdapter = new AdaptadorAcordeon(getActivity(), listDataHeader, listDataChild);
       // expListView.setAdapter(listAdapter);


        return rootView;
    }


    private void prepareListData() {

        listDataChild = new HashMap<String, List<String>>();
        listDataHeader=new ArrayList<String>();

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
                        listDataHeader.add(jsonObject.getString("uni_numi_vc20"));
                        JSONArray jsonArray1 = jsonObject.optJSONArray("encargos");
                        top250 = new ArrayList<String>();
                        for (int x = 0; x < jsonArray1.length(); x++) {
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(x);
                            top250.add(jsonObject1.getString("usuario"));
                        }
                        listDataChild.put(listDataHeader.get(i), top250);
                    }
                    //listAdapter = new AdaptadorAcordeon(getActivity(), listDataHeader, listDataChild);
                    expListView.setAdapter(listAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getActivity(), "->" + error, Toast.LENGTH_SHORT).show();
            }
        });

            /*
            listDataChild = new HashMap<String, List<String>>();
            // Adding child data
            listDataHeader.add("21966 | GAMARRA EDLA");
            listDataHeader.add("21967 | LANCHO RICCI ANA LUZ");
            listDataHeader.add("21969 | HEVIA CRUZ JORGE");
            // Adding child data
            List<String> top250 = new ArrayList<String>();
            top250.add("Eduardo rosales Carlos");
            top250.add("Juan jose quinto");
            List<String> nowShowing = new ArrayList<String>();
            nowShowing.add("Byayan ");
            nowShowing.add("Despicable Me 2");
            */
            /*List<String> comingSoon = new ArrayList<String>();
            comingSoon.add("Juan carlos");
            comingSoon.add("Maria teresa");
            listDataChild.put(listDataHeader.get(0), comingSoon); // Header, Child data
            /* listDataChild.put(listDataHeader.get(1), nowShowing);
            listDataChild.put(listDataHeader.get(2), comingSoon);*/

    }
}

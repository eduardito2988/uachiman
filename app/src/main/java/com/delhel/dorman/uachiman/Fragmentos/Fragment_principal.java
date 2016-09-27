package com.delhel.dorman.uachiman.Fragmentos;


import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.delhel.dorman.uachiman.Constantes.Constantes;
import com.delhel.dorman.uachiman.Interface.Comunicacion;
import com.delhel.dorman.uachiman.MainActivity;
import com.delhel.dorman.uachiman.Printer.Bluetooh;
import com.delhel.dorman.uachiman.Printer.ConstantesPrinter;
import com.delhel.dorman.uachiman.R;
import com.delhel.dorman.uachiman.Sp.QuickstartPreferences;
import com.zj.btsdk.BluetoothService;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Fragment_principal extends Fragment implements View.OnClickListener {

    View rootView;
    ImageView btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10;
    Class Framentclass = null;
    Fragment fragment = null;
    FragmentManager fragmentManager;

    int CODIGO_ENTIDAD;

    public BluetoothService mService = null;
    BluetoothDevice con_dev = null;

    private Comunicacion mListener;




    @Override
    public void onStart() {
        super.onStart();
     /*   Bluetooh bluetooh = new Bluetooh(getActivity());
        bluetooh.conectarBluetooth();
        SystemClock.sleep(2000);
        mService = bluetooh.rmService();
        con_dev = mService.getDevByMac(ConstantesPrinter.printer_name);
        mService.connect(con_dev);*/
    }

    @Override
    public void onPause() {
        super.onPause();
      //  mService.stop();
        Toast.makeText(getActivity(), "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_fragment_principal, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle("DOORMAN");// TITULO FRAGMENT
        Inicialisar_botones();

        SharedPreferences sharedPreferencesE = PreferenceManager.getDefaultSharedPreferences(getActivity());
        CODIGO_ENTIDAD= sharedPreferencesE.getInt(QuickstartPreferences.COD_ENTIDAD, 0);
        //hora y fecha panel principal
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEEE d MMMM yyyy");
        String formattedDate = df.format(c.getTime());

        TextClock hora = (TextClock) rootView.findViewById(R.id.hora_principal);
        TextView textView = (TextView)rootView.findViewById(R.id.fecha_principal);
        textView.setText(formattedDate);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    public void Inicialisar_botones() {

        btn1 = (ImageView) rootView.findViewById(R.id.btnInicio1);
        btn2 = (ImageView) rootView.findViewById(R.id.btnInicio2);
        btn3 = (ImageView) rootView.findViewById(R.id.btnInicio3);
        btn4 = (ImageView) rootView.findViewById(R.id.btnInicio4);
        btn5 = (ImageView) rootView.findViewById(R.id.btnInicio5);
        btn6 = (ImageView) rootView.findViewById(R.id.btnInicio6);
        btn7 = (ImageView) rootView.findViewById(R.id.btnInicio7);
        btn8 = (ImageView) rootView.findViewById(R.id.btnInicio8);
        btn9 = (ImageView) rootView.findViewById(R.id.btnInicio9);
       // btn10 = (ImageView) rootView.findViewById(R.id.btnInicio10);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
       // btn10.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        Intent i = null;
        MainActivity.con = 0;
         int tipo = 0;

        switch (view.getId()) {

            case R.id.btnInicio1:
                Framentclass = Fragment_principal_encargo.class;
                break;
            case R.id.btnInicio2:
                Framentclass = Fragment_principal_residente.class;
                break;
            case R.id.btnInicio3:
                Framentclass = Fragment_principal_invitado.class;
                break;
            case R.id.btnInicio4:
                Framentclass = Fragment_principal_trabajadores.class;
                break;
            case R.id.btnInicio5:
                Framentclass = Fragment_principal_operario.class;
                break;
            case R.id.btnInicio6:
                Framentclass = Fragment_principal_profecional.class;
                break;
            case R.id.btnInicio7:
                Framentclass = Fragment_principal_delivery.class;
                break;
            case R.id.btnInicio8:
                Framentclass = Fragment_principal_novedad.class;
                break;
            case R.id.btnInicio9:
                   tipo=1;
                break;
            case R.id.btnInicio10:
                //   Framentclass = Fragment_relevo.class;
                break;


        }

        if(tipo>0){

           // LLamarLogin(view);

        }else {

            /**carga fragment principal**/

            try {
                fragment = (Fragment) Framentclass.newInstance();

            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


            fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                    .replace(R.id.layou_cont, fragment).commit();
            /************************/
        }
    }


}

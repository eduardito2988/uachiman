package com.delhel.dorman.uachiman.DIALOG;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.delhel.dorman.uachiman.R;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

/**
 * Created by Usuario on 19/07/2016.
 */
public class mensaje extends DialogFragment {
    AlertDialog dialog;
    ListenerOyente listenerOyente;
    public int icono;
    public String title = "";
    public String msga = "";
    public int tipo;

    /* Tipos
    1 = texto
    2 = single
    3 = multi
    4 = custom
     */

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setIcon(icono);
        builder.setTitle(title);

        View view =  LayoutInflater.from(getActivity()).inflate(R.layout.custom_title,null);

        ImageView icon = (ImageView) view.findViewById(R.id.img_title);
        icon.setImageResource(icono);

        TextView a = (TextView) view.findViewById(R.id.textView1);
        a.setText(title);

        CheckBox checkBox = (CheckBox)view.findViewById(R.id.Checktodo);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                //((AlertDialog) dialog).getListView().setItemChecked(1, true);

                ListView listView = ((AlertDialog) dialog).getListView();
                int c = listView.getCount();


                    for ( int  i = 0;i<c;i++){
                        ((AlertDialog) dialog).getListView().setItemChecked(i, b);
                    }


               // Log.e("MENSAJE","Q"+c);

              /*  int len = listView.getCount();
                SparseBooleanArray checked = listView.getCheckedItemPositions();
                for (int i = 0; i < len; i++)
                    if (checked.get(i)) {
                        String item = cont_list.get(i);
  /* do whatever you want with the checked item */
                   // }

              //  Toast.makeText(getActivity(), "-->"+c, Toast.LENGTH_SHORT).show();*/


             //   ((AlertDialog) dialog).getListView().setItemChecked(1, true);


               // listenerOyente.selectionall(b);*/

            }
        });

        builder.setCustomTitle(view);

        if (tipo == 1) builder.setMessage(this.msga);
        if (tipo == 3) {

            final String lista[] = {
                    "Usuarios",
                    "Unidades",
                    "puertas"
            };

            boolean estado[]={false,false,false};

            builder.setMultiChoiceItems(lista, estado, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int posicion, boolean estado) {

                    String valor_seleccionado = lista[posicion];
                    listenerOyente.clickopciones(valor_seleccionado,estado);
                }
            });
        }



        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    listenerOyente.clickboton();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });

        dialog = builder.create();
        return dialog;
    }

    public interface ListenerOyente {
        public void clickboton() throws JSONException, UnsupportedEncodingException;
        public void clickopciones(String dato, boolean est);
        public void selectionall(boolean est);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listenerOyente = (ListenerOyente) activity;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onStart() {
        super.onStart();
        Button nbutton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        nbutton.setBackgroundResource(R.drawable.boton_dialogo);
        nbutton.setTextColor(Color.WHITE);

    }


    public ListView listView(){
        return dialog.getListView();
    }


}
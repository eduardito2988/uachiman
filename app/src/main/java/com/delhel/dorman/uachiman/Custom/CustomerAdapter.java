package com.delhel.dorman.uachiman.Custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.delhel.dorman.uachiman.R;
import com.delhel.dorman.uachiman.Clases.Modelo_invitado;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Usuario on 22/08/2016.
 */
public class CustomerAdapter extends ArrayAdapter<Modelo_invitado> {
    private LayoutInflater layoutInflater;
    List<Modelo_invitado> mCustomers;

    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((Modelo_invitado)resultValue).getDni();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {

                ArrayList<Modelo_invitado> suggestions = new ArrayList<Modelo_invitado>();

                for (Modelo_invitado customer : mCustomers) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (customer.getDni().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(customer);
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll((ArrayList<Modelo_invitado>) results.values);
            } else {
                // no filter, add entire original list back in
                addAll(mCustomers);
            }
            notifyDataSetChanged();
        }
    };

    public CustomerAdapter(Context context, int textViewResourceId, List<Modelo_invitado> customers) {
        super(context, textViewResourceId, customers);
        // copy all the customers into a master list
        mCustomers = new ArrayList<Modelo_invitado>(customers.size());
        mCustomers.addAll(customers);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.customer_auto, null);
        }

        Modelo_invitado customer = getItem(position);

        TextView name = (TextView) view.findViewById(R.id.customerNameLabel);
        name.setText(customer.getNombre());





        return view;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }




}
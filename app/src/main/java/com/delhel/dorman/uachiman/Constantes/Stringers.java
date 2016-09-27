package com.delhel.dorman.uachiman.Constantes;

import android.content.Context;

/**
 * Created by Usuario on 25/08/2016.
 */
public class Stringers {

    Context context;

    public Stringers(Context context) {
        this.context = context;
    }

    public String getStringResourceByName(String name) {
        String packageName = context.getPackageName();
        int resId = context.getResources().getIdentifier(name, "string", packageName);
        return context.getString(resId);
    }
}

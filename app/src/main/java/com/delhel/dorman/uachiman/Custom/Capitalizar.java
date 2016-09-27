package com.delhel.dorman.uachiman.Custom;

/**
 * Created by EDUARDO on 04/09/2016.
 */
public class Capitalizar {

    public static String ucFirst(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        } else {
            return  Character.toUpperCase(str.charAt(0)) + str.substring(1, str.length()).toLowerCase();
        }
    }

    public static String ucWords(String str) {

        String cadena[] = str.split(" ");
        String cad = "";

        for (int i = 0; i < cadena.length; i++) {
            String words = cadena[i];
            cad += Character.toUpperCase(words.charAt(0)) + words.substring(1, words.length()).toLowerCase() + " ";
        }
        return cad;
    }

}

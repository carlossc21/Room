package es.carlossc212.proyecto.objects;

import android.content.Context;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Historial {
    private static Context appContext;
    private static Fragment currentFragment;

    public static void setContext(Context context){
        appContext = context;
    }

    private static File getHistoryFile(){
        return new File(appContext.getExternalFilesDir(null), "historial.txt");
    }

    public static BufferedWriter getWriter(){
        BufferedWriter writer = null;
        try {
            writer =  new BufferedWriter(new FileWriter(getHistoryFile(), true));
        } catch (IOException e) {}

        return writer;
    }

    public static String getHistorial() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(getHistoryFile()));

        String linea;
        String texto = "";
        while((linea=br.readLine())!=null)
            texto+=linea + "\n";

        return texto;
    }

    public static void setCurrentFragment(Fragment f){
        currentFragment = f;
    }

    public static Fragment getCurrentFragment(){
        return currentFragment;
    }


}

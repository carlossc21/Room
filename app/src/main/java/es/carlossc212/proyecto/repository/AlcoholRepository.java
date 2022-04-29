package es.carlossc212.proyecto.repository;

import android.content.Context;


import androidx.lifecycle.LiveData;

import java.util.List;

import es.carlossc212.proyecto.entity.Alcohol;
import es.carlossc212.proyecto.entity.Tipo;
import es.carlossc212.proyecto.room.AlcoholDao;
import es.carlossc212.proyecto.room.AlcoholDatabase;

public class AlcoholRepository {

    private AlcoholDao dao;
    private LiveData<List<Alcohol>> alcoholesList;
    private LiveData<List<Tipo>> tiposList;


    public AlcoholRepository(Context context){
        AlcoholDatabase db = AlcoholDatabase.getDatabase(context);
        dao = db.getDao();
    }



    public LiveData<List<Alcohol>> getAllAlcoholes(){
        if (alcoholesList == null) {
            alcoholesList = dao.getAllAlcoholes();
        }
        return alcoholesList;
    }

    public LiveData<List<Tipo>> getAllTipos(){
        if (tiposList == null) {
            tiposList = dao.getAllTipos();
        }
        return tiposList;
    }

    public Alcohol getAlcohol(long id){
        return dao.getAlcohol(id);
    }

    public Alcohol getAlcohol(String nombre){
        return dao.getAlcohol(nombre);
    }

    public Tipo getTipo(long id){
        return dao.getTipo(id);
    }

    public Tipo getTipo(String nombre){
        return dao.getTipo(nombre);
    }

    public void insertAlcohol(Alcohol alcohol){
        Runnable r = () -> {
            dao.insertAlcohol(alcohol);
        };
        new Thread(r).start();
    }

    public void insertTipo(Tipo tipo){
        Runnable r = () -> {
            dao.insertTipo(tipo);
        };
        new Thread(r).start();

    }

    public void updateAlcohol(Alcohol alcohol){
        Runnable r = () -> {
            dao.updateAlcohol(alcohol);
        };
        new Thread(r).start();
    }

    public void updateTipo(Tipo tipo){
        Runnable r = () -> {
            dao.updateTipo(tipo);
        };
        new Thread(r).start();
    }

    public void deleteAlcohol(Alcohol alcohol){
        Runnable r = () -> {
            dao.deleteAlcohol(alcohol);
        };
        new Thread(r).start();
    }

    public void deleteTipo(Tipo tipo){
        Runnable r = () -> {
            dao.deleteTipo(tipo);
        };
        new Thread(r).start();
    }


}

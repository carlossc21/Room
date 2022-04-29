package es.carlossc212.proyecto.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.carlossc212.proyecto.entity.Alcohol;
import es.carlossc212.proyecto.repository.AlcoholRepository;

public class AlcoholViewModel extends AndroidViewModel {

    private AlcoholRepository repository;

    public AlcoholViewModel(@NonNull Application application) {
        super(application);

        repository = new AlcoholRepository(application);
    }

    public void insertAlcohol(Alcohol alcohol){
        repository.insertAlcohol(alcohol);
    }

    public void updateAlcohol(Alcohol alcohol){
        repository.updateAlcohol(alcohol);
    }

    public void deleteAlcohol(Alcohol alcohol){
        repository.deleteAlcohol(alcohol);
    }

    public LiveData<List<Alcohol>> getAllAlcoholes(){
        return repository.getAllAlcoholes();
    }

    public Alcohol getAlcohol(long id){
        return repository.getAlcohol(id);
    }

    public Alcohol getAlcohol(String nombre){
        return repository.getAlcohol(nombre);
    }



}

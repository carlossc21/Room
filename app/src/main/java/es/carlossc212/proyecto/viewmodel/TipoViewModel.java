package es.carlossc212.proyecto.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.carlossc212.proyecto.entity.Tipo;
import es.carlossc212.proyecto.repository.AlcoholRepository;

public class TipoViewModel extends AndroidViewModel {

    private AlcoholRepository repository;

    public TipoViewModel(@NonNull Application application) {
        super(application);

        repository = new AlcoholRepository(application);
    }

    public void insertTipo(Tipo tipo){ repository.insertTipo(tipo); }

    public void updateTipo(Tipo tipo){
        repository.updateTipo(tipo);
    }

    public void deleteTipo(Tipo tipo){
        repository.deleteTipo(tipo);
    }

    public LiveData<List<Tipo>> getAllTipos(){
        return repository.getAllTipos();
    }

    public Tipo getTipo(long id){
        return repository.getTipo(id);
    }

    public Tipo getTipo(String nombre){
        return repository.getTipo(nombre);
    }


}

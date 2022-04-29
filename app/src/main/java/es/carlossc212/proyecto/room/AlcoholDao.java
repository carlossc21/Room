package es.carlossc212.proyecto.room;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

import es.carlossc212.proyecto.entity.Alcohol;
import es.carlossc212.proyecto.entity.Tipo;

@Dao
public interface AlcoholDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insertAlcohol(Alcohol alcoholes);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insertTipo(Tipo tipos);

    @Update
    int updateAlcohol(Alcohol... alcohol);

    @Update
    int updateTipo(Tipo... tipos);

    @Delete
    int deleteAlcohol(Alcohol alcohol);

    @Delete
    int deleteTipo(Tipo tipo);

    @Query("SELECT * FROM alcohol ORDER BY nombre ASC")
    LiveData<List<Alcohol>> getAllAlcoholes();

    @Query("SELECT * FROM tipo ORDER BY nombre ASC")
    LiveData<List<Tipo>> getAllTipos();

    @Query("SELECT * FROM alcohol WHERE nombre = :nombre")
    Alcohol getAlcohol(String nombre);

    @Query("SELECT * FROM alcohol WHERE id = :id")
    Alcohol getAlcohol(long id);

    @Query("SELECT * FROM tipo WHERE nombre = :nombre")
    Tipo getTipo(String nombre);

    @Query("SELECT * FROM tipo WHERE id = :id")
    Tipo getTipo(long id);
}

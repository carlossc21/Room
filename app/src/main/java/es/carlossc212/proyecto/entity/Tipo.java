package es.carlossc212.proyecto.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tipo",
        indices = {@Index(value = "nombre", unique = true)})
public class Tipo {

    @PrimaryKey(autoGenerate = true)
    public long id = 0;

    @NonNull
    @ColumnInfo(name = "nombre")
    public String nombre;

    public String toString(){
        return nombre;
    }
}

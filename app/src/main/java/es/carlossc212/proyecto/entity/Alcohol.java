package es.carlossc212.proyecto.entity;


import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDate;

import es.carlossc212.proyecto.objects.Historial;

@Entity(tableName = "alcohol",
        indices = {@Index(value = "nombre", unique = true)},
        foreignKeys = {@ForeignKey(entity = Tipo.class, parentColumns = "id", childColumns = "idtipo", onDelete = ForeignKey.CASCADE)})

public class Alcohol implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public long id = 0;

    @NonNull
    @ColumnInfo(name = "nombre")
    public String nombre;

    @NonNull
    @ColumnInfo(name = "idtipo")
    public long idtipo;

    @ColumnInfo(name = "grados")
    public float grados;

    @ColumnInfo(name = "url")
    public String url;

    @ColumnInfo(name = "fecha")
    public String fecha;

    @ColumnInfo(name = "fecha_intro")
    public String fecha_intro;

    public Alcohol(){

    }


    protected Alcohol(Parcel in) {
        id = in.readLong();
        nombre = in.readString();
        idtipo = in.readLong();
        url = in.readString();
        fecha = in.readString();
        grados = in.readFloat();
        fecha_intro = in.readString();
    }

    public static final Creator<Alcohol> CREATOR = new Creator<Alcohol>() {
        @Override
        public Alcohol createFromParcel(Parcel in) {
            return new Alcohol(in);
        }

        @Override
        public Alcohol[] newArray(int size) {
            return new Alcohol[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(nombre);
        parcel.writeLong(idtipo);
        parcel.writeString(url);
        parcel.writeString(fecha);
        parcel.writeFloat(grados);
        parcel.writeString(fecha_intro);
    }



    public void add(){
        BufferedWriter bf = Historial.getWriter();

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                
                bf.write(LocalDate.now().toString() + " Se ha introducido " + nombre + "\n");
            }
            bf.flush();
        } catch (IOException e) {
            System.out.println("No se ha podido escribir en el historial");
        }
    }

    public void edit(){
        BufferedWriter bf = Historial.getWriter();

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                bf.write(LocalDate.now().toString() + " Se ha editado " + nombre + "\n");
            }
            bf.flush();
        } catch (IOException e) {
            System.out.println("No se ha podido escribir en el historial");
        }
    }

    public void delete(){
        BufferedWriter bf = Historial.getWriter();

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                bf.write(LocalDate.now().toString() + " Se ha borrado " + nombre + "\n");
            }
            bf.flush();
        } catch (IOException e) {
            System.out.println(" No se ha podido escribir en el historial");
        }
    }
}

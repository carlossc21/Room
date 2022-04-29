package es.carlossc212.proyecto.room;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.carlossc212.proyecto.entity.Alcohol;
import es.carlossc212.proyecto.entity.Tipo;

@Database(entities = {Alcohol.class, Tipo.class}, version = 3, exportSchema = false)
public abstract class AlcoholDatabase extends RoomDatabase {


    public abstract AlcoholDao getDao();

    private  static volatile AlcoholDatabase INSTANCE;

    public static  AlcoholDatabase getDatabase(Context context){

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AlcoholDatabase.class, "alcoholes").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}

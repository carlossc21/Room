package es.carlossc212.proyecto.view.activityfragments;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import es.carlossc212.proyecto.R;
import es.carlossc212.proyecto.databinding.ActivityMainBinding;
import es.carlossc212.proyecto.entity.Alcohol;
import es.carlossc212.proyecto.entity.Tipo;
import es.carlossc212.proyecto.objects.AddTipoDialog;
import es.carlossc212.proyecto.objects.Historial;
import es.carlossc212.proyecto.repository.AlcoholRepository;

import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private AlcoholRepository rep;
    private AlertDialog.Builder deleteTipos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.navg);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);



        init();
    }

    private void init(){
        Historial.setContext(this);
        rep = new AlcoholRepository(this);

        deleteTipos = getDeleteTipoDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.añadir_tipo) {
            AddTipoDialog at = new AddTipoDialog(this, rep);

            return true;
        }

        if (id == R.id.eliminar_tipos) {

            deleteTipos.create().show();

            return true;
        }

        if(id == R.id.historial){
            if(Historial.getCurrentFragment().getClass().isInstance(new FirstFragment())){
                NavHostFragment.findNavController(Historial.getCurrentFragment()).navigate(R.id.action_FirstFragment_to_historyFragment);
            }
            if(Historial.getCurrentFragment().getClass().isInstance(new AddAlcoholFragment())){
                NavHostFragment.findNavController(Historial.getCurrentFragment()).navigate(R.id.action_addAlcoholFragment_to_historyFragment);
            }
            if(Historial.getCurrentFragment().getClass().isInstance(new EditAlcoholFragment())){
                NavHostFragment.findNavController(Historial.getCurrentFragment()).navigate(R.id.action_editAlcoholFragment_to_historyFragment);
            }
            if(Historial.getCurrentFragment().getClass().isInstance(new EditAlcoholFragment())){
                NavHostFragment.findNavController(Historial.getCurrentFragment()).navigate(R.id.action_editAlcoholFragment_to_historyFragment);
            }

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.navg);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    private AlertDialog.Builder getDeleteTipoDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        ArrayList<Tipo> seleccionados = new ArrayList<>();

        LiveData<List<Tipo>> listaTipos = rep.getAllTipos();
        listaTipos.observe(this, tipos -> {

            CharSequence[] nombres = new CharSequence[tipos.size()];
            for (int i = 0; i < nombres.length; i++) {
                nombres[i] = tipos.get(i).nombre;
            }
            builder.setTitle("Selecciona los tipos");

            builder.setPositiveButton("Eliminar", (dialogInterface, i) -> {
                for (Tipo tipo: seleccionados) {
                    rep.deleteTipo(tipo);
                }
            });

            builder.setNegativeButton("Cancelar", (dialogInterface, i) -> {});

            builder.setMultiChoiceItems(nombres, null, (DialogInterface.OnMultiChoiceClickListener) (dialogInterface, i, b) -> {
                if (b) {
                    seleccionados.add(rep.getTipo((String) nombres[i]));
                }else if (seleccionados.contains(rep.getTipo((String) nombres[i]))) {
                    seleccionados.remove(rep.getTipo((String) nombres[i]));
                }
            });

        });

        return builder;
    }
}
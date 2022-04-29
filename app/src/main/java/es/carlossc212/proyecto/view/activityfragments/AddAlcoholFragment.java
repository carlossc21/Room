package es.carlossc212.proyecto.view.activityfragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

import es.carlossc212.proyecto.R;
import es.carlossc212.proyecto.databinding.FragmentAddAlcoholBinding;
import es.carlossc212.proyecto.databinding.FragmentEditAlcoholBinding;
import es.carlossc212.proyecto.entity.Alcohol;
import es.carlossc212.proyecto.entity.Tipo;
import es.carlossc212.proyecto.objects.Historial;
import es.carlossc212.proyecto.viewmodel.AlcoholViewModel;
import es.carlossc212.proyecto.viewmodel.TipoViewModel;


public class AddAlcoholFragment extends Fragment {

    private FragmentAddAlcoholBinding binding;

    private TipoViewModel tvm;
    private AlcoholViewModel avm;

    private Alcohol alcohol;

    private ActivityResultLauncher<Intent> arLauncher;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tvm = new TipoViewModel(getActivity().getApplication());
        avm = new AlcoholViewModel(getActivity().getApplication());
        alcohol = new Alcohol();

        arLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK){
                Uri url = result.getData().getData();
                binding.ivAlcohol.setImageURI(url);
                alcohol.url = url.toString();
                binding.lbUrl.setText(getShortImageUrl());
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddAlcoholBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.ivAlcohol.setImageResource(R.drawable.notfound);

        binding.btCalendario.setOnClickListener(v -> {
            DatePickerDialog datePicker = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                datePicker = new DatePickerDialog(getActivity());
                datePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septempre", "Octubre", "Noviembre", "Diciembre"};
                        binding.etFecha.setText(datePicker.getDayOfMonth() + "/" + meses[datePicker.getMonth()] + "/" + datePicker.getYear());

                    }
                });

                datePicker.show();
            }
        });

        binding.ivAlcohol.setOnClickListener(view1 -> {
            seleccionarImagen();
        });


        LiveData<List<Tipo>> lista = tvm.getAllTipos();
        lista.observe(getActivity(), tipos -> {
            try {
                ArrayAdapter<Tipo> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, tipos);
                binding.spTipos.setAdapter(adapter);
            }catch (NullPointerException e){}

        });

        binding.btGuardar.setOnClickListener(v -> {
            if (validaCampos()) {
                alcohol.nombre = binding.etNombre.getText().toString();
                alcohol.idtipo = tvm.getTipo(binding.spTipos.getSelectedItem().toString()).id;
                alcohol.fecha = binding.etFecha.getText().toString();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    alcohol.fecha_intro = LocalDateTime.now().toString().substring(0,LocalDateTime.now().toString().indexOf('T'));
                }
                try {
                    alcohol.grados = Float.valueOf(binding.etGrados.getText().toString());
                    avm.insertAlcohol(alcohol);
                    alcohol.add();
                    NavHostFragment.findNavController(AddAlcoholFragment.this).popBackStack();
                }catch(NumberFormatException ex){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("Error");
                    builder.setMessage("Debes escribir los grados correctamente");
                    builder.setPositiveButton("Ok", (dialogInterface, i) -> {});
                    builder.create().show();
                }

            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Error");
                builder.setMessage("Debes llenar todos los campos");
                builder.setPositiveButton("Ok", (dialogInterface, i) -> {});
                builder.create().show();
            }
        });

        binding.btCancel.setOnClickListener(v->{
            NavHostFragment.findNavController(AddAlcoholFragment.this).popBackStack();
        });


        Historial.setCurrentFragment(this);
    }


    private void seleccionarImagen(){

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/");

        arLauncher.launch(i);
    }

    private String getShortImageUrl(){
        String url = "";

        try {
            binding.ivAlcohol.setImageURI(Uri.parse(alcohol.url));

            if (alcohol.url.toCharArray().length >20) {
                char[] array = alcohol.url.toCharArray();
                for (int j = 0; j < 20; j++) {
                    url+=array[j];
                }
                url+="...";
            }else{url = alcohol.url;}
        }catch (NullPointerException e){
            binding.ivAlcohol.setImageResource(R.drawable.notfound);
        }

        return url;
    }

    private boolean validaCampos(){
        if (binding.etFecha.getText().toString().trim().equals("") || binding.etNombre.getText().toString().trim().equals("") || binding.etGrados.getText().toString().trim().equals("")){

            return false;
        }
        if(binding.etGrados.getText().toString().contains(",")){
            binding.etGrados.setText(binding.etGrados.getText().toString().replace(',', '.'));
        }

        return true;
    }


}
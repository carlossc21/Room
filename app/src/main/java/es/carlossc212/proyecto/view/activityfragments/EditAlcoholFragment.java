package es.carlossc212.proyecto.view.activityfragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

import es.carlossc212.proyecto.R;
import es.carlossc212.proyecto.databinding.FragmentEditAlcoholBinding;
import es.carlossc212.proyecto.databinding.FragmentFirstBinding;
import es.carlossc212.proyecto.entity.Alcohol;
import es.carlossc212.proyecto.entity.Tipo;
import es.carlossc212.proyecto.objects.Historial;
import es.carlossc212.proyecto.viewmodel.AlcoholViewModel;
import es.carlossc212.proyecto.viewmodel.TipoViewModel;


public class EditAlcoholFragment extends Fragment {

    private Alcohol alcohol;
    private FragmentEditAlcoholBinding binding;
    private TipoViewModel tvm;
    private AlcoholViewModel avm;
    private ActivityResultLauncher<Intent> arLauncher;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        alcohol = NavHostFragment.findNavController(EditAlcoholFragment.this).getCurrentBackStackEntry().getArguments().getParcelable("alcohol");
        tvm = new TipoViewModel(getActivity().getApplication());
        avm = new AlcoholViewModel(getActivity().getApplication());

        arLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK){
                Uri url = result.getData().getData();
                binding.ivAlcohol.setImageURI(url);
                alcohol.url = url.toString();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditAlcoholBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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


        binding.ivAlcohol.setOnClickListener(v->{
            seleccionarImagen();
        });


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




        binding.etNombre.setText(alcohol.nombre);
        binding.etFecha.setText(alcohol.fecha);
        binding.etGrados.setText(String.valueOf(alcohol.grados));

        binding.lbUrl.setText(url);



        LiveData<List<Tipo>> lista = tvm.getAllTipos();
        lista.observe(getActivity(), tipos1 -> {
            try {
                ArrayAdapter<Tipo> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, tipos1);
                binding.spTipos.setAdapter(adapter);
            }catch (NullPointerException e){}


        });



        binding.btGuardar.setOnClickListener(v -> {
            if (validaCampos()) {

                alcohol.nombre = binding.etNombre.getText().toString();
                alcohol.idtipo = tvm.getTipo(binding.spTipos.getSelectedItem().toString()).id;
                alcohol.fecha = binding.etFecha.getText().toString();
                alcohol.grados = Float.valueOf(binding.etGrados.getText().toString());

                avm.updateAlcohol(alcohol);
                alcohol.edit();
                NavHostFragment.findNavController(EditAlcoholFragment.this).popBackStack();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Error");
                builder.setMessage("No puedes dejar campos vacíos");
                builder.setPositiveButton("Ok", (dialogInterface, i) -> {});
                builder.create().show();
            }
        });

        binding.btCancel.setOnClickListener(v->{
            NavHostFragment.findNavController(EditAlcoholFragment.this).popBackStack();
        });

        Historial.setCurrentFragment(this);

    }

    private void seleccionarImagen(){

        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/");

        arLauncher.launch(i);
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
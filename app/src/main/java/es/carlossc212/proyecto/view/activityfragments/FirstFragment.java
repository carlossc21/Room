package es.carlossc212.proyecto.view.activityfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.carlossc212.proyecto.R;
import es.carlossc212.proyecto.databinding.FragmentFirstBinding;
import es.carlossc212.proyecto.entity.Alcohol;
import es.carlossc212.proyecto.entity.Tipo;
import es.carlossc212.proyecto.objects.Historial;
import es.carlossc212.proyecto.view.adapter.AlcoholAdapter;
import es.carlossc212.proyecto.viewmodel.AlcoholViewModel;
import es.carlossc212.proyecto.viewmodel.TipoViewModel;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.rvItems.setLayoutManager(new LinearLayoutManager(getContext()));
        AlcoholViewModel avm = new ViewModelProvider(getActivity()).get(AlcoholViewModel.class);
        TipoViewModel tvm = new ViewModelProvider(getActivity()).get(TipoViewModel.class);


        AlcoholAdapter adapter = new AlcoholAdapter(getContext(), FirstFragment.this);
        binding.rvItems.setAdapter(adapter);





        LiveData<List<Alcohol>> listaAlcoholes = avm.getAllAlcoholes();
        listaAlcoholes.observe(getActivity(), alcoholes ->{
            adapter.setAlcoholList(alcoholes);
        } );

        binding.fabAdd.setOnClickListener(v->{
            NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_addAlcoholFragment);
        });



        Historial.setCurrentFragment(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
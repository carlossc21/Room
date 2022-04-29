package es.carlossc212.proyecto.view.activityfragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

import es.carlossc212.proyecto.R;
import es.carlossc212.proyecto.databinding.FragmentFirstBinding;
import es.carlossc212.proyecto.databinding.FragmentHistoryBinding;
import es.carlossc212.proyecto.objects.Historial;

public class HistoryFragment extends Fragment {
    private FragmentHistoryBinding binding;

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            binding.tvHistorial.setText(Historial.getHistorial());
        } catch (IOException e) { binding.tvHistorial.setText("No se ha podido cargar el historial"); }

        Historial.setCurrentFragment(this);
    }
}
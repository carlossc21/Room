package es.carlossc212.proyecto.view.adapter.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import es.carlossc212.proyecto.R;

import es.carlossc212.proyecto.entity.Alcohol;
import es.carlossc212.proyecto.repository.AlcoholRepository;
import es.carlossc212.proyecto.view.activityfragments.FirstFragment;

public class AlcoholViewHolder extends RecyclerView.ViewHolder{

    public Alcohol alcohol;
    public TextView tvFecha, tvUrl, tvTipo, tvNombre, tvGrados;
    public ImageView imagen;
    public Button btEdit, btDelete;


    public AlcoholViewHolder(@NonNull View itemView) {
        super(itemView);
        tvNombre = itemView.findViewById(R.id.tvNombre);
        tvTipo = itemView.findViewById(R.id.tvTipo);
        tvFecha = itemView.findViewById(R.id.tvFecha);
        tvUrl = itemView.findViewById(R.id.tvUrl);
        tvGrados = itemView.findViewById(R.id.tvGrados);
        imagen = itemView.findViewById(R.id.alcoholIMG);

        btEdit = itemView.findViewById(R.id.btEdit);
        btDelete = itemView.findViewById(R.id.btDelete);


    }
}

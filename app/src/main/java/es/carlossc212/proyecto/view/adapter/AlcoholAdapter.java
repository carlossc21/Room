package es.carlossc212.proyecto.view.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.carlossc212.proyecto.R;
import es.carlossc212.proyecto.entity.Alcohol;
import es.carlossc212.proyecto.repository.AlcoholRepository;
import es.carlossc212.proyecto.view.adapter.viewholder.AlcoholViewHolder;

public class AlcoholAdapter extends RecyclerView.Adapter<AlcoholViewHolder>{

    private List<Alcohol> alcoholList;
    private Context context;
    private AlcoholRepository rep;
    private Fragment firsFragment;

    public AlcoholAdapter(Context context, Fragment firstFragment){this.context = context; this.firsFragment = firstFragment;}

    @NonNull
    @Override
    public AlcoholViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);
        return new AlcoholViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlcoholViewHolder avh, int i) {
        rep = new AlcoholRepository(context.getApplicationContext());
        Alcohol alcohol = alcoholList.get(i);
        avh.alcohol = alcohol;
        avh.tvNombre.setText(alcohol.nombre);
        avh.tvGrados.setText(alcohol.grados+"º");

        String url = "";
        try {
            if (alcohol.url.toCharArray().length >20) {
                char[] array = alcohol.url.toCharArray();
                for (int j = 0; j < 20; j++) {
                    url+=array[j];
                }
                url+="...";
            }else{url = alcohol.url;}

            avh.imagen.setImageURI(Uri.parse(alcohol.url));
        }catch (NullPointerException ex){
            avh.imagen.setImageResource(R.drawable.notfound);
        }




        avh.tvTipo.setText(rep.getTipo(alcohol.idtipo).toString());

        avh.btEdit.setOnClickListener(view -> {
            Bundle datos = new Bundle();
            datos.putParcelable("alcohol", alcohol);
            NavHostFragment.findNavController(firsFragment).navigate(R.id.action_FirstFragment_to_editAlcoholFragment, datos);
        });
        avh.btDelete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("¿Seguro que quieres borrar este elemento?");
            builder.setPositiveButton("Borrar", (dialogInterface, i1) -> {
                avh.alcohol.delete();
                rep.deleteAlcohol(avh.alcohol);
                notifyDataSetChanged();
            });
            builder.setNegativeButton("Cancelar", (dialogInterface, i1) -> {});
            builder.create().show();
        });

        avh.tvFecha.setText(alcohol.fecha);
        avh.tvUrl.setText(url);

    }

    @Override
    public int getItemCount() {
        if (alcoholList == null) {
            return 0;
        }
        return alcoholList.size();
    }

    public void setAlcoholList(List<Alcohol> alcoholList){
        this.alcoholList = alcoholList;
        notifyDataSetChanged();
    }


}

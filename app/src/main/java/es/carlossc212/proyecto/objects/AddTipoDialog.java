package es.carlossc212.proyecto.objects;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import es.carlossc212.proyecto.R;
import es.carlossc212.proyecto.entity.Tipo;
import es.carlossc212.proyecto.repository.AlcoholRepository;

public class AddTipoDialog extends Dialog {

    private Button btAceptar, btCancelar;
    private EditText etNombre;



    public AddTipoDialog(@NonNull Context context, AlcoholRepository rep) {
        super(context);

        View v = LayoutInflater.from(context).inflate(R.layout.add_tipo_layout,null);

        btAceptar = v.findViewById(R.id.btAceptar);
        btAceptar.setOnClickListener(view -> {
            if (!etNombre.getText().toString().trim().equals("")) {

                    Tipo t = new Tipo();
                    t.nombre = etNombre.getText().toString();
                    rep.insertTipo(t);
                    this.hide();

            }else{this.cancel();}


        });
        btCancelar = v.findViewById(R.id.btCancelar);
        btCancelar.setOnClickListener(view -> {

            this.cancel();
        });
        etNombre = v.findViewById(R.id.nombreTipo);
        this.setContentView(v);

        this.setCancelable(true);


        create();
        show();


    }



}

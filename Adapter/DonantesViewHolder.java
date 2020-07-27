package com.fede.nextu.controldedonantes.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fede.nextu.controldedonantes.Modelo.Donante;
import com.fede.nextu.controldedonantes.R;

/**
 * Created by JHOAN on 17/09/2017.
 */

public class DonantesViewHolder extends RecyclerView.ViewHolder {
    TextView txt_datos;
    ImageButton btn_eliminar,btn_editar;

    public DonantesViewHolder(View itemView) {
        super(itemView);

        txt_datos = (TextView) itemView.findViewById(R.id.datos_card);
        btn_eliminar= (ImageButton) itemView.findViewById(R.id.card_eliminar);
        btn_editar= (ImageButton) itemView.findViewById(R.id.card_editar);
    }

    public void bindDonante(Donante donante){

        txt_datos.setText(donante.getNombre() + " " + donante.getApellido() + "                 " + String.valueOf(donante.getId())
                + "\n" + "Edad: " + String.valueOf(donante.getEdad()) + "          Peso: " + String.valueOf(donante.getPeso())
                + "       Estatura: " + String.valueOf(donante.getEstatura()) + "\nTipo de sangre: " + donante.getSangre()
                + "           Rh: " + donante.getRh());

    }
}





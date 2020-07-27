package com.fede.nextu.controldedonantes.Dialogos;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fede.nextu.controldedonantes.Modelo.Donante;
import com.fede.nextu.controldedonantes.R;

/**
 * Created by JHOAN on 19/09/2017.
 */

public class DialogoEditarDonante extends DialogFragment {
    public static final String TAG = "dialogo_editar_donante";

    public EditText editIdentificacion;
    public EditText editNombre;
    public EditText editApellido;
    public EditText editEdad;
    public EditText editPeso;
    public EditText editEstatura;
    public Spinner spinner_sangre,spinner_rh;
    View view;
    public Button btnCancelar;
    public Button btnActualizar;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.dialogo_nuevo_donante, null);
        builder.setTitle("Editar Donante").setView(view);

        editIdentificacion = (EditText) view.findViewById(R.id.identificacion);
        editNombre = (EditText) view.findViewById(R.id.nombre);
        editApellido = (EditText) view.findViewById(R.id.apellido);
        editEdad = (EditText) view.findViewById(R.id.edad);
        editPeso = (EditText) view.findViewById(R.id.peso);
        editEstatura = (EditText) view.findViewById(R.id.estatura);
        spinner_sangre = (Spinner) view.findViewById(R.id.spinner_izq);
        spinner_rh = (Spinner) view.findViewById(R.id.spinner_der);

        btnActualizar= (Button) view.findViewById(R.id.btn_registrar);
        btnCancelar= (Button) view.findViewById(R.id.btn_cancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return builder.create();
    }

}

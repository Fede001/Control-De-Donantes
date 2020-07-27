package com.fede.nextu.controldedonantes.Dialogos;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fede.nextu.controldedonantes.Modelo.Donante;
import com.fede.nextu.controldedonantes.R;

/**
 * Created by JHOAN on 16/09/2017.
 */

public class DialogoNuevoDonante extends DialogFragment {

    public static final String TAG = "dialogo_nuevo_donante";

    public interface OnNuevoDonanteListener {
        void nuevoDonanteListener(Donante donante);
    }
    OnNuevoDonanteListener listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogo_nuevo_donante, null);
        builder.setTitle("Nuevo Donante").setView(view);

        final EditText editIdentificacion = (EditText) view.findViewById(R.id.identificacion);
        final EditText editNombre = (EditText) view.findViewById(R.id.nombre);
        final EditText editApellido = (EditText) view.findViewById(R.id.apellido);
        final EditText editEdad = (EditText) view.findViewById(R.id.edad);
        final EditText editPeso = (EditText) view.findViewById(R.id.peso);
        final EditText editEstatura = (EditText) view.findViewById(R.id.estatura);

        final Spinner spinner_sangre= (Spinner) view.findViewById(R.id.spinner_izq);
        final Spinner spinner_Rh= (Spinner) view.findViewById(R.id.spinner_der);
        Button btnCancelar = (Button) view.findViewById(R.id.btn_cancelar);
        Button btnRegistrar = (Button) view.findViewById(R.id.btn_registrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editIdentificacion.getText().toString().equals("") && !editNombre.getText().toString().equals("") &&
                        !editApellido.getText().toString().equals("") && !editEdad.getText().toString().equals("") &&
                        !editPeso.getText().toString().equals("") && !editEstatura.getText().toString().equals("") &&
                        !spinner_Rh.getSelectedItem().equals("") && !spinner_sangre.getSelectedItem().equals("")){

                    Donante donante=new Donante(Integer.parseInt(editIdentificacion.getText().toString()),
                            editNombre.getText().toString(), editApellido.getText().toString(), Integer.parseInt(editEdad.getText()
                            .toString()), Integer.parseInt(editPeso.getText().toString()),
                            Double.parseDouble(editEstatura.getText().toString()),spinner_sangre.getSelectedItem().toString(),
                            spinner_Rh.getSelectedItem().toString());

                    listener.nuevoDonanteListener(donante);
                    dismiss();

                }else {
                    Toast.makeText(getActivity(), "Rellene todos los campos...", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (OnNuevoDonanteListener) getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " no se implementó la interfaz OnNuevoDonanteListener");
        }
    }
}

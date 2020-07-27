package com.fede.nextu.controldedonantes.Dialogos;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fede.nextu.controldedonantes.R;

/**
 * Created by JHOAN on 20/09/2017.
 */

public class DialogoCambioContraseña extends DialogFragment {

    public static final String TAG = "dialogo_cambio_contraseña";

    public interface OnCambioContListener {
       void cambiarContraseña(String viejaCont, String nuevaCont);
    }
    OnCambioContListener listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogo_cambiar_contrasena, null);
        builder.setTitle("Cambiar Contraseña").setView(view);

        final EditText editActualCont = (EditText) view.findViewById(R.id.cont_actual);
        final EditText editNuevaCont = (EditText) view.findViewById(R.id.cont_nueva);
        final EditText editConfCont = (EditText) view.findViewById(R.id.cont_confir);

        Button btnCancelar = (Button) view.findViewById(R.id.btn_cancelar);
        Button btnRegistrar = (Button) view.findViewById(R.id.btn_registrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editActualCont.getText().toString().equals("") && !editNuevaCont.getText().toString().equals("") &&
                        !editConfCont.getText().toString().equals("")){
                    if(editNuevaCont.getText().toString().equals(editConfCont.getText().toString())){

                        listener.cambiarContraseña(editActualCont.getText().toString(),editNuevaCont.getText().toString());
                        dismiss();

                    }else{
                        Toast.makeText(getActivity(), "Las contraseñas deben coincidir", Toast.LENGTH_SHORT).show();
                    }
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
            listener = (OnCambioContListener) getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " no se implementó la interfaz OnCambioContListener");
        }
    }
}

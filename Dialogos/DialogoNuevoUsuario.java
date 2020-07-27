package com.fede.nextu.controldedonantes.Dialogos;

import android.app.Dialog;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fede.nextu.controldedonantes.MainActivity;
import com.fede.nextu.controldedonantes.R;

/**
 * Created by JHOAN on 15/09/2017.
 */

public class DialogoNuevoUsuario extends DialogFragment {

    public static final String TAG = "dialogo_nuevo_usuario";

   public interface OnRegistrarListener {
        void RegistrarListener(String usuario, String contraseña);
    }
    OnRegistrarListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogo_nuevo_usuario,null);
        builder.setTitle("Nuevo Usuario").setView(view);

        final EditText editUsuario = (EditText) view.findViewById(R.id.usu_dialogo);
        final EditText editContraseña = (EditText) view.findViewById(R.id.cont_dialogo);
        final EditText editConfContraseña = (EditText) view.findViewById(R.id.cont2_dialogo);
        Button btnCancelar = (Button) view.findViewById(R.id.btn_cancelar);
        Button btnRegistrar = (Button) view.findViewById(R.id.btn_registrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editUsuario.getText().toString().equals("") && !editContraseña.getText().toString().equals("")
                        && !editConfContraseña.getText().toString().equals("")){
                    if(editContraseña.getText().toString().equals(editConfContraseña.getText().toString())){

                        listener.RegistrarListener(editUsuario.getText().toString(),editContraseña.getText().toString());
                        dismiss();

                    }else{
                        Toast.makeText(getActivity(), "Las contraseñas deben coincidir", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getActivity(), "Rellene todos los campos", Toast.LENGTH_SHORT).show();
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
            listener = (OnRegistrarListener) getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " no se implementó la interfaz OnRegistrarListener");
        }
    }

}

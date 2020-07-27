package com.fede.nextu.controldedonantes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.fede.nextu.controldedonantes.Dialogos.DialogoNuevoUsuario;

public class MainActivity extends AppCompatActivity implements DialogoNuevoUsuario.OnRegistrarListener{

    private TextInputLayout inputEditUsu, inputEditCont;
    SQLiteDatabase DataBase;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputEditUsu= (TextInputLayout) findViewById(R.id.input_usu);
        inputEditCont= (TextInputLayout) findViewById(R.id.input_cont);

        preferences = getSharedPreferences(Constantes.PREFERENCE_SESION, MODE_PRIVATE);
        DataBase = openOrCreateDatabase("Database", MODE_APPEND, null);
        //DataBase.execSQL("DROP TABLE IF EXISTS USUARIOS;");
        DataBase.execSQL(Constantes.CREAR_TABLA_USUARIOS);

    }


    private void guardarPreferencia(String nombre){
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(Constantes.PREF_LLAVE_USU, nombre);
        edit.apply();
    }


    private void readCursor(Cursor cursor) {
        int a=0;
        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndex("NOMBRE"));
                String contraseña = cursor.getString(cursor.getColumnIndex("CONTRASENA"));
                if(inputEditUsu.getEditText().getText().toString().equals(nombre) &&
                        inputEditCont.getEditText().getText().toString().equals(contraseña)){

                    guardarPreferencia(nombre);
                    Toast.makeText(this, "bienvenido "+nombre, Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(this,MainActivity2.class);
                    startActivity(intent);
                }else{
                    a++;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        if(a==1) {
            Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_LONG).show();
        }
    }

///////////////////////////para los dos botones iniciar sesion y nuevo usuario.
    public void onIniciarSesion (View view){
        if(!inputEditUsu.getEditText().getText().toString().equals("") &&
                !inputEditCont.getEditText().getText().toString().equals("")){

            String texto = inputEditUsu.getEditText().getText().toString();
            Cursor cursor = DataBase.rawQuery("SELECT * FROM USUARIOS WHERE NOMBRE LIKE ?", new String[]{texto});
            readCursor(cursor);
        }else {
            Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
        }
    }


    public void onNuevoUsuario (View view){
        DialogoNuevoUsuario dialgUsuario = new DialogoNuevoUsuario();
        dialgUsuario.show(getSupportFragmentManager(),DialogoNuevoUsuario.TAG);
    }


    @Override
    public void RegistrarListener(String usuario,String contraseña) {
        int a=0;
        Cursor cursor = DataBase.rawQuery(Constantes.MOSTRAR_TABLA_USUARIOS,null);
        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndex("NOMBRE"));
                if (usuario.equals(nombre)) {
                    a++;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        if(a==1) {
            Toast.makeText(this, "El usuario " + usuario + " ya existe.....", Toast.LENGTH_LONG).show();
        }else{
            DataBase.execSQL(Constantes.INSERTAR_TABLA_USUARIOS, new Object[]{usuario, contraseña});
            Toast.makeText(this, "Usuario "+usuario+" registrado", Toast.LENGTH_SHORT).show();
        }
    }



}


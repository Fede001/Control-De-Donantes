package com.fede.nextu.controldedonantes;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fede.nextu.controldedonantes.Adapter.DonantesAdapter;
import com.fede.nextu.controldedonantes.Dialogos.DialogoCambioContraseña;
import com.fede.nextu.controldedonantes.Dialogos.DialogoEditarDonante;
import com.fede.nextu.controldedonantes.Dialogos.DialogoNuevoDonante;
import com.fede.nextu.controldedonantes.Modelo.Donante;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity implements DialogoNuevoDonante.OnNuevoDonanteListener,
        DonantesAdapter.OnEventoBotones, DialogoCambioContraseña.OnCambioContListener{

    SharedPreferences preferences;
    SQLiteDatabase DataBase;
    RecyclerView recyclerView;
    DonantesAdapter adapter;
    ArrayList<Donante> donantes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferences = getSharedPreferences(Constantes.PREFERENCE_SESION, MODE_PRIVATE);
        DataBase = openOrCreateDatabase("Database", MODE_APPEND, null);
        //DataBase.execSQL("DROP TABLE IF EXISTS DONANTES;");
        DataBase.execSQL(Constantes.CREAR_TABLA_DONANTES);
        donantes= new ArrayList<>();


        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        adapter = new DonantesAdapter(donantes,this);
        adapter.setOnEventoBotones(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        actualizarVista();

        final TextInputLayout editBusqueda=(TextInputLayout) findViewById(R.id.input_buscar);
        ImageButton btn_cancelar=(ImageButton)findViewById(R.id.image_cancelar);
        ImageButton btn_buscar=(ImageButton)findViewById(R.id.image_buscar);
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editBusqueda.getEditText().setText("");
                actualizarVista();
            }
        });
        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String busqueda = editBusqueda.getEditText().getText().toString();
                if(!busqueda.equals("")){
                    recorridoBusqueda(busqueda);
                }else {
                    actualizarVista();
                }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogoNuevoDonante dialgDonante = new DialogoNuevoDonante();
                dialgDonante.show(getSupportFragmentManager(),DialogoNuevoDonante.TAG);
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.cambiar_contraseña) {
            DialogoCambioContraseña dialogoCambioContraseña = new DialogoCambioContraseña();
            dialogoCambioContraseña.show(getSupportFragmentManager(),DialogoCambioContraseña.TAG);

        }else if (id == R.id.eliminar_cuenta) {
            eliminarUsuario();

        }else if(id == R.id.cerrar_sesion) {
            super.onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    private void actualizarVista() {
        Cursor cursor = DataBase.rawQuery(Constantes.MOSTRAR_TABLA_DONANTES, null);
        donantes.clear();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("ID"));
                String nombre = cursor.getString(cursor.getColumnIndex("NOMBRES"));
                String apellido = cursor.getString(cursor.getColumnIndex("APELLIDOS"));
                int edad = cursor.getInt(cursor.getColumnIndex("EDAD"));
                int peso = cursor.getInt(cursor.getColumnIndex("PESO"));
                double estatura = cursor.getDouble(cursor.getColumnIndex("ESTATURA"));
                String sangre = cursor.getString(cursor.getColumnIndex("SANGRE"));
                String Rh = cursor.getString(cursor.getColumnIndex("RH"));

                donantes.add(new Donante(id,nombre,apellido,edad,peso,estatura,sangre,Rh));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    private void eliminarUsuario() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Desea eliminar la cuenta?");
        builder.setPositiveButton("Sí", null);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    String username = preferences.getString(Constantes.PREF_LLAVE_USU, null);
                    DataBase.execSQL("DELETE FROM USUARIOS WHERE NOMBRE=?;",new Object[]{username});
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.remove(Constantes.PREF_LLAVE_USU);
                    edit.apply();
                    MainActivity2.super.onBackPressed();
            }
        });
    }


    @Override
    public void nuevoDonanteListener(Donante donante) {
        int a=0;
        Cursor cursor = DataBase.rawQuery(Constantes.MOSTRAR_TABLA_DONANTES,null);
        if (cursor.moveToFirst()) {
            do {
                int identificacion = cursor.getInt(cursor.getColumnIndex("ID"));
                if (identificacion==donante.getId()) {
                    a++;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        if(a==1){
            Toast.makeText(this, "El numero de identificacion " +donante.getId() + " ya existe.....", Toast.LENGTH_LONG)
                    .show();
        }else{
            DataBase.execSQL(Constantes.INSERTAR_TABLA_DONANTES, new Object[]{donante.getId(),donante.getNombre(),
                    donante.getApellido(),donante.getEdad(),donante.getPeso(),donante.getEstatura(),donante.getSangre(),
                    donante.getRh()});

            Toast.makeText(this, "Donante con identificacion "+donante.getId()+" registrado", Toast.LENGTH_LONG).show();
            actualizarVista();
        }
    }


    @Override
    public void eliminarDonante(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Desea eliminar el este donante?");
        builder.setPositiveButton("Sí", null);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Donante don=donantes.get(position);
                DataBase.execSQL("DELETE FROM DONANTES WHERE ID=?;",new Object[]{don.getId()});
                Toast.makeText(MainActivity2.this, "Donante "+don.getId()+" eliminado", Toast.LENGTH_LONG).show();
                actualizarVista();
                alertDialog.dismiss();
            }
        });
    }


    @Override
    public void editarDonante(int position) {

        final DialogoEditarDonante dialogo = new DialogoEditarDonante();
        dialogo.show(getSupportFragmentManager(), DialogoEditarDonante.TAG );
        getSupportFragmentManager().executePendingTransactions();

        Donante don=donantes.get(position);
        dialogo.editIdentificacion.setText(String.valueOf(don.getId()));
        dialogo.editIdentificacion.setEnabled(false);
        dialogo.editIdentificacion.setFocusable(false);
        dialogo.editNombre.setText(don.getNombre());
        dialogo.editApellido.setText(don.getApellido());
        dialogo.editEdad.setText(String.valueOf(don.getEdad()));
        dialogo.editPeso.setText(String.valueOf(don.getPeso()));
        dialogo.editEstatura.setText(String.valueOf(don.getEstatura()));

        if(don.getSangre().equals("A")){
            dialogo.spinner_sangre.setSelection(1);
        }else if(don.getSangre().equals("B")){
            dialogo.spinner_sangre.setSelection(2);
        }else if(don.getSangre().equals("AB")){
            dialogo.spinner_sangre.setSelection(3);
        }else{
            dialogo.spinner_sangre.setSelection(4);
        }

        if(don.getRh().equals("positivo")){
            dialogo.spinner_rh.setSelection(1);
        }else{
            dialogo.spinner_rh.setSelection(2);
        }
        dialogo.btnActualizar.setText("Actualizar");
        dialogo.btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(dialogo.editIdentificacion.getText().toString());
                String nombre = dialogo.editNombre.getText().toString();
                String apellido = dialogo.editApellido.getText().toString();
                int edad = Integer.parseInt(dialogo.editEdad.getText().toString());
                int peso = Integer.parseInt(dialogo.editPeso.getText().toString());
                double estatura = Double.parseDouble(dialogo.editEstatura.getText().toString());
                String sangre = dialogo.spinner_sangre.getSelectedItem().toString();
                String rh = dialogo.spinner_rh.getSelectedItem().toString();

                ingresoDatos(nombre,apellido,edad,peso,estatura,sangre,rh,id);
                dialogo.dismiss();
            }
        });
    }

    public void ingresoDatos(String nombre, String apellido, int edad, int peso, double estatura, String sangre, String rh,int id) {

        DataBase.execSQL("UPDATE DONANTES SET NOMBRES=?,APELLIDOS=?,EDAD=?,PESO=?,ESTATURA=?,SANGRE=?,RH=? WHERE ID=?",
                new Object[]{nombre,apellido,edad,peso,estatura,sangre,rh,id});
        Toast.makeText(this, "Donante con identificacion "+id+" actualizado", Toast.LENGTH_LONG).show();
        actualizarVista();
    }



    @Override
    public void cambiarContraseña(String viejaCont, String nuevaCont) {
        String nombre="";
        Cursor cursor = DataBase.rawQuery("SELECT * FROM USUARIOS WHERE CONTRASENA LIKE ?", new String[]{viejaCont});
        int a=0;
        if (cursor.moveToFirst()) {
            do {
                nombre = cursor.getString(cursor.getColumnIndex("NOMBRE"));
                String contraseña = cursor.getString(cursor.getColumnIndex("CONTRASENA"));
                if (!viejaCont.equals(contraseña)) {
                    a++;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        if(a==1) {
            Toast.makeText(this, "La contraseña no coincide con ningun usuario", Toast.LENGTH_LONG).show();
        }else{
            DataBase.execSQL("UPDATE USUARIOS SET CONTRASENA=? WHERE NOMBRE=?", new Object[]{nuevaCont, nombre});
            Toast.makeText(this, "Usuario "+nombre+" ha cambiado su congtraseña", Toast.LENGTH_SHORT).show();
        }
    }

    private void recorridoBusqueda(String busqueda) {

        Cursor cursor = DataBase.rawQuery("SELECT * FROM DONANTES WHERE ID LIKE ?;", new String[]{"%"+busqueda+"%"});
        donantes.clear();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("ID"));
                String nombre = cursor.getString(cursor.getColumnIndex("NOMBRES"));
                String apellido = cursor.getString(cursor.getColumnIndex("APELLIDOS"));
                int edad = cursor.getInt(cursor.getColumnIndex("EDAD"));
                int peso = cursor.getInt(cursor.getColumnIndex("PESO"));
                double estatura = cursor.getDouble(cursor.getColumnIndex("ESTATURA"));
                String sangre = cursor.getString(cursor.getColumnIndex("SANGRE"));
                String Rh = cursor.getString(cursor.getColumnIndex("RH"));

                donantes.add(new Donante(id,nombre,apellido,edad,peso,estatura,sangre,Rh));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

}

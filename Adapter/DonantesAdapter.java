package com.fede.nextu.controldedonantes.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fede.nextu.controldedonantes.Dialogos.DialogoEditarDonante;
import com.fede.nextu.controldedonantes.Modelo.Donante;
import com.fede.nextu.controldedonantes.R;

import java.util.ArrayList;

/**
 * Created by JHOAN on 17/09/2017.
 */

public class DonantesAdapter extends RecyclerView.Adapter<DonantesViewHolder> {

    public interface OnEventoBotones{
        void editarDonante(int position);
        void eliminarDonante (int position);
    }
    OnEventoBotones listener;


    private ArrayList<Donante> donantes;
    Activity activity;

    public DonantesAdapter(ArrayList<Donante> donante, Activity activity){
        this.activity = activity;
        this.donantes = donante;
    }

    @Override
    public DonantesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=activity.getLayoutInflater().inflate(R.layout.item_cardview,parent,false);
        DonantesViewHolder view1= new DonantesViewHolder(v);
        return view1;

    }

    @Override
    public void onBindViewHolder(final DonantesViewHolder holder, final int position) {
        holder.bindDonante(donantes.get(position));


        holder.btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.eliminarDonante(position);

            }
        });

        holder.btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               listener.editarDonante(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return donantes.size();
    }
    public ArrayList<Donante> getDonantes(){
        return donantes;
    }

    public void setOnEventoBotones(
            OnEventoBotones onEventoBotones) {
        this.listener = onEventoBotones;
    }

}

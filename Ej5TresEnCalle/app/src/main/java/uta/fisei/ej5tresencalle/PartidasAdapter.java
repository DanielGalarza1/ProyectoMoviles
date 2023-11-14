package uta.fisei.ej5tresencalle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PartidasAdapter extends RecyclerView.Adapter<PartidasAdapter.ViewHolder>{

    private ArrayList<Partida> dataset;

    public PartidasAdapter() {
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_partida, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PartidasAdapter.ViewHolder holder, int position) {
        Partida partida = dataset.get(position);

        if (partida.getQuienGano() == 1) {
            holder.tituloTextView.setText(partida.getNombreJugador1());
        } else {
            holder.tituloTextView.setText(partida.getNombreJugador2());
        }

        holder.subtituloTextView.setText(darFormato(partida.getFecha()));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static String darFormato(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMMyyyy hh:mm");
        return simpleDateFormat.format(date).toUpperCase();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tituloTextView;
        TextView subtituloTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            tituloTextView = (TextView) itemView.findViewById(R.id.tituloTextView);
            subtituloTextView = (TextView) itemView.findViewById(R.id.subtituloTextView);
        }
    }

    public void add(Partida partida) {
        dataset.add(0, partida); // Agrega la nueva partida en la parte superior de la lista
        notifyItemInserted(0); // Notifica al adaptador que se ha insertado un nuevo elemento en la posición 0
    }

    public void clear() {
        dataset.clear();
        notifyDataSetChanged();
    }
}

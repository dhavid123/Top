package com.damg.top;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArtistaAdapter extends RecyclerView.Adapter<ArtistaAdapter.ViewHolder> {

    private List<Artista> artistas;
    private Context context;
    private OnItemClickListener listener;


    public ArtistaAdapter(List<Artista> artistas, OnItemClickListener listener) {
        this.artistas = artistas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist, parent, false);
        this.context = parent.getContext();
        return new ViewHolder(view);
    }


    //Se vincula la vista de cada elemento.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Artista artista = artistas.get(position);
        holder.setListener(artista, listener);

        holder.tvNombre.setText(artista.getNombreCompleto());
        holder.tvOrden.setText(String.valueOf(artista.getOrden()));

        if (artista.getFotoUrl() != null) {
            RequestOptions options =  new RequestOptions();
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
            options.centerCrop();
            options.placeholder(R.drawable.ic_sentiment_satisfied);

            Glide.with(context).load(artista.getFotoUrl())
                    .into(holder.imgFoto);

        } else {
            holder.imgFoto.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_account_box));

        }


    }

    @Override
    public int getItemCount() {
        return this.artistas.size();
    }

    public void addArtista(Artista artista) {

        if (!artistas.contains(artista)) {
            artistas.add(artista);
            notifyDataSetChanged();
        }


    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgFoto)
        AppCompatImageView imgFoto;
        @BindView(R.id.tvNombre)
        AppCompatTextView tvNombre;
        @BindView(R.id.tvOrden)
        AppCompatTextView tvOrden;
        @BindView(R.id.containerMain)
        RelativeLayout containerMain;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        //metodo auxiliar para añadir un listener a nuestro ViweHolder y de esa forma poder enviar el elemento seleccionado a la actividad.
        void setListener(final Artista artista, final OnItemClickListener listener) {

            containerMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(artista);
                }
            });

            containerMain.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.OnLongItemClick(artista);
                    return true;
                }
            });

        }


    }

}

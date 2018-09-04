package com.example.sinon.bursakerjakhusussmk.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.sinon.bursakerjakhusussmk.Interface.ItemClickListener;
import com.example.sinon.bursakerjakhusussmk.R;

public class PerusahaanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView GambarPerusahaan;
    public TextView NamaPerusahaan;

    public ItemClickListener itemClickListener;

    public PerusahaanViewHolder(View itemView) {
        super(itemView);

        GambarPerusahaan = itemView.findViewById(R.id.gambarPerusahaan);
        NamaPerusahaan = itemView.findViewById(R.id.namaPerusahaan);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.OnClick(v,getAdapterPosition(),false);
    }
}

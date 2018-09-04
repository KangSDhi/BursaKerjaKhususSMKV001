package com.example.sinon.bursakerjakhusussmk.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.sinon.bursakerjakhusussmk.Interface.ItemClickListener;
import com.example.sinon.bursakerjakhusussmk.R;

public class RiwayatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView NamaLowongan, NamaPerusahaan, TxtStatus;

    public ItemClickListener itemClickListener;

    public RiwayatViewHolder(View itemView) {

        super(itemView);

        NamaLowongan = itemView.findViewById(R.id.namaLowongan);
        NamaPerusahaan = itemView.findViewById(R.id.namaPerusahaan);
        TxtStatus = itemView.findViewById(R.id.txtStatus);

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

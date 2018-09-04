package com.example.sinon.bursakerjakhusussmk.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.example.sinon.bursakerjakhusussmk.Interface.ItemClickListener;
import com.example.sinon.bursakerjakhusussmk.R;

public class LowonganViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView LowNama, LowPerusahaan, LowBerlaku;

    public ItemClickListener itemClickListener;

    public LowonganViewHolder(View itemView) {
        super(itemView);

        LowNama = (TextView)itemView.findViewById(R.id.namaLowongan);
        LowPerusahaan = (TextView)itemView.findViewById(R.id.namaPerusahaan);
        LowBerlaku = (TextView)itemView.findViewById(R.id.txtBatasWaktu);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.OnClick(v, getAdapterPosition(), false);
    }
}
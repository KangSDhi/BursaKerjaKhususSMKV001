package com.example.sinon.bursakerjakhusussmk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.example.sinon.bursakerjakhusussmk.Interface.ItemClickListener;
import com.example.sinon.bursakerjakhusussmk.Model.Lowongan;
import com.example.sinon.bursakerjakhusussmk.ViewHolder.LowonganViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DaftarLowongan extends AppCompatActivity {

    DatabaseReference RefrensiLowongan = FirebaseDatabase.getInstance().getReference("Lowongan");

    RecyclerView recyclerViewLowongan;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Lowongan, LowonganViewHolder> adapter;

    String lowonganId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_lowongan);

        recyclerViewLowongan = (RecyclerView)findViewById(R.id.recyclerDaftarLowongan);
        recyclerViewLowongan.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewLowongan.setLayoutManager(layoutManager);

        //get Intent
        if (getIntent() != null)
        {
            lowonganId = getIntent().getStringExtra("LowonganId");
        }
        if (!lowonganId.isEmpty() && lowonganId != null)
        {
            memuatDaftarLowongan(lowonganId);
        }
    }

    private void memuatDaftarLowongan(String lowonganId) {
        adapter = new FirebaseRecyclerAdapter<Lowongan, LowonganViewHolder>(
                Lowongan.class,
                R.layout.item_lowongan,
                LowonganViewHolder.class,
                RefrensiLowongan.orderByChild("perusahaanId").equalTo(lowonganId)
        ) {
            @Override
            protected void populateViewHolder(LowonganViewHolder viewHolder, final Lowongan model, int position) {
                viewHolder.LowNama.setText(model.getNama());
                viewHolder.LowPerusahaan.setText(model.getPerusahaan());
                viewHolder.LowBerlaku.setText(model.getBerlaku());

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean isLongClick) {
                        Intent lowonganDetail = new Intent(DaftarLowongan.this, LowonganDetail.class);
                        lowonganDetail.putExtra("LowonganId", adapter.getRef(position).getKey());
                        startActivity(lowonganDetail);
                        Toast.makeText(DaftarLowongan.this, ""+model.getNama(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        recyclerViewLowongan.setAdapter(adapter);
    }
}

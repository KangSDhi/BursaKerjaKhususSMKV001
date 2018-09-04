package com.example.sinon.bursakerjakhusussmk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.example.sinon.bursakerjakhusussmk.Interface.ItemClickListener;
import com.example.sinon.bursakerjakhusussmk.Model.Perusahaan;
import com.example.sinon.bursakerjakhusussmk.ViewHolder.PerusahaanViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DaftarPerusahaan extends AppCompatActivity {

    DatabaseReference RefrensiPerusahaan = FirebaseDatabase.getInstance().getReference("Perusahaan");
    RecyclerView recyclerViewPerusahaan;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Perusahaan, PerusahaanViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perusahaan);

        recyclerViewPerusahaan = (RecyclerView)findViewById(R.id.recyclerPerusahaan);
        recyclerViewPerusahaan.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewPerusahaan.setLayoutManager(layoutManager);

        memuatPerusahaan();
    }

    private void memuatPerusahaan() {
        adapter = new FirebaseRecyclerAdapter<Perusahaan, PerusahaanViewHolder>(
                Perusahaan.class,
                R.layout.item_perusahaan,
                PerusahaanViewHolder.class,
                RefrensiPerusahaan
        ) {
            @Override
            protected void populateViewHolder(PerusahaanViewHolder viewHolder, final Perusahaan model, int position) {
                Picasso.with(getBaseContext()).load(model.getGambar()).into(viewHolder.GambarPerusahaan);
                viewHolder.NamaPerusahaan.setText(model.getNama());

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean isLongClick) {
                        Intent daftarLowongan = new Intent(DaftarPerusahaan.this, DaftarLowongan.class);
                        daftarLowongan.putExtra("LowonganId", adapter.getRef(position).getKey());
                        startActivity(daftarLowongan);
                        Toast.makeText(DaftarPerusahaan.this, ""+model.getNama(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        recyclerViewPerusahaan.setAdapter(adapter);
    }
}

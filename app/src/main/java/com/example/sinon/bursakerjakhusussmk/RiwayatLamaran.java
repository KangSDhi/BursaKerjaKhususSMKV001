package com.example.sinon.bursakerjakhusussmk;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sinon.bursakerjakhusussmk.Interface.ItemClickListener;
import com.example.sinon.bursakerjakhusussmk.Model.RiwayatLowongan;
import com.example.sinon.bursakerjakhusussmk.ViewHolder.RiwayatViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RiwayatLamaran extends AppCompatActivity {

    RecyclerView recyclerViewRiwayat;
    RecyclerView.LayoutManager layoutManager;

    DatabaseReference RefrensiRiwayat = FirebaseDatabase.getInstance().getReference("PermintaanLowongan");
    FirebaseRecyclerAdapter<RiwayatLowongan,RiwayatViewHolder> adapter;

    String riwayatId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_lamaran);

        recyclerViewRiwayat = (RecyclerView)findViewById(R.id.recyclerRiwayat);
        recyclerViewRiwayat.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewRiwayat.setLayoutManager(layoutManager);

        //Get Intent Here
        if (getIntent() != null)
        {
            riwayatId = getIntent().getStringExtra("RiwayatId");

        }
        if (!riwayatId.isEmpty() && riwayatId != null)
        {
            memuatRiwayat(riwayatId);
        }

    }

    private void memuatRiwayat(String riwayatId) {
        adapter = new FirebaseRecyclerAdapter<RiwayatLowongan, RiwayatViewHolder>(
                    RiwayatLowongan.class,
                    R.layout.item_riwayat,
                    RiwayatViewHolder.class,
                    RefrensiRiwayat.orderByChild("nama").equalTo(riwayatId)
            ) {
                @Override
                protected void populateViewHolder(RiwayatViewHolder viewHolder, final RiwayatLowongan model, int position) {
                    viewHolder.NamaLowongan.setText(model.getNama_lowongan());
                    viewHolder.NamaPerusahaan.setText(model.getPerusahaan());
                    viewHolder.TxtStatus.setText(model.getStatus());
                    viewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void OnClick(View view, int position, boolean isLongClick) {
                            alertDialog(model.getPesan());
                            //Toast.makeText(RiwayatLamaran.this, "test", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            };


        recyclerViewRiwayat.setAdapter(adapter);
    }

    private void alertDialog(String pesan) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RiwayatLamaran.this);
        alertDialog.setTitle("Pesan");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_item_pesan = inflater.inflate(R.layout.item_pesan, null);
        TextView TextPesan = add_item_pesan.findViewById(R.id.txtPesan);
        TextPesan.setText(pesan);
        alertDialog.setView(add_item_pesan);
        alertDialog.show();
    }
}

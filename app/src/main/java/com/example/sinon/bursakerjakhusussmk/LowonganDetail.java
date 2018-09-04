package com.example.sinon.bursakerjakhusussmk;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.sinon.bursakerjakhusussmk.Common.Common;
import com.example.sinon.bursakerjakhusussmk.Model.Lowongan;
import com.example.sinon.bursakerjakhusussmk.Model.PendftaranLowongan;
import com.google.firebase.database.*;
import com.squareup.picasso.Picasso;

public class LowonganDetail extends AppCompatActivity {

    String lowonganId = "";
    Lowongan LowonganBerlaku;

    TextView NamaLowongan, WaktuLowonganBerlaku;
    ImageView LogoPerusahaan;
    EditText EdPersyaratan;

    Button TombolDaftarLowongan;

    CollapsingToolbarLayout collapsingToolbarLayout;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference RefrensiLowongan = database.getReference("Lowongan");
    DatabaseReference RefrensiPendaftaranLowongan = database.getReference("PermintaanLowongan");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lowongan_detail);

        inisialisasiKomponen();
        aksiKomponen();
    }

    private void aksiKomponen() {

        if (getIntent() != null)
        {
            lowonganId = getIntent().getStringExtra("LowonganId");
        }
        if (!lowonganId.isEmpty())
        {
            dapatkanDetailLowongan(lowonganId);
        }

        //collapsing
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
    }

    private void dapatkanDetailLowongan(String lowonganId) {
        RefrensiLowongan.child(lowonganId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LowonganBerlaku = dataSnapshot.getValue(Lowongan.class);
                collapsingToolbarLayout.setTitle(LowonganBerlaku.getPerusahaan());
                Picasso.with(getBaseContext()).load(LowonganBerlaku.getLogoPerusahaan())
                        .into(LogoPerusahaan);
                NamaLowongan.setText(LowonganBerlaku.getNama());
                WaktuLowonganBerlaku.setText(LowonganBerlaku.getBerlaku());
                EdPersyaratan.setText(LowonganBerlaku.getPersyaratan());

                TombolDaftarLowongan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PendftaranLowongan pendftaranLowongan = new PendftaranLowongan(
                                Common.currentUserKlien.getNama(),
                                Common.currentUserKlien.getTahun_kelulusan(),
                                Common.currentUserKlien.getAsal_sekolah(),
                                Common.currentUserKlien.getJurusan(),
                                Common.currentUserKlien.getPonsel(),
                                Common.currentUserKlien.getEmail(),
                                LowonganBerlaku.getPerusahaan(),
                                LowonganBerlaku.getNama(),
                                LowonganBerlaku.getBerlaku()
                        );

                        RefrensiPendaftaranLowongan.push().setValue(pendftaranLowongan);
                        Toast.makeText(LowonganDetail.this, "Berhasil Mendaftar", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    private void inisialisasiKomponen() {
        NamaLowongan = (TextView)findViewById(R.id.namaLowongan);
        WaktuLowonganBerlaku = (TextView)findViewById(R.id.txtWaktuBerlaku);
        LogoPerusahaan = (ImageView)findViewById(R.id.gambarLogoPerusahaan);
        EdPersyaratan = (EditText)findViewById(R.id.edPersyaratan);
        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);

        TombolDaftarLowongan = (Button)findViewById(R.id.tmblDaftarLowongan);

        EdPersyaratan.setEnabled(false);
    }
}

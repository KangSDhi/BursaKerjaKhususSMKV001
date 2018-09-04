package com.example.sinon.bursakerjakhusussmk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.sinon.bursakerjakhusussmk.Common.Common;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;
import org.w3c.dom.Text;

public class Akun extends AppCompatActivity {

    ImageView ImageViewProfile;
    TextView TxtAccountNama;
    TextView TxtAccountJenisKelamin;
    TextView TxtAccountTempatLahir;
    TextView TxtAccountTanggalLahir;
    TextView TxtAccountAsalSekolah;
    TextView TxtAccountJurusan;
    TextView TxtAccountTahunLulus;
    TextView TxtAccountAlamat;
    TextView TxtAccountKabupaten;
    TextView TxtAccountAlamatSurel;
    TextView TxtAccountNomorPonsel;
    TextView TxtAccountTinggiBadan;
    TextView TxtAccountBeratBadan;
    Button BtnUbah;

    //Firebase
    DatabaseReference userKlienDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun);

        initialitationItem();
        methodAction();

    }



    private void initialitationItem() {
        ImageViewProfile = (ImageView)findViewById(R.id.imgProfile);
        TxtAccountNama = (TextView)findViewById(R.id.edAccountNama);
        TxtAccountJenisKelamin = (TextView)findViewById(R.id.edAccountJenisKelamin);
        TxtAccountTempatLahir = (TextView)findViewById(R.id.edAccountTempatLahir);
        TxtAccountTanggalLahir = (TextView)findViewById(R.id.edAccountTanggalLahir);
        TxtAccountAsalSekolah = (TextView)findViewById(R.id.edAccountAsalSekolah);
        TxtAccountJurusan = (TextView)findViewById(R.id.edAccountJurusan);
        TxtAccountTahunLulus = (TextView)findViewById(R.id.edAccountTahunLulus);
        TxtAccountAlamat = (TextView)findViewById(R.id.edAccountAlamat);
        TxtAccountKabupaten = (TextView)findViewById(R.id.edAccountKabupaten);
        TxtAccountAlamatSurel = (TextView)findViewById(R.id.edAccountAlamatSurel);
        TxtAccountNomorPonsel = (TextView)findViewById(R.id.edAccountNomorPonsel);
        TxtAccountTinggiBadan = (TextView)findViewById(R.id.edAccountTinggiBadan);
        TxtAccountBeratBadan = (TextView)findViewById(R.id.edAccountBeratBadan);
        BtnUbah = (Button)findViewById(R.id.btnPengaturanAkun);
    }

    private void methodAction() {
        Picasso.with(getBaseContext()).load(Common.currentUserKlien.getFoto_Profil()).into(ImageViewProfile);
        TxtAccountNama.setText(Common.currentUserKlien.getNama());
        TxtAccountJenisKelamin.setText(Common.currentUserKlien.getJenis_kelamin());
        TxtAccountTempatLahir.setText(Common.currentUserKlien.getTempat_Lahir());
        TxtAccountTanggalLahir.setText(Common.currentUserKlien.getTanggal_lahir());
        TxtAccountAsalSekolah.setText(Common.currentUserKlien.getAsal_sekolah());
        TxtAccountJurusan.setText(Common.currentUserKlien.getJurusan());
        TxtAccountTahunLulus.setText(Common.currentUserKlien.getTahun_kelulusan());
        TxtAccountAlamat.setText(Common.currentUserKlien.getAlamat());
        TxtAccountKabupaten.setText(Common.currentUserKlien.getKabupaten_atau_kota());
        TxtAccountAlamatSurel.setText(Common.currentUserKlien.getEmail());
        TxtAccountNomorPonsel.setText(Common.currentUserKlien.getPonsel());
        TxtAccountTinggiBadan.setText(Common.currentUserKlien.getTinggi_badan());
        TxtAccountBeratBadan.setText(Common.currentUserKlien.getBerat_badan());

        BtnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Akun.this, UbahAkun.class));
            }
        });
    }
}

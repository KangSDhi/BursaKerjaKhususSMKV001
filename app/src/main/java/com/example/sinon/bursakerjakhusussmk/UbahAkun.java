package com.example.sinon.bursakerjakhusussmk;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.sinon.bursakerjakhusussmk.Common.Common;
import com.example.sinon.bursakerjakhusussmk.Common.CommonSampah;
import com.example.sinon.bursakerjakhusussmk.Model.UserKlien;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.*;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.HashMap;
import java.util.Map;

public class UbahAkun extends AppCompatActivity {

    private TextInputEditText EdNama;
    private TextInputEditText EdTempatLahir;
    private TextInputEditText EdTanggalLahir;
    private Spinner EdJenisKelamin;
    private TextInputEditText EdAlamat;
    private TextInputEditText EdAlamatKabupatenKota;
    private TextInputEditText EdTinggiBadan;
    private TextInputEditText EdBeratBadan;
    private TextInputEditText EdAsalSekolah;
    private TextInputEditText EdJurusan;
    private TextInputEditText EdTahunLulus;
    private TextInputEditText EdNomorPonsel;
    private TextInputEditText EdEmail;

    private CircleImageView ImgProfile, ImgProfileDialog;

    private Button BtnPerbaruiAkun, BtnPengaturanSandi;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userKlienDatabase = database.getReference("PenggunaKlien");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_akun);

        initialitationItem();
        metodeAksi();
    }

    private void metodeAksi() {
        Picasso.with(getBaseContext()).load(Common.currentUserKlien.getFoto_Profil()).into(ImgProfile);
        EdNama.setText(Common.currentUserKlien.getNama());
        EdTempatLahir.setText(Common.currentUserKlien.getTempat_Lahir());
        EdTanggalLahir.setText(Common.currentUserKlien.getTanggal_lahir());
        //Spinner Common Text
        ArrayAdapter spinnerAdap = (ArrayAdapter)EdJenisKelamin.getAdapter();
        int posisiSpinner = spinnerAdap.getPosition(Common.currentUserKlien.getJenis_kelamin());
        EdJenisKelamin.setSelection(posisiSpinner);
        //
        EdAlamat.setText(Common.currentUserKlien.getAlamat());
        EdAlamatKabupatenKota.setText(Common.currentUserKlien.getKabupaten_atau_kota());
        EdTinggiBadan.setText(Common.currentUserKlien.getTinggi_badan());
        EdBeratBadan.setText(Common.currentUserKlien.getBerat_badan());
        EdAsalSekolah.setText(Common.currentUserKlien.getAsal_sekolah());
        EdJurusan.setText(Common.currentUserKlien.getJurusan());
        EdTahunLulus.setText(Common.currentUserKlien.getTahun_kelulusan());
        EdNomorPonsel.setText(Common.currentUserKlien.getPonsel());
        EdEmail.setText(Common.currentUserKlien.getEmail());

        BtnPerbaruiAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fungsiPerbaruiAkun();
            }
        });

        BtnPengaturanSandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fungsiGantiSandi();
            }
        });

    }

    private void fungsiGantiSandi() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(UbahAkun.this);
        alertDialog.setTitle("Perubahan Sandi");
        alertDialog.setIcon(R.drawable.ic_security);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View layout_password_setting = layoutInflater.inflate(R.layout.layout_password_setting, null);
        //
        final TextInputEditText EdSandiLama = (TextInputEditText)layout_password_setting.findViewById(R.id.edtSandiLama);
        final TextInputEditText EdSandiBaru = (TextInputEditText)layout_password_setting.findViewById(R.id.edtSandiBaru);
        //
        alertDialog.setView(layout_password_setting);
        //
        alertDialog.setPositiveButton("Perbarui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (EdSandiLama.getText().toString().equals(Common.currentUserKlien.getSandi()))
                {
                    userKlienDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String, Object> perbaruiSandi = new HashMap<>();
                            perbaruiSandi.put("sandi", EdSandiBaru.getText().toString());

                            final UserKlien userKlien = dataSnapshot.child(CommonSampah.currentUserKlien.getNamaPengguna()).getValue(UserKlien.class);

                            userKlienDatabase.child(CommonSampah.currentUserKlien.getNamaPengguna())
                                    .updateChildren(perbaruiSandi)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            userKlien.setSandi(EdSandiBaru.getText().toString());
                                            Common.currentUserKlien = userKlien;
                                            Toast.makeText(UbahAkun.this, "Sandi Telah Diperbarui", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(UbahAkun.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(UbahAkun.this, "Sandi Lama Salah", Toast.LENGTH_SHORT).show();

                }
            }
        }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void fungsiPerbaruiAkun() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(UbahAkun.this);
        alertDialog.setTitle("Konfirmasi Perubahan Akun");
        alertDialog.setIcon(R.drawable.ic_security);

        alertDialog.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                userKlienDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, Object> aksiPerbarui = new HashMap<>();
                        aksiPerbarui.put("nama", EdNama.getText().toString());
                        aksiPerbarui.put("tempat_Lahir", EdTempatLahir.getText().toString());
                        aksiPerbarui.put("tanggal_lahir", EdTanggalLahir.getText().toString());
                        aksiPerbarui.put("jenis_kelamin", EdJenisKelamin.getSelectedItem().toString());
                        aksiPerbarui.put("alamat", EdAlamat.getText().toString());
                        aksiPerbarui.put("kabupaten_atau_kota", EdAlamatKabupatenKota.getText().toString());
                        aksiPerbarui.put("tinggi_badan", EdTinggiBadan.getText().toString());
                        aksiPerbarui.put("berat_badan", EdBeratBadan.getText().toString());
                        aksiPerbarui.put("asal_sekolah", EdAsalSekolah.getText().toString());
                        aksiPerbarui.put("jurusan", EdJurusan.getText().toString());
                        aksiPerbarui.put("tahun_kelulusan", EdTahunLulus.getText().toString());
                        aksiPerbarui.put("ponsel", EdNomorPonsel.getText().toString());
                        aksiPerbarui.put("email", EdEmail.getText().toString());

                        final UserKlien userKlien = dataSnapshot.child(CommonSampah.currentUserKlien.getNamaPengguna()).getValue(UserKlien.class);

                        userKlienDatabase.child(CommonSampah.currentUserKlien.getNamaPengguna())
                                .updateChildren(aksiPerbarui)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        userKlien.setNama(EdNama.getText().toString());
                                        userKlien.setTempat_Lahir(EdTempatLahir.getText().toString());
                                        userKlien.setTanggal_lahir(EdTanggalLahir.getText().toString());
                                        userKlien.setJenis_kelamin(EdJenisKelamin.getSelectedItem().toString());
                                        userKlien.setAlamat(EdAlamat.getText().toString());
                                        userKlien.setKabupaten_atau_kota(EdAlamatKabupatenKota.getText().toString());
                                        userKlien.setTinggi_badan(EdTinggiBadan.getText().toString());
                                        userKlien.setBerat_badan(EdBeratBadan.getText().toString());
                                        userKlien.setAsal_sekolah(EdAsalSekolah.getText().toString());
                                        userKlien.setJurusan(EdJurusan.getText().toString());
                                        userKlien.setTahun_kelulusan(EdTahunLulus.getText().toString());
                                        userKlien.setPonsel(EdNomorPonsel.getText().toString());
                                        userKlien.setEmail(EdEmail.getText().toString());

                                        Common.currentUserKlien = userKlien;
                                        Toast.makeText(UbahAkun.this, "Akun Diperbarui", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(UbahAkun.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        alertDialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void initialitationItem() {

        EdNama = (TextInputEditText)findViewById(R.id.edtNama);
        EdTempatLahir = (TextInputEditText)findViewById(R.id.edtTempatLahir);
        EdTanggalLahir = (TextInputEditText) findViewById(R.id.edtTanggalLahir);
        EdJenisKelamin = (Spinner) findViewById(R.id.edtJenisKelamin);
        EdAlamat = (TextInputEditText)findViewById(R.id.edtAlamat);
        EdAlamatKabupatenKota = (TextInputEditText)findViewById(R.id.edtAlamatKabupatenKota);
        EdTinggiBadan = (TextInputEditText)findViewById(R.id.edtTinggiBadan);
        EdBeratBadan = (TextInputEditText)findViewById(R.id.edtBeratBadan);
        EdAsalSekolah = (TextInputEditText)findViewById(R.id.edtAsalSekolah);
        EdJurusan = (TextInputEditText)findViewById(R.id.edtJurusan);
        EdTahunLulus = (TextInputEditText)findViewById(R.id.edtTahunLulus);
        EdNomorPonsel = (TextInputEditText)findViewById(R.id.edtNomorPonsel);
        EdEmail = (TextInputEditText)findViewById(R.id.edtEmail);

        ImgProfile = (CircleImageView)findViewById(R.id.imgProfileAkun);

        BtnPerbaruiAkun = (Button)findViewById(R.id.btnPerbaruiAkun);
        BtnPengaturanSandi = (Button)findViewById(R.id.btnPengaturanSandi);
    }

}
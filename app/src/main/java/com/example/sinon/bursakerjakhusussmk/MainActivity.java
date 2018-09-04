package com.example.sinon.bursakerjakhusussmk;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.sinon.bursakerjakhusussmk.Common.Common;
import com.example.sinon.bursakerjakhusussmk.Common.CommonSampah;
import com.example.sinon.bursakerjakhusussmk.Model.UserKlien;
import com.google.firebase.database.*;

public class MainActivity extends AppCompatActivity {

    TextInputEditText EdNamaPengguna, EdSandi;
    Button BtnMasuk, BtnDaftar;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("PenggunaKlien");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialitationItem();
        functionAction();
    }

    private void initialitationItem() {

        EdNamaPengguna = (TextInputEditText)findViewById(R.id.edNamaPengguna);
        EdSandi = (TextInputEditText)findViewById(R.id.edSandi);
        BtnMasuk = (Button)findViewById(R.id.btnMasuk);
        BtnDaftar = (Button)findViewById(R.id.btnDaftar);

        BtnMasuk.setEnabled(false);
    }

    private void functionAction() {

        EdNamaPengguna.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(EdNamaPengguna.getText().toString()))
                {
                    BtnMasuk.setEnabled(false);
                }
                else
                {
                    BtnMasuk.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        BtnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(EdNamaPengguna.getText().toString()).exists())
                        {
                            UserKlien userKlien = dataSnapshot.child(EdNamaPengguna.getText().toString()).getValue(UserKlien.class);
                            userKlien.setNamaPengguna(EdNamaPengguna.getText().toString()); // ID NamaPengguna
                            if (userKlien.getSandi().equals(EdSandi.getText().toString()))
                            {
                                Toast.makeText(MainActivity.this, "Selamat Datang "+userKlien.getNama(), Toast.LENGTH_SHORT).show();
                                {
                                    Common.currentUserKlien = userKlien;
                                    CommonSampah.currentUserKlien= userKlien;
                                    startActivity(new Intent(MainActivity.this, Dasbor.class));
                                }
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "Sandi Salah", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Pengguna Belum Terdaftar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        BtnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Daftar.class));
            }
        });
    }
}
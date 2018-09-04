package com.example.sinon.bursakerjakhusussmk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class TentangAplikasi extends AppCompatActivity {


    Button BtnValidasiDigital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang_aplikasi);

        BtnValidasiDigital = (Button)findViewById(R.id.btnValidasiDigital);

        BtnValidasiDigital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TentangAplikasi.this, ValidasiKodeBatang.class));
            }
        });
    }
}

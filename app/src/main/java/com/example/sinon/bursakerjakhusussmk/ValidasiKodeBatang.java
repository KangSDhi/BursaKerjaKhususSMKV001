package com.example.sinon.bursakerjakhusussmk;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.example.sinon.bursakerjakhusussmk.Common.Common;
import com.google.firebase.database.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ValidasiKodeBatang extends AppCompatActivity {

    ImageView GambarKodebatang;
    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validasi_kode_batang);

        GambarKodebatang = (ImageView)findViewById(R.id.gambarKodeBatang);

                String textBarcode = Common.currentUserKlien.getNama()+","+Common.currentUserKlien.getJurusan()+","+Common.currentUserKlien.getAsal_sekolah();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(textBarcode, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    GambarKodebatang.setImageBitmap(bitmap);
                }
                catch (WriterException e){
                    e.printStackTrace();
                }


        /*try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            GambarKodebatang.setImageBitmap(bitmap);
        }
        catch (WriterException e){
            e.printStackTrace();
        }*/
    }
}

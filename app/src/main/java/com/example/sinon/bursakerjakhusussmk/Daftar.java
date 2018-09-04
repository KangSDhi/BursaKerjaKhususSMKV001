package com.example.sinon.bursakerjakhusussmk;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.example.sinon.bursakerjakhusussmk.Model.UserKlien;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.Calendar;
import java.util.UUID;

public class Daftar extends AppCompatActivity {

    private TextInputEditText EdNamaPengguna;
    private TextInputEditText EdSandi;
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

    private Button BtnDaftarForm;

    //init Firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("PenggunaKlien");

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    //Date Picker
    Calendar calendar;
    int day, month, year;

    //add_new_image_profile_layout
    Button BtnPilih;
    private final int PICK_IMAGE_REQUEST = 71;
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        initialitationItem();
        functionAction();
    }

    private void initialitationItem() {
        EdNamaPengguna = (TextInputEditText)findViewById(R.id.edNamaPengguna);
        EdSandi = (TextInputEditText)findViewById(R.id.edSandi);
        EdNama = (TextInputEditText)findViewById(R.id.edNama);
        EdTempatLahir = (TextInputEditText)findViewById(R.id.edTempatLahir);
        EdTanggalLahir = (TextInputEditText) findViewById(R.id.edTanggalLahir);
        EdJenisKelamin = (Spinner) findViewById(R.id.edJenisKelamin);
        EdAlamat = (TextInputEditText)findViewById(R.id.edAlamat);
        EdAlamatKabupatenKota = (TextInputEditText)findViewById(R.id.edAlamatKabupatenKota);
        EdTinggiBadan = (TextInputEditText)findViewById(R.id.edTinggiBadan);
        EdBeratBadan = (TextInputEditText)findViewById(R.id.edBeratBadan);
        EdAsalSekolah = (TextInputEditText)findViewById(R.id.edAsalSekolah);
        EdJurusan = (TextInputEditText)findViewById(R.id.edJurusan);
        EdTahunLulus = (TextInputEditText)findViewById(R.id.edTahunLulus);
        EdNomorPonsel = (TextInputEditText)findViewById(R.id.edNomorPonsel);
        EdEmail = (TextInputEditText)findViewById(R.id.edEmail);

        ImgProfile = (CircleImageView)findViewById(R.id.imgProfile);

        BtnDaftarForm = (Button)findViewById(R.id.btnDaftarForm);

        EdSandi.setEnabled(false);
        EdNama.setEnabled(false);
        EdTempatLahir.setEnabled(false);
        EdTanggalLahir.setEnabled(false);
        EdJenisKelamin.setEnabled(false);
        EdAlamat.setEnabled(false);
        EdAlamatKabupatenKota.setEnabled(false);
        EdTinggiBadan.setEnabled(false);
        EdBeratBadan.setEnabled(false);
        EdAsalSekolah.setEnabled(false);
        EdJurusan.setEnabled(false);
        EdTahunLulus.setEnabled(false);
        EdNomorPonsel.setEnabled(false);
        EdEmail.setEnabled(false);

        BtnDaftarForm.setEnabled(false);

    }

    private void functionAction() {

        eventDatePicker();

        actionUnlockForm();

        actionDaftar();

    }

    private void imagePicker() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Edit Foto");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_image_layout = inflater.inflate(R.layout.layout_add_image_profile_layout, null);

        BtnPilih = add_image_layout.findViewById(R.id.btnPilih);
        //ImgProfileDialog = add_image_layout.findViewById(R.id.gambarProfileDialog);


        BtnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihGambar();
            }
        });

        alertDialog.setView(add_image_layout);



        alertDialog.setPositiveButton("IYA", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                ImgProfile.setImageURI(imageUri);
            }
        });

        alertDialog.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();

    }

    private void pilihGambar() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE_REQUEST)
        {
            imageUri = data.getData();
            //ImgProfileDialog.setImageURI(imageUri);
        }
    }

    public void actionDaftar() {

        ImgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker();
            }
        });

        final ProgressDialog mDialog = new ProgressDialog(this);
        mDialog.setMessage("Mengunggah...");



        BtnDaftarForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(EdNamaPengguna.getText().toString()).exists())
                        {
                            Toast.makeText(Daftar.this, "Nama Pengguna Telah Digunakan", Toast.LENGTH_SHORT).show();
                        }
                        else if (imageUri == null)
                        {
                            Toast.makeText(Daftar.this, "Foto Profil Kosong!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String imageName = UUID.randomUUID().toString();
                            final StorageReference imageFolder = storageReference.child("images/"+imageName);
                            imageFolder.putFile(imageUri)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    UserKlien userKlien = new UserKlien(EdNama.getText().toString(),
                                                            EdTempatLahir.getText().toString(),
                                                            EdTanggalLahir.getText().toString(),
                                                            EdJenisKelamin.getSelectedItem().toString(),
                                                            EdTinggiBadan.getText().toString(),
                                                            EdBeratBadan.getText().toString(),
                                                            EdTahunLulus.getText().toString(),
                                                            EdAsalSekolah.getText().toString(),
                                                            EdJurusan.getText().toString(),
                                                            EdAlamat.getText().toString(),
                                                            EdAlamatKabupatenKota.getText().toString(),
                                                            EdNomorPonsel.getText().toString(),
                                                            EdEmail.getText().toString(),
                                                            EdSandi.getText().toString(),
                                                            uri.toString());

                                                    databaseReference.child(EdNamaPengguna.getText().toString()).setValue(userKlien);
                                                    Toast.makeText(Daftar.this, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                }
                                            });
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            mDialog.dismiss();
                                            Toast.makeText(Daftar.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                            mDialog.show();
                                            mDialog.setMessage("Proses "+progress+"%");
                                        }
                                    });

                            /**UserKlien userKlien = new UserKlien(EdNama.getText().toString(),
                                    EdTempatLahir.getText().toString(),
                                    EdTanggalLahir.getText().toString(),
                                    EdJenisKelamin.getText().toString(),
                                    EdTinggiBadan.getText().toString(),
                                    EdBeratBadan.getText().toString(),
                                    EdTahunLulus.getText().toString(),
                                    EdAsalSekolah.getText().toString(),
                                    EdJurusan.getText().toString(),
                                    EdAlamat.getText().toString(),
                                    EdAlamatKabupatenKota.getText().toString(),
                                    EdNomorPonsel.getText().toString(),
                                    EdEmail.getText().toString(),
                                    EdSandi.getText().toString());

                            databaseReference.child(EdNamaPengguna.getText().toString()).setValue(userKlien);
                            Toast.makeText(Daftar.this, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show();
                            finish();*/
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    private void eventDatePicker() {
        calendar = Calendar.getInstance();

        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        month = month+1;

        EdTanggalLahir.setText(day+"/"+month+"/"+year);

        EdTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogAction();
            }
        });
    }

    private void actionUnlockForm() {

        EdNamaPengguna.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(EdNamaPengguna.getText().toString()))
                {
                    EdSandi.setEnabled(false);
                }
                else
                {
                    EdSandi.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EdSandi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(EdSandi.getText().toString()))
                {
                    EdNama.setEnabled(false);
                }
                else
                {
                    EdNama.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EdNama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(EdNama.getText().toString()))
                {
                    EdTempatLahir.setEnabled(false);
                }
                else
                {
                    EdTempatLahir.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EdTempatLahir.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(EdTempatLahir.getText().toString()))
                {
                    EdTanggalLahir.setEnabled(false);
                }
                else
                {
                    EdTanggalLahir.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EdTanggalLahir.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(EdTanggalLahir.getText().toString()))
                {
                    EdJenisKelamin.setEnabled(false);
                    EdAlamat.setEnabled(false);
                }
                else
                {
                    EdJenisKelamin.setEnabled(true);
                    EdAlamat.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /*EdJenisKelamin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(EdJenisKelamin.getText().toString()))
                {
                    EdAlamat.setEnabled(false);
                }
                else
                {
                    EdAlamat.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        EdAlamat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(EdAlamat.getText().toString()))
                {
                    EdAlamatKabupatenKota.setEnabled(false);
                }
                else
                {
                    EdAlamatKabupatenKota.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EdAlamatKabupatenKota.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(EdAlamatKabupatenKota.getText().toString()))
                {
                    EdTinggiBadan.setEnabled(false);
                }
                else
                {
                    EdTinggiBadan.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EdTinggiBadan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(EdTinggiBadan.getText().toString()))
                {
                    EdBeratBadan.setEnabled(false);
                }
                else
                {
                    EdBeratBadan.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EdBeratBadan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(EdBeratBadan.getText().toString()))
                {
                    EdAsalSekolah.setEnabled(false);
                }
                else
                {
                    EdAsalSekolah.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EdAsalSekolah.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(EdAsalSekolah.getText().toString()))
                {
                    EdJurusan.setEnabled(false);
                }
                else
                {
                    EdJurusan.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EdJurusan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(EdJurusan.getText().toString()))
                {
                    EdTahunLulus.setEnabled(false);
                }
                else
                {
                    EdTahunLulus.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EdTahunLulus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(EdTahunLulus.getText().toString()))
                {
                    EdNomorPonsel.setEnabled(false);
                }
                else
                {
                    EdNomorPonsel.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EdNomorPonsel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(EdNomorPonsel.getText().toString()))
                {
                    EdEmail.setEnabled(false);
                }
                else
                {
                    EdEmail.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EdEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (EdEmail.getText().toString().equals(""))
                {
                    BtnDaftarForm.setEnabled(false);
                }
                else
                {
                    BtnDaftarForm.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void DatePickerDialogAction() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(Daftar.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                EdTanggalLahir.setText(dayOfMonth+"/"+month+"/"+year);
            }
        },year,month,day);
        datePickerDialog.show();
    }

}

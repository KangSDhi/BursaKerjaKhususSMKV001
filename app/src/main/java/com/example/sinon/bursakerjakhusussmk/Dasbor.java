package com.example.sinon.bursakerjakhusussmk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.example.sinon.bursakerjakhusussmk.Common.Common;
import com.example.sinon.bursakerjakhusussmk.Interface.ItemClickListener;
import com.example.sinon.bursakerjakhusussmk.Model.Lowongan;
import com.example.sinon.bursakerjakhusussmk.ViewHolder.LowonganViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import de.hdodenhof.circleimageview.CircleImageView;

public class Dasbor extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference lowonganRefrence = firebaseDatabase.getReference("Lowongan");

    TextView TxtNama;
    CircleImageView ImgProfil;

    RecyclerView recyclerLowongan;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Lowongan, LowonganViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasbor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rimayatLamaran = new Intent(Dasbor.this, RiwayatLamaran.class);
                rimayatLamaran.putExtra("RiwayatId", Common.currentUserKlien.getNama());
                startActivity(rimayatLamaran);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Set Nama dan Foto Profil Untuk User
        View headerView = navigationView.getHeaderView(0);
        TxtNama = headerView.findViewById(R.id.txtNama);
        TxtNama.setText(Common.currentUserKlien.getNama());

        ImgProfil = headerView.findViewById(R.id.imageViewProfil);
        Picasso.with(getBaseContext()).load(Common.currentUserKlien.getFoto_Profil()).into(ImgProfil);

        //Load Lowongan
        recyclerLowongan = (RecyclerView)findViewById(R.id.recyclerLowonganDashbor);
        recyclerLowongan.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerLowongan.setLayoutManager(layoutManager);

        loadLowongan();

    }

    private void loadLowongan() {

        adapter = new FirebaseRecyclerAdapter<Lowongan, LowonganViewHolder>(Lowongan.class, R.layout.item_lowongan, LowonganViewHolder.class, lowonganRefrence) {
            @Override
            protected void populateViewHolder(LowonganViewHolder viewHolder, final Lowongan model, int position) {
                viewHolder.LowNama.setText(model.getNama());
                viewHolder.LowPerusahaan.setText(model.getPerusahaan());
                viewHolder.LowBerlaku.setText(model.getBerlaku());

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void OnClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(Dasbor.this, ""+model.getNama(), Toast.LENGTH_SHORT).show();
                        {
                            Intent lowonganDetail = new Intent(Dasbor.this, LowonganDetail.class);
                            lowonganDetail.putExtra("LowonganId", adapter.getRef(position).getKey());
                            startActivity(lowonganDetail);
                        }
                    }
                });
            }
        };

        recyclerLowongan.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dasbor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(Dasbor.this, TentangAplikasi.class));
            //return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_daftar_perusahaan) {
            startActivity(new Intent(Dasbor.this, DaftarPerusahaan.class));
        } else if (id == R.id.nav_riwayat) {
            Intent rimayatLamaran = new Intent(Dasbor.this, RiwayatLamaran.class);
            rimayatLamaran.putExtra("RiwayatId", Common.currentUserKlien.getNama());
            startActivity(rimayatLamaran);
        } else if (id == R.id.nav_akun) {
            startActivity(new Intent(Dasbor.this, Akun.class));
        } else if (id == R.id.nav_keluar) {
            //Keluar
            Intent keluar = new Intent(Dasbor.this, MainActivity.class);
            keluar.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(keluar);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

package com.example.pertemuan5_recycleview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import com.example.pertemuan5_recycleview.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
// tugas recycle view
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
// tugas notification
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {
    // tugas recycle view
    RecyclerView recylerView;
    String s1[], s2[], s3[], s4[];
    int images[] = {R.drawable.goreng,R.drawable.rendang,R.drawable.bawang,R.drawable.soto};

    private DrawerLayout dl;
    private ActivityMainBinding binding;
    private ActionBarDrawerToggle abut;
    private View button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // tugas recycle view
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recylerView = findViewById(R.id.recyclerView);
        s1 = getResources().getStringArray(R.array.mie);
        s2 = getResources().getStringArray(R.array.harga);
        s3 = getResources().getStringArray(R.array.star);
        MakananAdapter appAdapter = new MakananAdapter(this, s1, s2, s3, images);
        recylerView.setAdapter(appAdapter);
        LinearLayoutManager layoutManager  = new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false);
        recylerView.setLayoutManager(layoutManager);
        recylerView.setItemAnimator(new DefaultItemAnimator());

        dl = (DrawerLayout) findViewById(R.id.dl);
        abut = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        abut.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abut);
        abut.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav_view = (NavigationView) findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    Intent a = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(a);
                } else if (id == R.id.nav_message) {
                    Intent a = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(a);
                } else if (id == R.id.nav_alarm) {
                    Intent a = new Intent(MainActivity.this, MainActivity3.class);
                    startActivity(a);
                } else if (id == R.id.nav_sqlite) {
                    Intent a = new Intent(MainActivity.this, MainActivity4.class);
                    startActivity(a);
                } else if (id == R.id.nav_profile) {
                    Intent a = new Intent(MainActivity.this, MainActivity5.class);
                    startActivity(a);
                } else if (id == R.id.nav_api) {
                    Intent a = new Intent(MainActivity.this, MainActivity6.class);
                    startActivity(a);
                }
                return true;
            }
        });

        final OneTimeWorkRequest request = new
                OneTimeWorkRequest.Builder(MyWorker.class).build();
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkManager.getInstance().enqueueUniqueWork("Notifikasi",
                        ExistingWorkPolicy.REPLACE, request);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abut.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    // tugas notification
}
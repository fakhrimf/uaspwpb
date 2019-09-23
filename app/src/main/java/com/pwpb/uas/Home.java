package com.pwpb.uas;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements RvAdapter.Click{

    FloatingActionButton fab;
    RecyclerView rv;
    List<Notes> notesList = new ArrayList<>();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbNotes = database.getReference("notes");

    private void initUI() {
        fab = findViewById(R.id.fab);
        rv = findViewById(R.id.rv);
    }

    private void initBtn() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, AddNotes.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initUI();
        initBtn();
        if (!haveNetworkConnection()){
            Toast.makeText(this,"App ini membutuhkan koneksi internet, harap nyalakan internet untuk melanjutkan.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Adding value event listener
        dbNotes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notesList.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Notes notes = ds.getValue(Notes.class);
                    notesList.add(notes);
                }

                rv.setHasFixedSize(true);
                RecyclerView.LayoutManager lm = new LinearLayoutManager(Home.this);
                rv.setLayoutManager(lm);

                //Creating RecyclerView Adapter
                RvAdapter rvAdapter = new RvAdapter(Home.this,Home.this, notesList);
                //Attaching Adapter
                rv.setAdapter(rvAdapter);
                rvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void click(String id,String judul, String desc, String act){
        Intent i = new Intent(this,AddNotes.class);
        i.putExtra("id",id);
        i.putExtra("judul",judul);
        i.putExtra("desc",desc);
        i.putExtra("act",act);
        startActivity(i);
    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    //Menus
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate Menu pada Action Bar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //SimplifiableIfStatement
        if (id == R.id.refresh) {
            onStart();
            Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

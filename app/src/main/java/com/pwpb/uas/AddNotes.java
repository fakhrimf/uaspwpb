package com.pwpb.uas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddNotes extends AppCompatActivity {

    EditText edtJudul, edtDesc;
    Button btnAdd;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference dbNotes = database.getReference("notes");
    String ID;
    String act;

    private void initUI() {
        edtJudul = findViewById(R.id.edtJudul);
        edtDesc = findViewById(R.id.edtDesc);
        btnAdd = findViewById(R.id.btnAdd);
    }

    private void initBtn() {
        System.out.println(btnAdd.getText().toString());
        if (btnAdd.getText().toString().equalsIgnoreCase("ubah")) {
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String judul = edtJudul.getText().toString().trim();
                    String desc = edtDesc.getText().toString().trim();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm", Locale.US);
                    String tgl = sdf.format(new Date());
                    if (judul.isEmpty()) {
                        edtJudul.setError("Judul Harus diisi");
                        if (desc.isEmpty()) {
                            edtDesc.setError("Deskripsi Harus diisi");
                        }
                    } else if (desc.isEmpty()) {
                        edtDesc.setError("Deskripsi Harus diisi");
                    } else {
                        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("notes").child(ID);
                        Notes notes = new Notes(ID, judul, desc, tgl);
                        dR.setValue(notes);
                        Toast.makeText(getApplicationContext(), notes.getJudul() + " Updated", Toast.LENGTH_SHORT).show();
                        AddNotes.super.onBackPressed();
                    }
                }
            });
        } else {
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String judul = edtJudul.getText().toString().trim();
                    String desc = edtDesc.getText().toString().trim();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm", Locale.US);
                    String tgl = sdf.format(new Date());
                    if (judul.isEmpty()) {
                        edtJudul.setError("Judul Harus diisi");
                        if (desc.isEmpty()) {
                            edtDesc.setError("Deskripsi Harus diisi");
                        }
                    } else if (desc.isEmpty()) {
                        edtDesc.setError("Deskripsi Harus diisi");
                    } else {
                        String id = dbNotes.push().getKey();
                        Notes notes = new Notes(id, judul, desc, tgl);
                        dbNotes.child(id).setValue(notes);
                        Toast.makeText(AddNotes.this, "Note " + judul + " ditambahkan.", Toast.LENGTH_SHORT).show();
                        AddNotes.super.onBackPressed();
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        getSupportActionBar().setTitle("Tambah data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initUI();
        Intent i = getIntent();
        String judul = i.getStringExtra("judul");
        String desc = i.getStringExtra("desc");
        act = i.getStringExtra("act");
        ID = i.getStringExtra("id");
        if (act != null) {
            if (act.equalsIgnoreCase("edit")) {
                getSupportActionBar().setTitle("Ubah Data");
                btnAdd.setText("Ubah");
                edtJudul.setText(judul);
                edtDesc.setText(desc);

            }
        }
        initBtn();
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate Menu pada Action Bar
        if (act != null) if (act.equalsIgnoreCase("edit")) getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //SimplifiableIfStatement
        if (act != null ) if (act.equalsIgnoreCase("edit") && id == R.id.delete) {
            DatabaseReference dR = FirebaseDatabase.getInstance().getReference("notes").child(ID);
            dR.removeValue();
            Toast.makeText(this,"Note dihapus!",Toast.LENGTH_SHORT).show();
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.example.aplikasipencatatannoteus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class FormTugas extends AppCompatActivity {

    private EditText edtCatatan;
    private Button btnTambah;
    private SharedPreferences sharedPreferences;
    FirebaseFirestore fireDb;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_tugas);
        edtCatatan = findViewById(R.id.edtTugas);
        btnTambah = findViewById(R.id.postTugas);

        user = FirebaseAuth.getInstance().getCurrentUser();
        fireDb = FirebaseFirestore.getInstance();

        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);

        if (sharedPreferences.getString("catatan", null) != null) {
            edtCatatan.setText(sharedPreferences.getString("catatan", null));
        }

        btnTambah.setOnClickListener(v -> {
            @SuppressLint("CommitPrefEdits")
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("catatan", edtCatatan.getText().toString());
            editor.apply();
            Toast.makeText(getApplicationContext(), "Catatan Tersimpan", Toast.LENGTH_SHORT).show();
        });
    }

    public void tambahTugas(View view) {
        String userId = user.getUid();
        String email = user.getEmail();
        String catatan = edtCatatan.getText().toString();

        fireDb.collection("catatan").document()
                .set(new Catatan(userId, email, catatan))
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("store_error", e.getMessage());
                    }
                });

        finish();
    }


    public void lihatPenting(View view) {
        startActivity(new Intent(this, ListTugas.class));
    }
}
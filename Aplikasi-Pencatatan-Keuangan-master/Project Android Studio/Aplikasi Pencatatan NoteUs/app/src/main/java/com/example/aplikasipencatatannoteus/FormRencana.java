package com.example.aplikasipencatatannoteus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class FormRencana extends AppCompatActivity {

    EditText edtCatatan;
    FirebaseFirestore fireDb;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_rencana);
        edtCatatan = findViewById(R.id.edtRencana);

        user = FirebaseAuth.getInstance().getCurrentUser();
        fireDb = FirebaseFirestore.getInstance();
    }

    public void tambahRencana(View view) {
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

    public void lihatRencana(View view) {
        startActivity(new Intent(this, ListRencana.class));
    }
}
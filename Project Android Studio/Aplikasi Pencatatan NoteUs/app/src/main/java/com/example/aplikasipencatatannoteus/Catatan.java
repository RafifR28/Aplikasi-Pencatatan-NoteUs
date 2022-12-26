package com.example.aplikasipencatatannoteus;

public class Catatan {
    public String uid;
    public String userId;
    public String email;
    public String catatan;
    public String Nama;

    public Catatan(String userId, String email, String catatan) {

    }

    public Catatan(String userId, String email, String catatan, String nama) {
        this.userId = userId;
        this.email = email;
        this.catatan = catatan;
        this.Nama = nama;
    }
}

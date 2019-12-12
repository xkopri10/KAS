package com.example.kas_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.Objects;
import java.math.BigInteger;
import java.util.Random;


public class DecryptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decrypt);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BigInteger p0 = largePrime(32);
        BigInteger p1 = largePrime(64);
        BigInteger p2 = largePrime(128);
        BigInteger p4 = largePrime(256);

        Log.e("32: ", p0.toString());
        Log.e("64: ", p1.toString());
        Log.e("128: ", p2.toString());
        Log.e("256: ", p4.toString());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();
        if (idItem == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static BigInteger largePrime(int bits) {
        Random randomInteger = new Random();
        BigInteger largePrime = BigInteger.probablePrime(bits, randomInteger);
        return largePrime;
    }
}

package com.example.kas_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigInteger;

import java.util.Objects;
import java.util.Random;

public class GenerateKeyAndSendKeyActivity extends AppCompatActivity {

    private TextView pTextView;
    private TextView qTextView;
    private TextView nTextViewPublic;
    private TextView eTextView;
    private TextView nTextViewPrivate;
    private TextView dTextView;
    private Button generateAllButton;
    private Button sendKeysButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_public_key);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pTextView = findViewById(R.id.generatedP);
        qTextView = findViewById(R.id.generatedQ);
        nTextViewPublic = findViewById(R.id.generatedNpublic);
        nTextViewPrivate = findViewById(R.id.generatedNprivate);
        eTextView = findViewById(R.id.generatedE);
        dTextView = findViewById(R.id.generatedD);

        generateAllButton = findViewById(R.id.generateAllButton);
        sendKeysButton = findViewById(R.id.sendbuttonGeneratedKeys);

        generateAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String primeNumberTextP = generatePrimeNumber(128).toString();
                pTextView.setText(primeNumberTextP);

                String primeNumberTextQ = generatePrimeNumber(128).toString();
                qTextView.setText(primeNumberTextQ);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();
        if (idItem == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private BigInteger generatePrimeNumber(int bits) {
        Random randomNumber = new Random();
        BigInteger primeNumber = BigInteger.probablePrime(bits, randomNumber);
        return primeNumber;
    }



}

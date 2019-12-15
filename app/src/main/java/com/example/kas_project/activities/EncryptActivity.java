package com.example.kas_project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kas_project.R;
import com.example.kas_project.utils.RSAGeneration;
import com.google.android.material.snackbar.Snackbar;

import java.math.BigInteger;
import java.util.Objects;

public class EncryptActivity extends AppCompatActivity {

    private EditText editTextTo;
    private EditText messageEditText;
    private EditText eEditText;
    private EditText nEditText;
    private TextView encrzptedMessage;
    private Button sendButton;
    private Button encryptMessageButton;

    private BigInteger cipherText;
    private BigInteger encryptedText;

    private BigInteger n;
    private BigInteger e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NestedScrollView view = (NestedScrollView) findViewById(R.id.nestedscrollviewEncrypt);
        view.setNestedScrollingEnabled(true);

        final RSAGeneration rsa = new RSAGeneration();

        editTextTo = findViewById(R.id.sendtoedittext);
        messageEditText = findViewById(R.id.messageedittext);
        eEditText = findViewById(R.id.e);
        nEditText = findViewById(R.id.n);

        encrzptedMessage = findViewById(R.id.messageedittextEncrypted);
        encryptMessageButton = findViewById(R.id.encryptMessageButton);

        sendButton = findViewById(R.id.sendbutton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextTo.getText().toString().matches("") ||
                        messageEditText.getText().toString().matches("") ||
                        eEditText.getText().toString().matches("") ||
                        encrzptedMessage.getText().toString().matches("") ||
                        nEditText.getText().toString().matches("")) {
                    Snackbar snackbar = Snackbar.make(view, "Some of parameters are not filled.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    sendEmail();
                }
            }
        });

        encryptMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (messageEditText.getText().toString().matches("") ||
                        eEditText.getText().toString().matches("") ||
                        nEditText.getText().toString().matches("")) {
                    Snackbar snackbar = Snackbar.make(view, "Some of parameters are not filled.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {

                    n = new BigInteger(nEditText.getText().toString());
                    e = new BigInteger(eEditText.getText().toString());

                    cipherText = rsa.stringMessageToAlphabetValue(messageEditText.getText().toString());
                    Log.e("Cipher TEXT", cipherText.toString());

                    encryptedText = rsa.encryptMessage(cipherText, e, n);
                    Log.e("Encrypted TEXT", encryptedText.toString());

                    encrzptedMessage.setText(encryptedText.toString());
                }
            }
        });
    }

    private void sendEmail() {
        String recipientList = editTextTo.getText().toString();
        String[] recipients = recipientList.split(",");
        String subject = "Encrypted message";
        String message = encrzptedMessage.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client:"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();
        if (idItem == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.kas_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class EncryptActivity extends AppCompatActivity {

    private EditText editTextTo;
    private EditText messageEditText;
    private EditText eEditText;
    private EditText nEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NestedScrollView view = (NestedScrollView) findViewById(R.id.nestedscrollviewEncrypt);
        view.setNestedScrollingEnabled(true);

        editTextTo = findViewById(R.id.sendtoedittext);
        messageEditText = findViewById(R.id.messageedittext);
        eEditText = findViewById(R.id.e);
        nEditText = findViewById(R.id.n);

        Button sendButton = findViewById(R.id.sendbutton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextTo.getText().toString().matches("") ||
                        messageEditText.getText().toString().matches("") ||
                        eEditText.getText().toString().matches("") ||
                        nEditText.getText().toString().matches("")) {
                    Snackbar snackbar = Snackbar.make(view, "Some of parameters are not filled.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    sendEmail();
                }
            }
        });
    }

    private void sendEmail() {
        String recipientList = editTextTo.getText().toString();
        String[] recipients = recipientList.split(",");
        String subject = "Encrypted message";
        String message = messageEditText.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client:"));
    }

    private static int findPositionOfLetterInAlphabet(char inputLetter) {
        int position;
        if (inputLetter == ' '){
            int asciiValueOfInputChar= (int)inputLetter;
            position = asciiValueOfInputChar-5;
        } else {
            char inputLetterToLowerCase= Character.toLowerCase(inputLetter);
            int asciiValueOfInputChar= (int)inputLetterToLowerCase;
            position = asciiValueOfInputChar-96;
        }
        return position;
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

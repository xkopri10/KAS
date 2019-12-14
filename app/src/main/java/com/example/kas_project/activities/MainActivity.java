package com.example.kas_project.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kas_project.R;

public class MainActivity extends AppCompatActivity {

    private Button generateKeysButton, encryptButton, aboutAppButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generateKeysButton = findViewById(R.id.generate_and_send_button);
        encryptButton = findViewById(R.id.encrypt_button);
        aboutAppButton = findViewById(R.id.about_app_button);

        generateKeysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGenerateKeyAndSendKeyActivity();
            }
        });
        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEncryptActivity();
            }
        });

        aboutAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAboutAppActivity();
            }
        });
    }

    private void openGenerateKeyAndSendKeyActivity() {
        Intent intent = new Intent(this, GenerateKeyAndSendKeyActivity.class);
        startActivity(intent);
    }

    private void openEncryptActivity() {
        Intent intent = new Intent(this, EncryptActivity.class);
        startActivity(intent);
    }

    private void openAboutAppActivity() {
        Intent intent = new Intent(this, AboutAppActivity.class);
        startActivity(intent);
    }
}

package com.example.kas_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button generateKeysButton, decryptButton, encryptButton, aboutAppButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generateKeysButton = findViewById(R.id.generate_and_send_button);
        decryptButton = findViewById(R.id.decrypt_button);
        encryptButton = findViewById(R.id.encrypt_button);
        aboutAppButton = findViewById(R.id.about_app_button);

        generateKeysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGenerateKeyAndSendKeyActivity();
            }
        });

        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDecryptActivity();
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

    private void openDecryptActivity() {
        Intent intent = new Intent(this, DecryptActivity.class);
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

package com.example.kas_project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.kas_project.R;

/**
 * Class for MainActivity logic
 */
public class MainActivity extends AppCompatActivity {

    private CardView generateKeysButton, encryptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NestedScrollView view = (NestedScrollView) findViewById(R.id.nestedscrollviewMain);
        view.setNestedScrollingEnabled(true);

        generateKeysButton = findViewById(R.id.generate_and_send_button);
        encryptButton = findViewById(R.id.encrypt_button);

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
    }

    /**
     * Method call when user click on Generation and Decryption section
     */
    private void openGenerateKeyAndSendKeyActivity() {
        Intent intent = new Intent(this, GenerateKeyAndSendKeyActivity.class);
        startActivity(intent);
    }

    /**
     * Method call when user click on Encryption section
     */
    private void openEncryptActivity() {
        Intent intent = new Intent(this, EncryptActivity.class);
        startActivity(intent);
    }

    /**
     * Method call when user click on Information Button Icon
     */
    public void onClickInformationButtonMenu(MenuItem item){
        Intent intent = new Intent(this,AboutAppActivity.class);
        startActivity(intent);
    }

    /**
     * Method for creating menu in this activity
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

}

package com.example.kas_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.os.Bundle;
import android.view.MenuItem;
import java.util.Objects;

public class EncryptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);

        NestedScrollView view = (NestedScrollView) findViewById(R.id.nestedscrollviewEncrypt);
        view.setNestedScrollingEnabled(true);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

package com.example.kas_project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.kas_project.R;
import com.example.kas_project.database.ProfileKeysDatabaseGetter;
import com.example.kas_project.models.ProfileKey;
import java.util.List;
import java.util.Objects;

/**
 * Activity for showing list of users in Dencryption section
 */
public class PeopleDecryptionListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_decryption);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rec_view);
        ProfileKeysDatabaseGetter db = new ProfileKeysDatabaseGetter();
        List<ProfileKey> profiles = db.getAllDecryptPeople();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PeopleDecryptionAdapter(profiles);
        recyclerView.setAdapter(adapter);
        recyclerView.invalidate();
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

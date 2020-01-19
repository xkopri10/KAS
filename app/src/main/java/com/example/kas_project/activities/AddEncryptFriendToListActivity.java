package com.example.kas_project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kas_project.R;
import com.example.kas_project.database.ProfileKeysDatabaseGetter;
import com.example.kas_project.models.ProfileKey;
import com.google.android.material.snackbar.Snackbar;

import java.math.BigInteger;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Class for adding people to the database in Encryption section
 */
public class AddEncryptFriendToListActivity extends AppCompatActivity {

    private EditText sendToEditText, parameterNEditText, parameterEEditText;
    private Button saveButton;

    private BigInteger n;
    private BigInteger e;

    /**
     * On Create Method
     * Here is logic for each button on the screen and references on the objects
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_encrypt_friend_to_list);

        Log.e("CREATE","");

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NestedScrollView view = (NestedScrollView) findViewById(R.id.nestedscrollviewAddPerson);
        view.setNestedScrollingEnabled(true);

        final Pattern regex = Pattern.compile("[-+*/#{}()a-zA-Z;,. ]");

        sendToEditText = findViewById(R.id.sendtoedittextAddPersonActivity);
        parameterEEditText = findViewById(R.id.parameterEEditTextAddActivity);
        parameterNEditText = findViewById(R.id.parameterNEditTextAddActivity);
        saveButton = findViewById(R.id.saveButtonAddPerson);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sendToEditText.getText().toString().matches("") ||
                        parameterEEditText.getText().toString().matches("") ||
                        parameterNEditText.getText().toString().matches("")) {
                    Snackbar snackbar = Snackbar.make(view, "Some of parameters are not filled.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (regex.matcher(parameterEEditText.getText().toString()).find()) {
                    Snackbar snackbar = Snackbar.make(view, "Parameter E contains illegal symbols", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (regex.matcher(parameterNEditText.getText().toString()).find()) {
                    Snackbar snackbar = Snackbar.make(view, "Parameter N contains illegal symbols", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    saveNewPerson();
                }
            }
        });
    }

    /**
     * Method for saving new person to the database
     * Need to be in Async task because of database
     */
    private void saveNewPerson() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                ProfileKeysDatabaseGetter db = new ProfileKeysDatabaseGetter();
                String email = sendToEditText.getText().toString();
                e = new BigInteger(parameterEEditText.getText().toString());
                n = new BigInteger(parameterNEditText.getText().toString());

                ProfileKey profileKey = new ProfileKey();
                profileKey.setEmail(email);
                profileKey.setEParameter(e.toString());
                profileKey.setNParameter(n.toString());
                profileKey.setPeopleForEncrypt(true);

                db.insert(profileKey);
            }
        });

        finish();
    }

    /**
     * Method for arrow back in Activity
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idItem = item.getItemId();
        if (idItem == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Life cycle methods for activity
     */

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("START","");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("STOP","");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("DESTROY","");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("PAUSE","");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("RESUME","");
    }
}

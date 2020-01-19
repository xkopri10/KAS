package com.example.kas_project.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kas_project.R;
import com.example.kas_project.database.ProfileKeysDatabaseGetter;
import com.example.kas_project.models.ProfileKey;
import com.example.kas_project.utils.AppUtils;
import com.example.kas_project.utils.RSAGeneration;
import com.google.android.material.snackbar.Snackbar;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class EncryptActivity extends AppCompatActivity {

    private EditText editTextTo;
    private EditText messageEditText;
    private EditText eEditText;
    private EditText nEditText;
    private TextView encrzptedMessage;
    private Button sendButton;
    private Button encryptMessageButton;
    private Button loadButton;

    private BigInteger cipherText;
    private BigInteger encryptedText;

    private BigInteger n;
    private BigInteger e;

    String[] listEmails;
    List<ProfileKey> listOfKeys;
    private String nParameter, eParameter, emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NestedScrollView view = (NestedScrollView) findViewById(R.id.nestedscrollviewEncrypt);
        view.setNestedScrollingEnabled(true);

        final RSAGeneration rsa = new RSAGeneration();
        final AppUtils utils = new AppUtils();

        final Pattern regexMessage = Pattern.compile("[{}|~]");
        final Pattern regex = Pattern.compile("[-+*/#{}()a-zA-Z;,. ]");

        editTextTo = findViewById(R.id.sendtoedittext);
        messageEditText = findViewById(R.id.messageedittext);
        eEditText = findViewById(R.id.e);
        nEditText = findViewById(R.id.n);

        encrzptedMessage = findViewById(R.id.messageedittextEncrypted);
        encryptMessageButton = findViewById(R.id.encryptMessageButton);
        loadButton = findViewById(R.id.loadParametersButtonEncrypt);


        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAndLoadParameters();
            }
        });

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
                } else if (regex.matcher(eEditText.getText().toString()).find()) {
                    Snackbar snackbar = Snackbar.make(view, "Parameter E contains illegal symbols", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (regex.matcher(nEditText.getText().toString()).find()) {
                    Snackbar snackbar = Snackbar.make(view, "Parameter N contains illegal symbols", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (!utils.isEmailValid(editTextTo.getText().toString())) {
                    Snackbar snackbar = Snackbar.make(view, "Email address is not valid.", Snackbar.LENGTH_LONG);
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
                } else if (regexMessage.matcher(messageEditText.getText().toString()).find()) {
                    Snackbar snackbar = Snackbar.make(view, "Message contain illegal symbols: {,},|,~", Snackbar.LENGTH_LONG);
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

    private void showDialogAndLoadParameters() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose communication person");
        builder.setItems(listEmails, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, final int which) {
                setData(which);
                editTextTo.setText(emailEditText);
                eEditText.setText(eParameter);
                nEditText.setText(nParameter);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void getData() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                ProfileKeysDatabaseGetter db = new ProfileKeysDatabaseGetter();
                listOfKeys = db.getAllEncryptPeople();
                listEmails = new String[listOfKeys.size()];
                for (int i=0; i<listOfKeys.size(); i++ ){
                    listEmails[i] = listOfKeys.get(i).getEmail();
                }
            }
        });
    }

    private void setData(final int index) {
        emailEditText = listOfKeys.get(index).getEmail();
        nParameter = listOfKeys.get(index).getNParameter();
        eParameter = listOfKeys.get(index).getEParameter();
    }

    public void onClickAddPeopleActivity(MenuItem item){
        Intent intent = new Intent(this, AddEncryptFriendToListActivity.class);
        startActivity(intent);
    }

    public void onClickPeopleListActivity(MenuItem item){
        Intent intent = new Intent(this, PeopleEncryprionListActivity.class);
        startActivity(intent);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu,menu);
        return true;
    }

    @Override
    protected void onStart() {
        Log.e("START","aaaaa");
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("STOP","aaaaa");
    }

    @Override
    protected void onDestroy() {
        Log.e("DESTROY","aaaaa");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.e("PAUSE","aaaaa");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.e("RESUME","aaaaa");
        super.onResume();
        getData();
    }
}

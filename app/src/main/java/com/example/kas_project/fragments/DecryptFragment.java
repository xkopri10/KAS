package com.example.kas_project.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kas_project.R;
import com.example.kas_project.activities.AboutAppActivity;
import com.example.kas_project.database.ProfileKeysDatabaseGetter;
import com.example.kas_project.models.ProfileKey;
import com.example.kas_project.utils.RSAGeneration;
import com.google.android.material.snackbar.Snackbar;

import java.math.BigInteger;
import java.util.List;
import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class DecryptFragment extends Fragment {

    final RSAGeneration rsa = new RSAGeneration();

    private EditText nEditText;
    private EditText dEditText;
    private TextView decryptedMessageEditText;
    private EditText encryptedMessageEditText;
    private Button decryptMessageButton, loadButton;

    private BigInteger decryptedText;
    private String messageBackToString;

    private BigInteger n;
    private BigInteger d;
    private BigInteger encryptedMessage;

    private String nParameter, dParameter;

    public DecryptFragment() {
        // Required empty public constructor
    }

    String[] listEmails;
    List<ProfileKey> listOfKeys;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_decrypt, container, false);

        nEditText = rootview.findViewById(R.id.privateKeyNEditText);
        dEditText = rootview.findViewById(R.id.privateKeyDEditText);
        decryptedMessageEditText = rootview.findViewById(R.id.decryptedMessage);
        encryptedMessageEditText = rootview.findViewById(R.id.encryptedMessage);
        decryptMessageButton = rootview.findViewById(R.id.decryptMessageButton);
        loadButton = rootview.findViewById(R.id.loadParametersButton);

        decryptMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pattern regex = Pattern.compile("[-+*/#{}()a-zA-Z;,. ]");

                if (dEditText.getText().toString().matches("") ||
                        nEditText.getText().toString().matches("") ||
                        encryptedMessageEditText.getText().toString().matches("")) {
                    Snackbar snackbar = Snackbar.make(view, "Some of parameters are not filled.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (regex.matcher(dEditText.getText().toString()).find()) {
                    Snackbar snackbar = Snackbar.make(view, "Parameter D contains illegal symbols", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (regex.matcher(nEditText.getText().toString()).find()) {
                        Snackbar snackbar = Snackbar.make(view, "Parameter N contains illegal symbols", Snackbar.LENGTH_LONG);
                        snackbar.show();
                } else if (regex.matcher(encryptedMessageEditText.getText().toString()).find()) {
                    Snackbar snackbar = Snackbar.make(view, "Message contains illegal symbols", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else {
                    d = new BigInteger(dEditText.getText().toString());
                    n = new BigInteger(nEditText.getText().toString());
                    encryptedMessage = new BigInteger(encryptedMessageEditText.getText().toString());

                    decryptedText = rsa.decryptMessage(encryptedMessage, d, n);
                    Log.e("Decrypted TEXT", decryptedText.toString());

                    messageBackToString = rsa.convertBigIntegerMessageBackToString(decryptedText);
                    Log.e("Message back to TEXT", messageBackToString);

                    decryptedMessageEditText.setText(messageBackToString);
                }
            }
        });

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAndLoadParameters();
            }
        });

        return rootview;
    }

    private void showDialogAndLoadParameters() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose communication person");
        builder.setItems(listEmails, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, final int which) {
                setData(which);
                dEditText.setText(dParameter);
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
                listOfKeys = db.getAllDecryptPeople();
                listEmails = new String[listOfKeys.size()];
                for (int i=0; i<listOfKeys.size(); i++ ){
                    listEmails[i] = listOfKeys.get(i).getEmail();
                }
            }
        });
    }

    private void setData(final int index) {
        nParameter = listOfKeys.get(index).getNParameter();
        dParameter = listOfKeys.get(index).getDParameter();
    }

    @Override
    public void onResume() {
        Log.e("RESUME","aaaaa");
        super.onResume();
        getData();
    }
}

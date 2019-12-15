package com.example.kas_project.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kas_project.R;
import com.example.kas_project.utils.RSAGeneration;
import com.google.android.material.snackbar.Snackbar;

import java.math.BigInteger;


/**
 * A simple {@link Fragment} subclass.
 */
public class DecryptFragment extends Fragment {

    final RSAGeneration rsa = new RSAGeneration();

    private EditText nEditText;
    private EditText dEditText;
    private TextView decryptedMessageEditText;
    private EditText encryptedMessageEditText;
    private Button decryptMessageButton;

    private BigInteger decryptedText;
    private String messageBackToString;

    private BigInteger n;
    private BigInteger d;
    private BigInteger encryptedMessage;

    public DecryptFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_decrypt, container, false);

        nEditText = rootview.findViewById(R.id.privateKeyNEditText);
        dEditText = rootview.findViewById(R.id.privateKeyDEditText);
        decryptedMessageEditText = rootview.findViewById(R.id.decryptedMessage);
        encryptedMessageEditText = rootview.findViewById(R.id.encryptedMessage);
        decryptMessageButton = rootview.findViewById(R.id.decryptMessageButton);

        decryptMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (dEditText.getText().toString().matches("") ||
                        nEditText.getText().toString().matches("") ||
                        encryptedMessageEditText.toString().matches("")) {
                    Snackbar snackbar = Snackbar.make(view, "Some of parameters are not filled.", Snackbar.LENGTH_LONG);
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

        return rootview;
    }

}

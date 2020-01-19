package com.example.kas_project.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class GenerationFragment extends Fragment {

    private TextView pTextView;
    private TextView qTextView;
    private TextView nTextViewPublic;
    private TextView eTextView;
    private TextView nTextViewPrivate;
    private TextView dTextView;
    private Button generateAllButton, sendKeysButton, saveParametersButton;
    private EditText emailToEditText;


    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger phiN;
    private BigInteger e;
    private BigInteger d;

    private Boolean generated;

    public GenerationFragment() {
        // Required empty public constructor
    }

    /**
     * On Create method
     * Here is logic for each button on the screen and references on the objects
     * @return view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_generation, container, false);

        final RSAGeneration rsa = new RSAGeneration();
        final AppUtils utils = new AppUtils();

        pTextView = rootView.findViewById(R.id.generatedP);
        qTextView = rootView.findViewById(R.id.generatedQ);
        nTextViewPublic = rootView.findViewById(R.id.generatedNpublic);
        nTextViewPrivate = rootView.findViewById(R.id.generatedNprivate);
        eTextView = rootView.findViewById(R.id.generatedE);
        dTextView = rootView.findViewById(R.id.generatedD);
        emailToEditText = rootView.findViewById(R.id.sendtoedittextGenerateKeys);

        generateAllButton = rootView.findViewById(R.id.generateAllButton);
        saveParametersButton = rootView.findViewById(R.id.saveParametersButton);
        sendKeysButton = rootView.findViewById(R.id.sendbuttonGeneratedKeys);

        generated = false;

        generateAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generated = true;
                do {
                    p = rsa.generatePrimeNumber(256);
                } while (!p.isProbablePrime(1));
                String primeNumberTextP = p.toString();
                pTextView.setText(primeNumberTextP);

                do {
                    q = rsa.generatePrimeNumber(256);
                } while (!q.isProbablePrime(1));
                String primeNumberTextQ = q.toString();
                qTextView.setText(primeNumberTextQ);

                n = rsa.calculateN(p, q);
                String nTextN = n.toString();
                nTextViewPrivate.setText(nTextN);
                nTextViewPublic.setText(nTextN);

                phiN = rsa.calculatePhiN(p, q);
                Log.e("PhiN", phiN.toString());

                e = rsa.calculateE(phiN);
                String eTextE = e.toString();
                eTextView.setText(eTextE);
                Log.e("e", eTextE);

                // tested on these values from practical classes (in School - přednáška na Asymetrické šifrování - RSA)
                //d = rsa.calculateD(BigInteger.valueOf(17), BigInteger.valueOf(3233));
                d = rsa.calculateD(e, phiN);
                String dTextD = d.toString();
                dTextView.setText(dTextD);
            }
        });

        saveParametersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!generated || emailToEditText.getText().toString().matches("")) {
                    Snackbar snackbar = Snackbar.make(view, "Some of parameters are not filled.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (p.equals(q)){
                    Snackbar snackbar = Snackbar.make(view, "P = Q. Generate keys again.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (utils.isEmailValid(emailToEditText.getText().toString())){
                    saveParameters();
                    showAlertDialog();
                } else {
                    Snackbar snackbar = Snackbar.make(view, "Email address is not valid", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

        sendKeysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!generated || emailToEditText.getText().toString().matches("")) {
                    Snackbar snackbar = Snackbar.make(view, "Some of parameters are not filled.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (p.equals(q)){
                    Snackbar snackbar = Snackbar.make(view, "P = Q. Generate keys again.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                } else if (utils.isEmailValid(emailToEditText.getText().toString())){
                    sendEmail();
                } else {
                    Snackbar snackbar = Snackbar.make(view, "Email address is not valid", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
        return rootView;
    }


    /**
     * Method preparing format of message which is send by email client to another user
     * @return this format
     */
    private String prepareKeysForSend() {
        return "Parameter n:\n" + nTextViewPublic.getText().toString() + "\n\n" + "Parameter e:\n" + eTextView.getText().toString();
    }

    /**
     * Method for showing AlertDialog when user save some parameters to database
     */
    private void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Saved");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Method for invoke email client
     * setting parameters to transmitted data with intent
     */
    private void sendEmail() {
        String recipientList = emailToEditText.getText().toString();
        String[] recipients = recipientList.split(",");
        String subject = "Public Key";
        String message = prepareKeysForSend();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client:"));
    }

    /**
     * Method for saving data to database
     */
    private void saveParameters() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                ProfileKeysDatabaseGetter db = new ProfileKeysDatabaseGetter();
                String email = emailToEditText.getText().toString();

                ProfileKey profileKey = new ProfileKey();
                profileKey.setEmail(email);
                profileKey.setPParameter(p.toString());
                profileKey.setQParameter(q.toString());
                profileKey.setNParameter(n.toString());
                profileKey.setEParameter(e.toString());
                profileKey.setDParameter(d.toString());
                profileKey.setPeopleForDecrypt(true);

                db.insert(profileKey);
            }
        });
    }

}

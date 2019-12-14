package com.example.kas_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;

import java.util.Objects;
import java.util.Random;

public class GenerateKeyAndSendKeyActivity extends AppCompatActivity {

    private TextView pTextView;
    private TextView qTextView;
    private TextView nTextViewPublic;
    private TextView eTextView;
    private TextView nTextViewPrivate;
    private TextView dTextView;
    private Button generateAllButton;
    private Button sendKeysButton;
    private EditText emailToEditText;


    private BigInteger p;
    private BigInteger q;
    private BigInteger n;
    private BigInteger phiN;
    private BigInteger e;
    private BigInteger d;

    private BigInteger cipherText;
    private BigInteger encryptedText;
    private BigInteger decryptedText;
    private String messageBackToString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_public_key);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pTextView = findViewById(R.id.generatedP);
        qTextView = findViewById(R.id.generatedQ);
        nTextViewPublic = findViewById(R.id.generatedNpublic);
        nTextViewPrivate = findViewById(R.id.generatedNprivate);
        eTextView = findViewById(R.id.generatedE);
        dTextView = findViewById(R.id.generatedD);
        emailToEditText = findViewById(R.id.sendtoedittextGenerateKeys);

        generateAllButton = findViewById(R.id.generateAllButton);
        sendKeysButton = findViewById(R.id.sendbuttonGeneratedKeys);

        generateAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p = generatePrimeNumber(128);
                String primeNumberTextP = p.toString();
                pTextView.setText(primeNumberTextP);

                q = generatePrimeNumber(128);
                String primeNumberTextQ = q.toString();
                qTextView.setText(primeNumberTextQ);

                n = calculateN(p, q);
                String nTextN = n.toString();
                nTextViewPrivate.setText(nTextN);
                nTextViewPublic.setText(nTextN);

                phiN = calculateEulerFunctionPhi(p, q);
                Log.e("PhiN", phiN.toString());

                e = calculateE(phiN);
                String eTextE = e.toString();
                eTextView.setText(eTextE);
                Log.e("e", eTextE);

                d = calculateDWithExtEuclideanAlgorihm(e, phiN)[1];
                String dTextD = d.toString();
                dTextView.setText(dTextD);

                cipherText = stringMessageToAlphabetValue("nevim co se stalo ale je to dost na ho");
                Log.e("Cipher TEXT", cipherText.toString());

                encryptedText = encryptMessage(cipherText, e, n);
                Log.e("Encrypted TEXT", encryptedText.toString());

                decryptedText = decryptMessage(encryptedText, d, n);
                Log.e("Decrypted TEXT", decryptedText.toString());

                messageBackToString = convertBigIntegerMessageBackToString(decryptedText);
                Log.e("Message back to TEXT", messageBackToString);
            }
        });

        sendKeysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });

    }

    private String prepareKeysForSend() {
        return "Parameter n:\n" + nTextViewPublic.getText().toString() + "\n\n" + "Parameter e:\n" + eTextView.getText().toString();
    }


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
     * Method for encryption message.
     * @param message = original message
     * @param e = parameter E (part of public key)
     * @param n = parameter N (part of public key
     * @return - modPow = message(on exponent - e) mod n
     */
    private BigInteger encryptMessage(BigInteger message, BigInteger e, BigInteger n) {
        return message.modPow(e, n);
    }

    /**
     * Method for decryption message.s
     * @param message = encrypted message
     * @param d = parameter D (part of private key)
     * @param n = parameter N (part of private key)
     * @return - modPow = message(on exponent d) mod n
     */
    private BigInteger decryptMessage(BigInteger message, BigInteger d, BigInteger n) {
        return message.modPow(d, n);
    }

    /**
     * Method for converting Text Message (original) to BigInteger number for encrypting.
     * @param message = message in String - message before encrypting
     * @return : BigInteger number where every 2 numbers correspond to decimal letter code in ASCII
     */
    private BigInteger stringMessageToAlphabetValue(String message) {
        message = message.toUpperCase();
        String cipherString = "";
        int i = 0;
        while (i < message.length()) {
            int character = (int) message.charAt(i);
            cipherString = cipherString + character;
            i++;
        }
        return new BigInteger(cipherString);
    }

    /**
     * Method for converting number to letter helped with ASCII
     * every 2 numbers correspond to decimal letter code in ASCII (that"s why i+2)
     * NOT ALLOWED characters like: {,},|,~. Because their code is 3 three-digit
     *
     * @param message = message is in BigInteger number
     * @return : text decrypted message
     */
    private String convertBigIntegerMessageBackToString(BigInteger message) {
        String cipherString = message.toString();
        int bla = cipherString.length();
        String output = "";
        Log.e("LENGTH of decrzpted message", String.valueOf(bla));
        int i = 0;
        while (i < (cipherString.length())) {
            int asciiNumberOfLetter = Integer.parseInt(cipherString.substring(i, i+2));
            char character = (char) asciiNumberOfLetter;
            output = output + character;
            i = i + 2;
        }
        return output;
    }

    /**
     * Method for calculate parameter N - needed for public and private key
     * @param p = prime number P
     * @param q = prime number Q
     * @return - multiply = is like p * q
     */
    private BigInteger calculateN(BigInteger p, BigInteger q) {
        return p.multiply(q);
    }

    /**
     * Method for calculate Euler function Phi
     * @param p = prime number P
     * @param q = prime number Q
     * @return - subtract = calculate difference between p and 1 (n - 1) and (q - 1). Then the numbers are multiplied like (n - 1)*(q - 1)
     */
    private BigInteger calculateEulerFunctionPhi(BigInteger p, BigInteger q) {
        return (p.subtract(BigInteger.ONE)).multiply((q.subtract(BigInteger.ONE)));
    }

    /**
     * Recursive method for calculating GCD (greatest common divisor) = used in method calculateE
     * @param number1 = will be E
     * @param number2 = will be phiN
     * @return number which is divided by another number. What I want is to get number 1 -> it means that these 2 numbers has common divisor 1
     */
    private BigInteger calculateGreatestCommonDivisor(BigInteger number1, BigInteger number2) {
        if (number2.equals(BigInteger.ZERO)) {
            return number1;
        } else {
            return calculateGreatestCommonDivisor(number2, number1.mod(number2));
        }
    }

    /**
     * Method for calculating E (part of public key) by finding Phi (which they are equals to 1)
     * e = 256 bits because is comparing with Phi (it has 256b) with new random value.
     * but it can be 128b too
     * @param phiN = value of (n - 1)*(q - 1)
     * @return -
     */
    private BigInteger calculateE(BigInteger phiN) {
        Random random = new Random();
        BigInteger e;
        do {
            e = new BigInteger(256, random);                                /* generate new bigInteger value - over the range 0 to (2^numBits – 1) */
            while (e.min(phiN).equals(phiN)) {
                e = new BigInteger(256, random);                            /* while phi is smaller than e ---> generate new E*/
            }
        } while (!calculateGreatestCommonDivisor(e, phiN).equals(BigInteger.ONE));  /* Do that UNTIL GCD is 1 */
        return e;
    }

    /**
     * Generate prime number about size bits in parameter
     * @param bits = 128/256/512/1024 and so on
     * @return prime number
     */
    private BigInteger generatePrimeNumber(int bits) {
        Random randomNumber = new Random();
        return BigInteger.probablePrime(bits, randomNumber);
    }

    /**
     * Method for calculate parameter D (part of private key)
     * For calculation D I was inspired here: https://crypto.stackexchange.com/questions/5889/calculating-rsa-private-exponent-when-given-public-exponent-and-the-modulus-fact
     * Method calculate D with help Extended Euclidean algorithm (ran backwards than normal)
     * Mathematical expression is: ax + by = gcd(a,b) where a = e and b = phiN - we need it for to solve: ex ≡ 1 (mod φ(n)) where x = d
     *
     * @param e = e
     * @param phi = phi
     * @return : [d, p, q] where d = gcd(a,b) and ap + bq = d
     */
    private BigInteger[] calculateDWithExtEuclideanAlgorihm(BigInteger e, BigInteger phi) {
        if (phi.equals(BigInteger.ZERO)) {
            return new BigInteger[] {
                    e, BigInteger.ONE, BigInteger.ZERO
            };
        }
        BigInteger[] values = calculateDWithExtEuclideanAlgorihm(phi, e.mod(phi));
        return new BigInteger[] {
                values[0],                                                  // d
                values[2],                                                  // p
                values[1].subtract(e.divide(phi).multiply(values[2]))};     // q
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

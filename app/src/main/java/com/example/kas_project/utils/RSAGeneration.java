package com.example.kas_project.utils;

import android.util.Log;
import java.math.BigInteger;
import java.util.Random;

public final class RSAGeneration {

    /**
     * Method for encryption message.
     * @param message = original message
     * @param e = parameter E (part of public key)
     * @param n = parameter N (part of public key
     * @return - modPow = message(on exponent e) mod n
     */
    public BigInteger encryptMessage(BigInteger message, BigInteger e, BigInteger n) {
        return message.modPow(e, n);
    }

    /**
     * Method for decryption message.s
     * @param message = encrypted message
     * @param d = parameter D (part of private key)
     * @param n = parameter N (part of private key)
     * @return - modPow = message(on exponent d) mod n
     */
    public BigInteger decryptMessage(BigInteger message, BigInteger d, BigInteger n) {
        return message.modPow(d, n);
    }

    /**
     * Method for converting Text Message (original) to BigInteger number for encrypting.
     * @param message = message in String - message before encrypting
     * @return : BigInteger number where every 2 numbers correspond to decimal letter code in ASCII
     */
    public BigInteger stringMessageToAlphabetValue(String message) {
        // all letters in message are transform to UPPER CASE
        message = message.toUpperCase();
        String cipherString = "";
        int i = 0;
        while (i < message.length()) {
            // each letter is transform to ASCII code (integer)
            int characterInInteger = (int) message.charAt(i);
            cipherString = cipherString + characterInInteger;
            i++;
        }
        // return BigInteger number - BigInteger(text) - returns BigInteger number
        return new BigInteger(cipherString);
    }

    /**
     * Method for converting number to letter helped with ASCII
     * every 2 numbers correspond to decimal letter code in ASCII (that"s why i+2)
     * NOT ALLOWED characters like: {,},|,~. Because their code is 3 three-digit - this is checked in code
     *
     * @param message = message is in BigInteger number
     * @return : text decrypted message
     */
    public String convertBigIntegerMessageBackToString(BigInteger message) {
        String cipherString = message.toString();
        int cipherLength = cipherString.length();

        String output = "";
        Log.e("LENGTH of decrypted message", String.valueOf(cipherLength));
        int i = 0;

        try {
            while (i < (cipherString.length())) {
                // first I get 2 numbers (represent one letter) ... substring give me numbers from interval (from i to i+2)
                int asciiNumberOfLetter = Integer.parseInt(cipherString.substring(i, i+2));
                // ASCII number transform to letter
                char character = (char) asciiNumberOfLetter;
                // to output add letter
                output = output + character;
                // increase index
                i = i + 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return STRING message
        return output;
    }

    /**
     * Method for check if length of text is even or not
     * @param textLength = length of text
     * @return true/false (true = is even / false = it is not even - it is odd)
     */
    private Boolean checkEvenNumber(int textLength) {
        return (textLength % 2 == 0);
    }

    /**
     * Method for calculate parameter N - needed for public and private key
     * @param p = prime number P
     * @param q = prime number Q
     * @return - multiply = is like p * q
     */
    public BigInteger calculateN(BigInteger p, BigInteger q) {
        return p.multiply(q);
    }

    /**
     * Method for calculate Euler function Phi
     * @param p = prime number P
     * @param q = prime number Q
     * @return - subtract = calculate difference between p and 1 (p - 1) and (q - 1). Then the numbers are multiplied like (n - 1)*(q - 1)
     */
    public BigInteger calculatePhiN(BigInteger p, BigInteger q) {
        return (p.subtract(BigInteger.ONE)).multiply((q.subtract(BigInteger.ONE)));
    }

    /**
     * Recursive method for calculating GCD (greatest common divisor - největší společný dělitel) = used in method calculateE
     * @param number1 = will be E
     * @param number2 = will be phiN
     * @return number which is divided by another number. What I want is to get number 1 -> it means that these 2 numbers has common divisor 1
     */
    public BigInteger calculateGCD(BigInteger number1, BigInteger number2) {
        if (number2.equals(BigInteger.ZERO)) {
            return number1;
        } else {
            return calculateGCD(number2, number1.mod(number2));
        }
    }

    /**
     * Method for calculating E (part of public key) by finding PhiN
     * @param phiN = Euler function = value of (n - 1)*(q - 1)
     * @return - E
     */
    public BigInteger calculateE(BigInteger phiN) {
        // generating some random number
        Random random = new Random();
        BigInteger e;

        do {
            // to variable E is set new random BigInteger value with length 128 bits
            // because it has be smaller than Euler function (phiN)
            e = new BigInteger(128, random);

            // comparing E number with phiN (Euler function)
            while (e.min(phiN).equals(phiN)) {

                // (min give me minimum from these 2 values) if minimum from e or phiN is equal phiN then generate new E
                e = new BigInteger(128, random);
            }

            // this algorithm is repeated until the greatest common divisor is equal to 1 - if its not - E is found
            // E musí být nesoudělné s phiN
        } while (!calculateGCD(e, phiN).equals(BigInteger.ONE));

        // return BigInteger number E
        return e;
    }

    /**
     * Generate prime number about size bits in parameter
     * @param bits = 256/512/1024 and so on
     * @return prime number
     */
    public BigInteger generatePrimeNumber(int bits) {
        Random randomNumber = new Random();
        // return me prime number from random number and length (bits) of number
        return BigInteger.probablePrime(bits, randomNumber);
    }

    /**
     * Method for calculating D parameter - it use extended Euclidean algorithm
     * @param e - BigInteger number
     * @param n - BigInteger number
     * @return  - D parameter
     */
    //calculate multiplicative inverse of a%n using the extended euclidean GCD algorithm
    public BigInteger calculateD(BigInteger e, BigInteger n){

        // call extendedEuclideanAlgorithm
        BigInteger [] ans = extendedEuclid(e, n);

        // výsledek by měl být větší jak 0 (neměl by být záporný)
        // result has to be greater than 0
        if (ans[1].compareTo(BigInteger.ZERO) > 0) {
            return ans[1];
        } else {
            return ans[1].add(n);
        }
    }

    // Calculate d = calculateD(a,N) = ex + ny
    // Recursion calling of method
    private static BigInteger [] extendedEuclid (BigInteger e, BigInteger N){
        //vytvorim si pole mezivýsledků o velikosti 3 prvků
        BigInteger [] betweenResult = new BigInteger[3];
        // two helped variables
        BigInteger ex, ny;

        // provede se pouze v posledním zanoření (neboli v nejzanorenejsim kroku celé rekurze)
        if (N.equals(BigInteger.ZERO)) {
            betweenResult[0] = e;
            betweenResult[1] = BigInteger.ONE;
            betweenResult[2] = BigInteger.ZERO;
            return betweenResult;
        }

        // rekurzivne volana funkce - zanori nas až do kroku kdy se N bude rovnat 0
        betweenResult = extendedEuclid (N, e.mod(N));

        ex = betweenResult[1];
        ny = betweenResult[2];

        betweenResult[1] = ny;
        BigInteger temp = e.divide(N);
        temp = ny.multiply(temp);
        betweenResult[2] = ex.subtract(temp);

        return betweenResult;
    }
}

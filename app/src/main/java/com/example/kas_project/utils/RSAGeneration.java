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
     * @return - modPow = message(on exponent - e) mod n
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
    public String convertBigIntegerMessageBackToString(BigInteger message) {
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
    public BigInteger calculateN(BigInteger p, BigInteger q) {
        return p.multiply(q);
    }

    /**
     * Method for calculate Euler function Phi
     * @param p = prime number P
     * @param q = prime number Q
     * @return - subtract = calculate difference between p and 1 (n - 1) and (q - 1). Then the numbers are multiplied like (n - 1)*(q - 1)
     */
    public BigInteger calculateEulerFunctionPhi(BigInteger p, BigInteger q) {
        return (p.subtract(BigInteger.ONE)).multiply((q.subtract(BigInteger.ONE)));
    }

    /**
     * Recursive method for calculating GCD (greatest common divisor) = used in method calculateE
     * @param number1 = will be E
     * @param number2 = will be phiN
     * @return number which is divided by another number. What I want is to get number 1 -> it means that these 2 numbers has common divisor 1
     */
    public BigInteger calculateGreatestCommonDivisor(BigInteger number1, BigInteger number2) {
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
    public BigInteger calculateE(BigInteger phiN) {
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
    public BigInteger generatePrimeNumber(int bits) {
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
    public BigInteger[] calculateDWithExtEuclideanAlgorihm(BigInteger e, BigInteger phi) {
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
}

package com.example.kas_project.utils;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AppUtils {

    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

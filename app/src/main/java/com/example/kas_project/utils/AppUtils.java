package com.example.kas_project.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utils for application
 */
public class AppUtils {

    /**
     * Method for checking right format of email
     * @param email - string email
     * @return TRUE or FALSE - if is valid or not
     */
    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

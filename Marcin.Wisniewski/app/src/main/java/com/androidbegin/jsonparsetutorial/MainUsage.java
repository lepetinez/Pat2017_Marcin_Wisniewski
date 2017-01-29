package com.androidbegin.jsonparsetutorial;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MainUsage {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}";

    static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }

    static boolean validatePassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}

package com.example.kotshare.utils;

public class Validator
{
    private Validator()
    {

    }

    public static boolean isValidEmail(String email)
    {
        return email.matches("\\w+\\@\\w+\\.\\w{2,}");
    }

    public static boolean isValidPassword(String password)
    {
        return password.length() >= 8;
    }
}

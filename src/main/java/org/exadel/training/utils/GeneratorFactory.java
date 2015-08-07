package org.exadel.training.utils;

import java.util.Random;

public final class GeneratorFactory {
    private static char[] symbols;

    private static char[] symbolsAndDigits;

    private static final char EMAIL_DOG = '@';

    static {
        StringBuilder sb = new StringBuilder();
        for (char ch = 'a'; ch <= 'z'; ch++) {
            sb.append(ch);
        }
        symbols = sb.toString().toCharArray();
        for (int i = 0; i < 10; i++) {
            sb.append(i);
        }
        symbolsAndDigits = sb.toString().toCharArray();
    }

    public static String generateFirstLogin(String firstName, String lastName) {
        StringBuilder sb = new StringBuilder();
        sb.append(firstName.charAt(0)).append(lastName);
        return sb.toString();
    }

    public static String generateSecondLogin(String firstName, String lastName) {
        StringBuilder sb = new StringBuilder();
        sb.append(firstName).append(lastName.charAt(0));
        return sb.toString();
    }

    public static String generateLoginFromEmail(String email) {
        return email.substring(0, email.indexOf(EMAIL_DOG));
    }

    public static String generateRandomLogin(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < length; i++) {
            sb.append(symbols[random.nextInt(symbols.length)]);
        }
        return sb.toString();
    }

    public static String generatePassword(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < length; i++) {
            sb.append(symbolsAndDigits[random.nextInt(symbolsAndDigits.length)]);
        }
        return sb.toString();
    }
}

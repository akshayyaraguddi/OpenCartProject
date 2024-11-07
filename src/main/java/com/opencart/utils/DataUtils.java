package com.opencart.utils;

import java.util.Random;

public class DataUtils {
	// Declared once, shared across all methods and instances, and cannot be re-assigned.
    private static final Random random = new Random();

    // Generic method for random character generation
    private String generateRandomCharacters(String characters, int length) {
        StringBuilder result = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            result.append(characters.charAt(random.nextInt(characters.length())));
        }
        return result.toString();
    }

    // Method to generate random alphabetic string
    public String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        return generateRandomCharacters(characters, length);
    }

    // Method to generate random numeric string
    public String generateRandomNumericString(int length) {
        String digits = "0123456789";
        return generateRandomCharacters(digits, length);
    }

    // Method to generate random password combining alphabetic and numeric strings
    public String generateRandomPassword() {
        String generatedString = generateRandomString(4);
        String generatedNumber = generateRandomNumericString(3);
        return generatedString + "@" + generatedNumber;
    }
}

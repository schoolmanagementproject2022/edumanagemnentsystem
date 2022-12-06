package com.epam.edumanagementsystem.util;

import org.springframework.ui.Model;

public class InputFieldsValidation {
    private static final Character[] list = {'!', '#', '@', '$', '%', '^', '&', '+', '=', '\'', '/', '?', ';', '.', '~', '[', ']', '{', '}', '"'};

    public static boolean checkingForIllegalCharacters(String verifiableName, Model model) {
        for (Character character : list) {
            if (verifiableName.contains(character.toString())) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateInputFieldSize(String inputField) {
        if (!inputField.isBlank()) {
            if (inputField.length() > 50) {
                return true;
            }
        }
        return false;
    }
}

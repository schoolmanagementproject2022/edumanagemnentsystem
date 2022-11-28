package com.epam.edumanagementsystem.util;

import org.springframework.ui.Model;

public class IllegalCharactersValidation {
    private static final Character[] list = {'!', '#', '@', '$', '%', '^', '&', '+', '=', '\'', '/', '?', ';', '.', '~', '[', ']', '{', '}', '"'};

    public static boolean checkingForIllegalCharacters(String verifiableName, Model model) {
        for (Character character : list) {
            if (verifiableName.contains(character.toString())) {
                model.addAttribute("invalidURL", "<>-_`*,:|() symbols can be used.");
                return true;
            }
        }
        return false;
    }
}

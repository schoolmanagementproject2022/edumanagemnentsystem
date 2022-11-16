package com.epam.edumanagementsystem.util;

import org.springframework.ui.Model;

public class EmailValidation {

    private static final String PATTERN_MAIL
            = "^([^! #$%&*+\\-/=?^_`{|\\.](?!.*[!#$%&*+-/=?^_`{|]{2})[a-zA-Z0-9!#$%&*+-/=?^_`{|]{0,50}[^!#$% &*+\\-/=?^_`{|\\.@]+)@([a-zA-Z0-9\\-]{1,50})\\.([a-z]{2,50})$";

    public static void validate(String email, Model model) {
        if (!email.isBlank()) {
            if (!email.matches(PATTERN_MAIL) || email.contains(" ")) {
                model.addAttribute("invalidEmail", "Email is invalid");
            }
        }
    }
}
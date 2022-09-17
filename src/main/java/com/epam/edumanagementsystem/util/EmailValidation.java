package com.epam.edumanagementsystem.util;

public class EmailValidation {

    private static final String FULL_REGEX =
            "^([A-Za-z0-9_\\-.!#$%&'*+-/=?^`{|]{0,30})(@)([a-zA-Z0-9_\\-.]{0,10})(.)([a-zA-Z_\\-.]{0,10})$";
    private static final String FIRST_SPLIT
            = "^([A-Za-z0-9_\\-.!#$%&'*+-/=?^`{|]{0,30})$";
    private static final String SECOND_SPLIT
            = "([a-zA-Z0-9_\\-.]{0,10})$";
    private static final String THIRD_SPLIT
            = "([a-zA-Z_\\-.]{0,10})$";
    private static final String LAST_FIRST
            = "([a-zA-Z]{0,2})$";
    private static final String DOT
            = "([a-zA-Z0-9_\\-.]{0,10})(.)([a-zA-Z_\\-.]{0,10})$";


    public static boolean validate(String email) {
        if (email.matches(FULL_REGEX)) {
            String[] splitFirstStep = email.split("@");
            String firstPart = splitFirstStep[0];
            String[] split = firstPart.split("");
            String splitSecondStep = splitFirstStep[1];
            if (splitSecondStep.matches(DOT)) {
                String[] splitThirdStep = splitSecondStep.split("\\.");
                String secondPart = splitThirdStep[0];

                String thirdPart = splitThirdStep[1];
                if (firstPart.matches(FIRST_SPLIT)) {
                    int count = 0;
                    for (int i = 0; i < firstPart.length(); i++) {
                        char[] JavaCharArray = {'!', '\'', '`', '.', '#', '$', '%', '&', '*', '+', '-', '/', '=', '?', '^', '{', '|'};
                        for (char c : JavaCharArray)
                            if (firstPart.charAt(i) == c) {
                                count++;
                            }
                    }
                    if (count > 1) {
                        return false;
                    }
                    if (!split[0].matches(LAST_FIRST)) {
                        return false;
                    }
                    if (!split[split.length - 1].matches(LAST_FIRST)) {
                        return false;
                    }

                    if (!secondPart.matches(SECOND_SPLIT)) {
                        return false;
                    }
                    return thirdPart.matches(THIRD_SPLIT);
                } else return false;
            } else return false;
        } else return false;
    }
}

package com.epam.edumanagementsystem.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IllegalCharactersUserDataValidationTest {

    private List<String> invalidNames;

    private List<String> validNames;
    private Character[] list = {'!', '#', '@', '$', '%', '^', '&', '+', '=', '\'', '/', '?', ';', '.', '~', '[', ']', '{', '}', '"'};

    @BeforeEach
    void setUp() {
        invalidNames = List.of("Name#", "@Name", "Na!me", "Nam%e", "Na^me", "Nam{e",
                "Name}", "Name]", "[Name", "Na+me", "Na=me}", "Na.me", "Na,me", "Name;", "Name/", "@Name");
        validNames = List.of("Name()", "Name*", "Name", "Na-me", "Na_me", "Name:", "Na|me", "<Name>");
    }

    @Test
    void nameIsInvalid() {

        for (String invalidName : invalidNames) {
            for (Character character : list) {
                if (!invalidName.contains(character.toString())) {
                    continue;
                } else {
                    assertTrue(invalidName.contains(character.toString()));
                }
            }
        }
    }

    @Test
    void nameIsValid() {
        for (String validName : validNames) {
            for (Character character : list) {
                assertFalse(validName.contains(character.toString()));
            }
        }
    }
}
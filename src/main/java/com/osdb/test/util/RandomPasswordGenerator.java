package com.osdb.test.util;

import org.passay.*;

public class RandomPasswordGenerator {

    private final static String ALLOWED_SPECIAL_CHARACTERS = "!@#$%^&*_+";
    private final static String ERROR_CODE = "Erroneous special char";
    private final static int PASSWORD_LENGTH = 8;

    public static String generatePassword() {
        PasswordGenerator generator = new PasswordGenerator();

        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(1);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(1);

        CharacterData digitChar = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChar);
        digitRule.setNumberOfCharacters(1);

        CharacterData specialChars = new CharacterData() {

            @Override
            public String getErrorCode() {
                return ERROR_CODE;
            }

            @Override
            public String getCharacters() {
                return ALLOWED_SPECIAL_CHARACTERS;
            }
        };

        CharacterRule specialCharsRule = new CharacterRule(specialChars);
        specialCharsRule.setNumberOfCharacters(1);

        return generator.generatePassword(PASSWORD_LENGTH, lowerCaseRule, upperCaseRule, digitRule, specialCharsRule);
    }
}

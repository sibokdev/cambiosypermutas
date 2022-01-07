package app.cambiosypermutas.cliente.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ari on 27/03/2021.
 */

public class PhoneValidator {

    private static final String PHONE_PATTERN = "[0-9]{10}";

    public PhoneValidator() {

    }

    public static boolean validate(final String hex) {
         Pattern pattern = Pattern.compile(PHONE_PATTERN);
         Matcher matcher = pattern.matcher(hex);

        return matcher.matches();
    }
}
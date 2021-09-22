package app.oficiodigital.cliente.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ari on 26/04/2021.
 */

public class PasswordValidator {
    private static final String PASSWORD_PATTERN = "[A-Z-a-z-0-9]{8,100}";

    public PasswordValidator() {

    }

    public static boolean validate(final String hex) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(hex);

        return matcher.matches();
    }
}

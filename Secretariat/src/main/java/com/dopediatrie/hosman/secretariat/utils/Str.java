package com.dopediatrie.hosman.secretariat.utils;

import com.dopediatrie.hosman.secretariat.entity.FactureMode;
import com.dopediatrie.hosman.secretariat.entity.ModePayement;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class Str {
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String slug(String input) {
        String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    public static String convertModePayementToString(List<ModePayement> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).getNom());
            if (i != list.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public static String convertFactureModePayementToString(List<FactureMode> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).getMode_payement().getNom());
            if (i != list.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}

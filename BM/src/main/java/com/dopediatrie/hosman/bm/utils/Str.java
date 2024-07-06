package com.dopediatrie.hosman.bm.utils;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
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

    public static String convertListToString(List<String> elements, String compactChar) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < elements.size(); i++) {
            sb.append(elements.get(i).toString());
            if (i != elements.size() - 1) {
                sb.append(compactChar);
            }
        }
        return sb.toString();
    }

    public static List<String> convertStringToList(String content, String escapeChar) {
        List<String> result = new ArrayList<String>();
        if(content != null && !content.isBlank()){
            String[] indiqList = content.split(escapeChar);
            result.addAll(Arrays.asList(indiqList));
        }
        return result;
    }

}

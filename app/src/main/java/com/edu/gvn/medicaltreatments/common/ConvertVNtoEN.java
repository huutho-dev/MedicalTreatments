package com.edu.gvn.medicaltreatments.common;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Created by hnc on 21/10/2016.
 */

public class ConvertVNtoEN {
    public static String unAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }
}

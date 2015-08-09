package com.triangularlake.constantine.triangularlake.utils;


import java.util.Locale;

public class StringUtils {

    public static String getSectorLabelOnLanguage() {
        // сделать в зависимости от количества секторов
        String sectorLabel;
        if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())) {
            sectorLabel = "sectors";
        } else if (Locale.getDefault().getLanguage().equals("ru")) {
            sectorLabel = "секторов";
        } else {
            sectorLabel = "sectors";
        }
        return sectorLabel;
    }

    public static String getProblemLabelOnLanguage() {
        String problemLabel;
        if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())) {
            problemLabel = "problems";
        } else if (Locale.getDefault().getLanguage().equals("ru")) {
            problemLabel = "проблем";
        } else {
            problemLabel = "problems";
        }
        return problemLabel;
    }
}

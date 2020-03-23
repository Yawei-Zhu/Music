package com.wind.music.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Utils {

    public static boolean isNetworkPath(String path) {
        Pattern pattern = Pattern.compile("^https?://", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(path);
        return matcher.find();
    }
}

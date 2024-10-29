package com.gamechanger.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class UrlUtils {
    public static String UrlDecode(String original) {
        return URLDecoder.decode(original, StandardCharsets.UTF_8);
    }
}

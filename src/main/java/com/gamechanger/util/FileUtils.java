package com.gamechanger.util;

public class FileUtils {
    public static final String FILE_URL_SEPARATOR = "/";

    public static String getFileNameFromUrl(String fileUrl) {
        int lastSlashIndex = fileUrl.lastIndexOf(FILE_URL_SEPARATOR);
        return fileUrl.substring(lastSlashIndex + 1);
    }
}

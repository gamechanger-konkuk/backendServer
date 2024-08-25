package com.gamechanger.util;

public class FileUtils {
    public static final String FILE_EXTENSION_SEPARATOR = ".";

//    public static String getFileName(String originalFileName) {
//        // 입력 파일명에서 확장자명 제거하는 알고리즘
//        int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
//        return originalFileName.substring(0, fileExtensionIndex); //파일 이름
//        // 현재 s3 다운로드에서 확장자명 포함해야 하므로 사용 x
//    }

    public static String buildFileName(String originalFileName) {
        // 파일명에 uuid 추가해서 해당 파일 unique하게 만드는 알고리즘
        int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR); //파일 확장자 구분선
        String fileExtension = originalFileName.substring(fileExtensionIndex); //파일 확장자
        String fileName = originalFileName.substring(0, fileExtensionIndex); //파일 이름
        String now = String.valueOf(System.currentTimeMillis()); //파일 업로드 시간

        return fileName + "_" + now + fileExtension;
        // 파일명만으로 uuid 추가된 파일 찾는 메커니즘 만들어야 함
    }
}

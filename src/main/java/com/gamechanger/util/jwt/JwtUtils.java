package com.gamechanger.util.jwt;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUtils {

    // jwt 토큰을 통한 인증 정보에서 id 가져오기
    public static String getCurrentLoginId() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername(); // 여기서 username은 loginId에 해당
    }
}

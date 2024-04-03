package com.andjela.diplomski.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class RequestHelper {
    public static String getTokenFromRequest(HttpServletRequest request){

        String bearerToken = request.getHeader("Authorization");

        // TODO remove bearerToken.equalsIgnoreCase("Bearer null")
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ") && !bearerToken.equalsIgnoreCase("Bearer null")){
            return bearerToken.substring(7, bearerToken.length());
        }

        return null;
    }

    public static String getRequestBaseUrl(HttpServletRequest request) {
        return ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
    }

    public static String getBaseUrl() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return getRequestBaseUrl(request);
    }
}

package com.andjela.diplomski.utils;

import com.andjela.diplomski.entity.Authority;
import com.andjela.diplomski.entity.User;
import com.andjela.diplomski.security.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtUtils {
    public static final String ISSUER = "PAT Web App";
    public static final String CLAIM_PURPOSE_AUTH = "PAT.App.Authenticate";
    public static final String CLAIM_PURPOSE_RESET_PASS = "PAT.App.ResetPassword";

    public static String getJwtTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (null == authHeader || authHeader.trim().length() == 0 || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        String jwtToken = authHeader.replace("Bearer ", "");
        return jwtToken;
    }

    public static String generateJwtToken(Authentication authentication, String jwtSecret, long jwtExpirationTimeMinutes) {
        return generateJwtToken(((SecurityUser)authentication.getPrincipal()).getUser(), jwtSecret, jwtExpirationTimeMinutes);
    }

    public static String generateJwtToken(User user, String jwtSecret, long jwtExpirationTimeMinutes) {
        return generateJwtToken(user, jwtSecret, jwtExpirationTimeMinutes, CLAIM_PURPOSE_AUTH);
    }

    private static String generateJwtToken(User user, String jwtSecret, long jwtExpirationTimeMinutes, String purpose) {
        String username = user.getEmail();

        Date currentDate = new Date();
        Date expiredDate = new Date(currentDate.getTime() + jwtExpirationTimeMinutes * 60 * 1000);

        String token = Jwts.builder()
                .issuer(ISSUER)
                .subject(username)
                .claim("authorities", populateAuthorities(user.getAuthorities()))
                .claim("purpose", purpose)
                .issuedAt(new Date())
                .expiration(expiredDate)
                .signWith(generateJwtKey(jwtSecret))
                .compact();

        return token;
    }

    public static Key generateJwtKey(String jwtSecret) {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public static Claims parseJwtClaims(final String jwt, final String jwtSecret) {
        Key key = generateJwtKey(jwtSecret);
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody();

        return claims;
    }

    private static Object populateAuthorities(Set<Authority> authorities) {
        return authorities.stream()
                .map(a -> a.getName()).collect(Collectors.joining(","));
    }
}

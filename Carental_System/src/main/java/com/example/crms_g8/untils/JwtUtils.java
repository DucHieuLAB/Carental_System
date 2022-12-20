package com.example.crms_g8.untils;

import com.example.crms_g8.Entity.AccountEntity;
import com.example.crms_g8.common.AccessDeniedException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static String secret = "This is secret";
    private static long expiryDuration = 60 * 60;

    public String generateJwt(AccountEntity accountEntity) {
        long miliTime = System.currentTimeMillis();
        long exiryTime = miliTime + expiryDuration * 1000;

        Date issuedAt = new Date(miliTime);
        Date expiryAt = new Date(exiryTime);

        Claims claims = Jwts.claims()
                .setSubject(Long.toString(accountEntity.getID()))
                .setIssuedAt(issuedAt)
                .setExpiration(expiryAt);
        claims.put("role", accountEntity.getRoleEntity().getRoleID());
        claims.put("email", accountEntity.getEmail());
        claims.put("username",accountEntity.getUsername());
        return Jwts.builder().setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public Claims verify(String authorization) throws Exception {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization).getBody();
            return claims;
        } catch (AccessDeniedException e) {
            throw e;
        }
    }
    // Lấy thông tin user từ jwt
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public void validateToken(String authToken) throws Exception {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
        } catch (MalformedJwtException ex) {
            throw new Exception("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new Exception("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new Exception("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new Exception("JWT claims string is empty.");
        }

    }
}

package org.brienze.shopping.list.api.utils;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtils {

    public static String generate(String username, SecretKey key, Date issuerAt, Date expiresAt) {
        return Jwts.builder()
                   .subject(username)
                   .issuedAt(issuerAt)
                   .expiration(expiresAt)
                   .signWith(key)
                   .compact();
    }

//    public static String getUsername(SecretKey key, String token) {
//        return Jwts.parser()
//                   .decryptWith(key)
//                   .build()
//                   .parseSignedClaims(token)
//                   .getPayload()
//                   .getSubject();
//    }

}

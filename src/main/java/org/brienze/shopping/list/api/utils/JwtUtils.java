package org.brienze.shopping.list.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import org.brienze.shopping.list.api.exception.InvalidToken;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

public class JwtUtils {

    public static String generate(String username, SecretKey key, Date issuerAt, Date expiresAt) {
        return Jwts.builder()
                   .header()
                   .add("username", username)
                   .and()
                   .subject(username)
                   .issuedAt(issuerAt)
                   .expiration(expiresAt)
                   .signWith(key)
                   .compact();
    }

    public static String decodeUsername(String token) {
        try {
            String encodedPayload = token.split("\\.")[0];
            String decodedPayload = new String(Base64.getDecoder().decode(encodedPayload));
            return new ObjectMapper().readTree(decodedPayload).get("username").asText();
        } catch (Exception e) {
            throw new InvalidToken(e);
        }
    }

    public static boolean isValid(String token) {
        try {
            String encodedPayload = token.split("\\.")[1];
            String decodedPayload = new String(Base64.getDecoder().decode(encodedPayload));
            return new Date(Duration.ofSeconds(new ObjectMapper().readTree(decodedPayload).get("exp").asLong()).toMillis()).after(new Date(System.currentTimeMillis()));
        } catch (JsonProcessingException e) {
            throw new InvalidToken(e);
        }
    }
}

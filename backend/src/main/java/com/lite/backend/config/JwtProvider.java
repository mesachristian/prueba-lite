package com.lite.backend.config;

import com.lite.backend.data.entity.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    public String generateToken(Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        return Jwts.builder().signWith(getKey(secret))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000))
                .claim("roles", getRoles(userDetails))
                .compact();
    }

    public String getUsernameFrmToken(String token) {
        return Jwts.parser().setSigningKey(getKey(secret)).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(getKey(secret)).build().parseClaimsJws(token).getBody();
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("Expired token", e);
            throw new RuntimeException(e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported token", e);
            throw new RuntimeException(e);
        } catch (MalformedJwtException e) {
            logger.error("Malformed token", e);
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            logger.error("Signature exception token", e);
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            logger.error("Illegal token", e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            logger.error("Failed token", e);
            throw e;
        }


    }

    private List<String> getRoles(User userDetails){
        return userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    private Key getKey(String secret){
        byte[] secretBytes = Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }
}

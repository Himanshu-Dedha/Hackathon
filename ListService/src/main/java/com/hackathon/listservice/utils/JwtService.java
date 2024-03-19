package com.hackathon.listservice.utils;



import com.hackathon.listservice.controller.TodoListController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.function.Function;

@Service
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);
    private final String SECRET_KEY="5742e0066c6b3a83703e475dd1cd0f6d283ac65f8801559cf27ef3ee6254e63e";

    public Long getUserIdFromToken(String authToken) {
        String token = authToken.substring(7);
      Claims claims = extractAllClaims(token);
        logger.info("Claims extracted from token: {}", claims);
        Long userId = claims.get("user_Id", Long.class);
        logger.info("Owner ID extracted from token: {}", userId);
        return userId;
    }

    public <T> T extractClaim(String token, Function<Claims,T> resolver){
        Claims claims = extractAllClaims(token);
        System.out.println("Printing the claims here:");
        System.out.println(claims);
        return resolver.apply(claims);
    }



    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }
    private SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

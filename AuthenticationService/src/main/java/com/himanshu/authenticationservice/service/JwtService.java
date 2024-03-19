package com.himanshu.authenticationservice.service;


import com.himanshu.authenticationservice.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private final String SECRET_KEY="5742e0066c6b3a83703e475dd1cd0f6d283ac65f8801559cf27ef3ee6254e63e";

    public Long getUserIdFromToken(String token) {
      Claims claims = extractAllClaims(token);
        Long userId = claims.get("user_Id", Long.class);
        System.out.println("The user ID is " + userId);
        return userId;
    }

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    //Token Validation
    public boolean isValid(String token, UserDetails user){
        String username = extractUsername(token); //Extracted User
        return username.equals(user.getUsername()) && !isTokenExpired(token); //If Extracted user == authenticated user and token is not expired then isValid
    }

    private boolean isTokenExpired(String token) {
        System.out.println("Calling the isTokenExpired Function");
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        System.out.println("Calling the extractExpiration Function");
        return extractClaim(token, Claims::getExpiration);
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
//        SO WHAT THE ABOVE METHOD DOES IS, IT EXTRACTS ALL THE PROPERTIES FROM TOKEN,
//        I.E. SUBJECT, ISSUEDAT ETC.
    }

    //    Token Generation
    public String generate_Token(Users user){
        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .claim("user_Id", user.getId()) // Custom claim to include the user's ID
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000))
                .signWith(getSignInKey())
                .compact();
        return token;
    }


/*
Adding more claims to the token:

public String generate_Token(User user){
    String token = Jwts
            .builder()
            .setSubject(user.getUsername()) // It's common to use username or user ID as the subject
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 24*60*60*1000))
            .claim("roles", user.getRoles()) // Adding custom claim for user roles
            .claim("email", user.getEmail()) // Adding custom claim for user's email
            .signWith(getSignInKey())
            .compact();
    return token;
}
*/


    private SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

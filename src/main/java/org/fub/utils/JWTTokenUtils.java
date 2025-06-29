package org.fub.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.fub.exception.JWTTokenCreationException;
import org.fub.exception.UserNotFoundException;
import org.fub.model.UserModel;
import org.fub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JWTTokenUtils {

    @Value("${app.jwtSecret}")
    private String jwtSecretKey;

    @Value("${app.jwtExpirationMs}")
    private int expirationTime;

    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Autowired
    private UserRepository repository;

    Date createdAt = new Date();
    Date expiryDate = new Date(createdAt.getTime()+expirationTime);

    public String getJWTToken(String userName) {
        String token = null;

       UserModel user = repository.findByEmail(userName).orElseThrow(()->new UserNotFoundException("User doesn't exist with the username or check the credentials"));

        try {
            token = Jwts.builder()
                    .setSubject("FUB")
                    .setIssuedAt(createdAt)
                    .setExpiration(expiryDate)
                    .setIssuer("FUB")
                    .setSubject(user.getUserId())
                    .signWith(key,SignatureAlgorithm.HS512)
                    .compact();

            return token;
        }
        catch (Exception e) {
            throw new JWTTokenCreationException(e.getMessage());
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}

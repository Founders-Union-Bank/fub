package org.fub.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.fub.exception.JWTTokenCreationException;
import org.fub.exception.UserNotFoundException;
import org.fub.model.UserModel;
import org.fub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTTokenUtils {

    @Autowired
    private UserRepository repository;


    public String getJWTToken(String userName) {
        String token = null;

        UserModel user = repository.findByEmail(userName).orElseThrow(() -> new UserNotFoundException("User doesn't exist with the username or check the credentials"));

        try {
            int expirationTime = 3600000;
            token = Jwts.builder()
                    .setSubject("FUB")
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime() + expirationTime))
                    .claim("userId", user.getUserId())
                    .claim("isAdmin", user.isAdmin())
                    .claim("role", user.getRoles())
                    .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                    .compact();
            return token;
        } catch (Exception e) {
            throw new JWTTokenCreationException(e.getMessage());
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public SecretKey getSecretKey() {
        String jwtSecretKey = "FOUNDERSUNIONBANKAPPLICATIONSECRETKEYAUTHOURSARANKUMARVIJAYANHFSAHSAHSADHAGDGHGASDHSDHHJSDKAJSKFJSAF";
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
    }
}

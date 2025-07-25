package org.fub.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.fub.response.ErrorResponse;
import org.fub.utils.JWTTokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;

@AllArgsConstructor
public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    private JWTTokenUtils utils;

    private ObjectMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken != null) {
            try {
                SecretKey key = utils.getSecretKey();
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key).build()
                        .parseClaimsJws(jwtToken.substring(7))
                        .getBody();

                String userName = String.valueOf(claims.get("userId"));
                String role = String.valueOf(claims.get("role"));

                Authentication authentication = new UsernamePasswordAuthenticationToken(userName, null, AuthorityUtils.commaSeparatedStringToAuthorityList(role));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ExpiredJwtException exception) {
                ErrorResponse errorResponse = ErrorResponse.builder()
                        .statusCode(HttpStatus.UNAUTHORIZED)
                        .message("JWT Token has Expired")
                        .timeStamp(new Date())
                        .build();
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getWriter().write(mapper.writeValueAsString(errorResponse));
                return;
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write(e.getMessage());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/api/v1/login") || "OPTIONS".equalsIgnoreCase(request.getMethod());
    }
}

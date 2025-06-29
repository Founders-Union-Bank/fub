package org.fub.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.fub.request.LoginRequest;
import org.fub.utils.JWTTokenUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private AuthenticationManager authenticationManager;

    private JWTTokenUtils utils;

    @Hidden
    @RequestMapping("/login")
    ResponseEntity<?> authenticate(@RequestBody LoginRequest request, HttpServletResponse response){
        Authentication authentication = authenticationManager
                       .authenticate(new UsernamePasswordAuthenticationToken(request.getUserName()
                        ,request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        response.setHeader("Authorization","Bearer "+utils.getJWTToken(request.getUserName()));
        return ResponseEntity.ok("Login Success");
    }

}

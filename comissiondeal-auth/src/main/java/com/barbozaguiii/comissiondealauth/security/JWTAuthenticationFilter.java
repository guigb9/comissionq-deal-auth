package com.barbozaguiii.comissiondealauth.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.barbozaguiii.comissiondealauth.controller.request.UserLogin;
import com.barbozaguiii.comissiondealauth.data.UserDetailData;
import com.barbozaguiii.comissiondealauth.model.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public static final int TOKEN_EXPIRACAO = 600_000;
    public static final String TOKEN_SENHA = "ae53bdc3-008d-4e21-bd8a-a4c40e0627f2";@Autowired
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLogin userModel = new ObjectMapper()
                    .readValue(request.getInputStream(), UserLogin.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userModel.getLogin(),
                    userModel.getPassword(),
                    new ArrayList<>()
            ));
        } catch (IOException e) {
            throw new RuntimeException("Falha ao autenticar usuario ",e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = JWT.create().withSubject((String) authResult.getPrincipal())
                .withExpiresAt(new Date(System.currentTimeMillis()+TOKEN_EXPIRACAO))
                .sign(Algorithm.HMAC512(TOKEN_SENHA));

        response.getWriter().write(token);
        response.getWriter().flush();
    }
}

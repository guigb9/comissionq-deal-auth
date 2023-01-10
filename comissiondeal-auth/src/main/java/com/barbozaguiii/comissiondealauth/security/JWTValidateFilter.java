package com.barbozaguiii.comissiondealauth.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class JWTValidateFilter extends GenericFilterBean {

    public static final String HEADER_ATTR = "Authorization";
    public static final String PREFIX_ATR = "Bearer ";


    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        String user = JWT.require(Algorithm.HMAC512(JWTAuthenticationFilter.TOKEN_SENHA)).build().verify(token).getSubject();

        if(Objects.isNull(user)) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());

    }



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String attribute = request.getHeader(HEADER_ATTR);

        if(Objects.isNull(attribute)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if(!attribute.startsWith(PREFIX_ATR)){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(attribute.replace(PREFIX_ATR, ""));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

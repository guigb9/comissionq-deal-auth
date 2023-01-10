package com.barbozaguiii.comissiondealauth.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KeyComponent {

    @Value("${key.jwt}")
    private String key;

    public KeyComponent() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

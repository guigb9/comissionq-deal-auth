package com.barbozaguiii.comissiondealauth.data;

import com.barbozaguiii.comissiondealauth.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDetailData implements UserDetails {
    private final Optional<UserModel> userModel;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return userModel.orElse(new UserModel()).getUserPassword();
    }

    @Override
    public String getUsername() {
        return userModel.orElse(new UserModel()).getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !userModel.orElse(new UserModel()).getAccountExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !userModel.orElse(new UserModel()).getAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !userModel.orElse(new UserModel()).getCredentialsExpired();
    }

    @Override
    public boolean isEnabled() {
        return userModel.orElse(new UserModel()).getEnabledUser();
    }
}

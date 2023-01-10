package com.barbozaguiii.comissiondealauth.service;

import com.barbozaguiii.comissiondealauth.data.UserDetailData;
import com.barbozaguiii.comissiondealauth.model.UserModel;
import com.barbozaguiii.comissiondealauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> usuario = userRepository.findByLogin(username);
        if(usuario.isEmpty()){
            throw new UsernameNotFoundException("Usuário["+username+"] não encontrado");
        }
        return new UserDetailData(usuario);
    }
}

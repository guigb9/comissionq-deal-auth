package com.barbozaguiii.comissiondealauth.service;

import com.barbozaguiii.comissiondealauth.model.UserModel;
import com.barbozaguiii.comissiondealauth.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public UserModel create(UserModel userModel) {
        String oldUserPassword = userModel.getUserPassword();
        userModel.setUserPassword(encodePassword(oldUserPassword));
        return userRepository.save(userModel);
    }

    public List<UserModel> getAll() {
        return userRepository.findAll();
    }

    public String encodePassword(String passwordNotEncoded) {
        return encoder.encode(passwordNotEncoded);
    }

    public Optional<UserModel> getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public HttpStatus getHttpStatusBasedInPassword(String password, UserModel user) {
        boolean valid = encoder.matches(password, user.getUserPassword());
        return valid ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
    }
}

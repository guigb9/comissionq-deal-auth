package com.barbozaguiii.comissiondealauth.controller;

import com.barbozaguiii.comissiondealauth.controller.request.UserCreationRequest;
import com.barbozaguiii.comissiondealauth.controller.response.UserCreationResponse;
import com.barbozaguiii.comissiondealauth.model.UserModel;
import com.barbozaguiii.comissiondealauth.repository.UserRepository;
import com.barbozaguiii.comissiondealauth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserModel>> getAll() {
        return ResponseEntity.ok(userService.getAll()) ;
    }

    @PostMapping
    public ResponseEntity<UserCreationResponse> post(@RequestBody @Valid UserCreationRequest request) {
        UserModel userModel = new UserModel();
        userModel.setLogin(request.getLogin());
        userModel.setEmail(request.getEmail());
        userModel.setUserPassword(request.getUserPassword());
        return ResponseEntity.ok(new UserCreationResponse(userService.create(userModel)));
    }

    @PostMapping("/password/validate")
    public ResponseEntity<Boolean> validatePasswd(@RequestParam String login, @RequestParam String password) {
        UserModel user = userService.getUserByLogin(login).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "user doesn't exists"));
        HttpStatus httpStatus = userService.getHttpStatusBasedInPassword(password, user);
        return ResponseEntity.status(httpStatus).body(httpStatus.equals(HttpStatus.OK));
    }
}

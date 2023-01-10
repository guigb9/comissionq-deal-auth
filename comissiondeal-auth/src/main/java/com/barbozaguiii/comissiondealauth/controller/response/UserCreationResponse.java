package com.barbozaguiii.comissiondealauth.controller.response;

import com.barbozaguiii.comissiondealauth.model.UserModel;
import lombok.Data;

@Data
public class UserCreationResponse {


    public UserCreationResponse(UserModel userModel) {
        setLogin(userModel.getLogin());
        setEmail(userModel.getEmail());
    }

    private String login;
    private String email;
    private String password;
}

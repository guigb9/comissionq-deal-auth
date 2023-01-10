package com.barbozaguiii.comissiondealauth.repository;

import com.barbozaguiii.comissiondealauth.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {

    Optional<UserModel> findByLogin(String login);
}

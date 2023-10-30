package com.dopediatrie.hosman.auth.repository;

import com.dopediatrie.hosman.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}

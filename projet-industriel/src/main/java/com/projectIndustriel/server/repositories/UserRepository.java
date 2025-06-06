package com.projectIndustriel.server.repositories;

import com.projectIndustriel.server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface UserRepository extends JpaRepository<User, String> {
}

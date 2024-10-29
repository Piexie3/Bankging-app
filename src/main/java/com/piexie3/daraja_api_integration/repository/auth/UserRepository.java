package com.piexie3.daraja_api_integration.repository.auth;

import com.piexie3.daraja_api_integration.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User , Long> {
   Boolean existsByEmail(String email);
}

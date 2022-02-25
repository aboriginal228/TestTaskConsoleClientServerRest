package com.example.server;

import com.example.server.entity.RenderUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<RenderUser, Long> {

    RenderUser getByUsername(String userName);
}

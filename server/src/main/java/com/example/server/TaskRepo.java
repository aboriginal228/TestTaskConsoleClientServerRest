package com.example.server;

import com.example.server.entity.RenderTask;
import com.example.server.entity.RenderUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<RenderTask, Long> {

    List<RenderTask> findAllByUser(RenderUser user);
}

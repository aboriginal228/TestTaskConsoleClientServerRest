package com.example.server;

import com.example.server.entity.RenderTask;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Component
public class ChangerSaver {

    private final TaskRepo repo;

    public ChangerSaver(TaskRepo repo) {
        this.repo = repo;
    }

    @Transactional
    public void saveTask(RenderTask task) {
        task.setStatus("COMPLETE");
        task.setStatusChangeTime(LocalTime.now());
        repo.save(task);
    }
}

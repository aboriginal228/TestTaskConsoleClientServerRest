package com.example.server;

import com.example.server.entity.RenderTask;
import org.springframework.stereotype.Component;

@Component
public class StatusChanger {

    private final ChangerSaver saver;

    public StatusChanger(ChangerSaver saver) {
        this.saver = saver;
    }

    public void changeStatus(RenderTask task) {
        new Thread(() -> {
            try {
                Thread.sleep((long) (60000 + 240000 * (Math.random())));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            saver.saveTask(task);
        }).start();
    }
}

package com.example.server;

import com.example.server.entity.RenderTask;
import com.example.server.entity.RenderUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalTime;
import java.util.List;

@Controller
public class MainController {

    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final StatusChanger changer;

    public MainController(TaskRepo taskRepo, UserRepo userRepo, StatusChanger changer) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
        this.changer = changer;
    }

    @GetMapping("create-user")
    public ResponseEntity<String> createUser(@RequestParam String user) {
        RenderUser savedUser = userRepo.save(RenderUser.builder().username(user).build());
        return ResponseEntity.ok("RenderUser created " + user + " id: " + savedUser.getId());
    }

    @GetMapping("create-task")
    public ResponseEntity<String> createTask(@RequestParam String task, @RequestParam RenderUser user) {

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        RenderTask renderTask = taskRepo.save(
                RenderTask.builder()
                        .task(task)
                        .user(user)
                        .status("RENDERING")
                        .createdTime(LocalTime.now())
                        .build());
        changer.changeStatus(renderTask);
        return ResponseEntity.ok("RenderTask created " + renderTask.getTask() + " id: " + renderTask.getId());
    }

    @GetMapping("get-list")
    public ResponseEntity<String> getList(@RequestParam RenderUser user) {
        List<RenderTask> renderTasks = taskRepo.findAllByUser(user);

        if (renderTasks.size() == 0) {
            return ResponseEntity.ok("No tasks");
        }

        StringBuilder builder = new StringBuilder();

        for (RenderTask t : renderTasks) {
            builder.append("id: ")
                    .append(t.getId())
                    .append(" status: ")
                    .append(t.getStatus());
            builder.append('\n');
        }

        return ResponseEntity.ok(builder.toString().trim());
    }

    @GetMapping("get-status")
    public ResponseEntity<String> getStatus(@RequestParam RenderTask task, @RequestParam RenderUser user) {

        if (!task.getUser().equals(user)) {
            return ResponseEntity.status(403).body("Access denied");
        }

        String changedTime = task.getStatusChangeTime() == null ? "working" : task.getStatusChangeTime().toString();
        return ResponseEntity.ok("id "
                + task.getId()
                + " status: "
                + task.getStatus()
                + " created: "
                + task.getCreatedTime()
                + " completed: "
                + changedTime);
    }

    @GetMapping("check-user")
    public ResponseEntity<String> checkUser(@RequestParam String user) {
        RenderUser userFromDB = userRepo.getByUsername(user);

        if (userFromDB == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        return ResponseEntity.ok(userFromDB.getId().toString());
    }
}

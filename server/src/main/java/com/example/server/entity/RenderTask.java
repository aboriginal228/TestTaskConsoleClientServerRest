package com.example.server.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RenderTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String task;

    @ManyToOne
    @JoinColumn(name = "render_user_id")
    private RenderUser user;

    private String status;

    private LocalTime createdTime;

    private LocalTime statusChangeTime;
}

package com.example.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

@Component
public class Runner implements CommandLineRunner {

    public static final String url = "http://localhost:9015/";

    private String id;

    private final ApplicationContext context;

    public Runner(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public void run(String... args) {

        RestTemplate template = new RestTemplate();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {

            String s = sc.nextLine();

            try {

                if ("exit".equals(s)) {
                    System.out.println("Bye");
                    break;

                } else if ("login".equals(s)){
                    System.out.println("Enter username");
                    String username = sc.nextLine();
                    try {
                        ResponseEntity<String> response = template.getForEntity(url + "check-user?user=" + username, String.class);
                        id = response.getBody();
                        System.out.println("Login successful");
                    } catch (Exception e) {
                        System.out.println("User not found");
                        id = null;
                    }

                } else if ("createuser".equals(s)) {
                    System.out.println("Enter username");
                    String user = sc.nextLine();
                    ResponseEntity<String> response = template.getForEntity(url + "create-user?user=" + user, String.class);
                    System.out.println(response.getBody());

                } else if ("createtask".equals(s)) {
                    if (id == null) {
                        System.out.println("Not logged in");
                        continue;
                    }
                    System.out.println("Enter task");
                    String task = sc.nextLine();
                    ResponseEntity<String> response = template.getForEntity(url + "create-task?user=" + id + "&task=" + task, String.class);
                    System.out.println(response.getBody());

                } else if ("getlist".equals(s)) {
                    if (id == null) {
                        System.out.println("Not logged in");
                        continue;
                    }
                    System.out.println("List of tasks");
                    ResponseEntity<String> response = template.getForEntity(url + "get-list?user=" + id, String.class);
                    System.out.println(response.getBody());

                } else if ("getstatus".equals(s)) {
                    if (id == null) {
                        System.out.println("Not logged in");
                        continue;
                    }
                    System.out.println("Enter task id");
                    String task = sc.nextLine();
                    ResponseEntity<String> response = template.getForEntity(url + "get-status?task=" + task + "&user=" + id, String.class);
                    System.out.println(response.getBody());

                } else {
                    System.out.println("Wrong command");
                }

            } catch (Exception e) {
                System.out.println("Something went wrong \n" + e.getMessage());
            }
        }

        sc.close();
        SpringApplication.exit(context);
    }
}

package com.example.firstservice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/first-service")
@RequiredArgsConstructor
@Slf4j
public class FirstServiceController {

    private final Environment env;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to First Service!";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header) {
        log.info(header);
        return "Hello World in First Service!";
    }

    @GetMapping("/check")
    public String check(HttpServletRequest request) {
        log.info("Server Port = {}", request.getServerPort());

        return "Hi, there. This is a message from First Service on PORT " + env.getProperty("local.server.port");
    }
}

package billsservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    @Autowired
    Environment env;
    @GetMapping("/test")
    public String testWork() {
        return "I am working on port: " + env.getProperty("server.port");
    }
}

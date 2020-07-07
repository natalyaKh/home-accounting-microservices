package usersservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import usersservice.repository.UserRepository;
import usersservice.service.UsersSchedulerService;
import usersservice.service.hystrix.SendMail;

@RestController
@RequestMapping("/clean-data")
public class SchedulerController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    SendMail sendMail;
    @Autowired
    UsersSchedulerService usersSchedulerService;


    @GetMapping("/clean-not-conf")
    public void cleanNotConfirmedEmail() {
        usersSchedulerService.cleanNotConfirmedEmail();
    }
}

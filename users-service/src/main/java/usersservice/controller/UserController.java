package usersservice.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import usersservice.dto.UserDto;
import usersservice.models.request.UserDetailsRequestModel;
import usersservice.models.responce.UserRest;
import usersservice.service.UserServiceImpl;

import javax.validation.Valid;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    Environment env;
    @Autowired
    UserServiceImpl userService;

    @GetMapping("/test")
    public String testWork() {
        return "I am working on port: " + env.getProperty("server.port");
    }

    @PostMapping()
    public UserRest createUser(@Validated @RequestBody UserDetailsRequestModel userDetails) throws Exception {
        UserRest returnValue = new UserRest();
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto createdUser = userService.createUser(userDto);
        returnValue = modelMapper.map(createdUser, UserRest.class);
        return returnValue;
    }
}

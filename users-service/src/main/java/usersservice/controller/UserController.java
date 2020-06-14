package usersservice.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import usersservice.dto.UserDto;
import usersservice.models.request.UserDetailsRequestModel;
import usersservice.models.responce.UserRest;
import usersservice.service.UserServiceImpl;

import javax.validation.Valid;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


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

    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable String id) {
        UserRest returnValue = new UserRest();
        UserDto userDto = userService.getUserByUserId(id);
        ModelMapper modelMapper = new ModelMapper();
        returnValue = modelMapper.map(userDto, UserRest.class);
        return returnValue;
    }

//    @PutMapping(path = "/{id}")
//    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
//        UserRest returnValue = new UserRest();
//        UserDto userDto = new UserDto();
//        userDto = new ModelMapper().map(userDetails, UserDto.class);
//        UserDto updateUser = userService.updateUser(id, userDto);
//        returnValue = new ModelMapper().map(updateUser, UserRest.class);
//        return returnValue;
//    }

//    @DeleteMapping(path = "/{id}")
//    public OperationStatusModel deleteUser(@PathVariable String id) {
//        OperationStatusModel returnValue = new OperationStatusModel();
//        returnValue.setOperationName(RequestOperationName.DELETE.name());
//
//        userService.deleteUser(id);
//
//        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
//        return returnValue;
//    }


    @GetMapping()
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "2") int limit) {
        List<UserRest> returnValue = new ArrayList<>();
        List<UserDto> users = userService.getUsers(page, limit);
        Type listType = new TypeToken<List<UserRest>>() {
        }.getType();
        returnValue = new ModelMapper().map(users, listType);

        return returnValue;
    }
}

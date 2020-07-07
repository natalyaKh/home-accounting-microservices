package usersservice.controller;



import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import usersservice.dto.UserDto;
import usersservice.enums.RequestOperationName;
import usersservice.models.request.PasswordResetModel;
import usersservice.models.request.PasswordResetRequestModel;
import usersservice.models.request.UserDetailsRequestModel;
import usersservice.models.responce.OperationStatusModel;
import usersservice.enums.RequestOperationStatus;
import usersservice.models.responce.UserRest;
import usersservice.service.UserService;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/create-admin")
    public String createSuperAdmin() {
        userService.createSuperAdmin();
        return "super admin created";
    }
    @DeleteMapping("/delete-admin/{adminId}")
    public String deleteSuperAdmin(@PathVariable String adminId){
        userService.deleteUser(adminId);
        return "Super admin deleted";
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
//    для свагера
    @ApiOperation(value="The Get User Details Web Service Endpoint",
            notes="${userController.GetUser.ApiOperation.Notes}")
    /**	добавляем значение токена в хедер метода*/
    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="${userController.authorizationHeader.description}", paramType="header")
    })
    public UserRest getUser(@PathVariable String id) {
        UserRest returnValue = new UserRest();
        UserDto userDto = userService.getUserByUserId(id);
        ModelMapper modelMapper = new ModelMapper();
        returnValue = modelMapper.map(userDto, UserRest.class);
        return returnValue;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="${userController.authorizationHeader.description}", paramType="header")
    })
    @PutMapping(path = "/{id}")
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();
        userDto = new ModelMapper().map(userDetails, UserDto.class);
        UserDto updateUser = userService.updateUser(id, userDto);
        returnValue = new ModelMapper().map(updateUser, UserRest.class);
        return returnValue;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="${userController.authorizationHeader.description}", paramType="header")
    })
    @DeleteMapping(path = "/{id}")
    public OperationStatusModel deleteUser(@PathVariable String id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(id);

        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name="authorization", value="${userController.authorizationHeader.description}", paramType="header")
    })
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

    /**
     * http://localhost:8082/users/email-verification?token=sdfsdf
     * */
    @GetMapping(path = "/email-verification")
    public OperationStatusModel verifyEmailToken(@RequestParam(value = "token") String token) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.VERIFY_EMAIL.name());
        boolean isVerified = userService.verifyEmailToken(token);
        if (isVerified) {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        } else {
            returnValue.setOperationResult(RequestOperationStatus.ERROR.name());
        }
        return returnValue;
    }

    /**
     * step-2 for change password
     * http://localhost:8082/users/password-reset-request
     */
    @PostMapping(path = "/password-reset-request")
    public OperationStatusModel requestReset(@RequestBody PasswordResetRequestModel passwordResetRequestModel) {
        OperationStatusModel returnValue = new OperationStatusModel();

        boolean operationResult = userService.requestPasswordReset(passwordResetRequestModel.getEmail());

        returnValue.setOperationName(RequestOperationName.REQUEST_PASSWORD_RESET.name());
        returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

        if (operationResult) {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        }

        return returnValue;
    }

    /**    step-2 for change password */
    @PostMapping(path = "/password-reset")
    public OperationStatusModel resetPassword(@RequestBody PasswordResetModel passwordResetModel) {
        OperationStatusModel returnValue = new OperationStatusModel();

        boolean operationResult = userService.resetPassword(
                passwordResetModel.getToken(),
                passwordResetModel.getPassword());

        returnValue.setOperationName(RequestOperationName.PASSWORD_RESET.name());
        returnValue.setOperationResult(RequestOperationStatus.ERROR.name());

        if (operationResult) {
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        }

        return returnValue;
    }


}

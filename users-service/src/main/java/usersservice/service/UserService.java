package usersservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.core.userdetails.UserDetailsService;
import usersservice.dto.UserDto;
import usersservice.models.entity.UserEntity;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto user) throws JsonProcessingException;
    UserDto getUser(String userName);
    UserDto getUserByUserId(String id);
    List<UserDto> getUsers(int page, int limit);
    UserDto updateUser(String id, UserDto userDto);
    void deleteUser(String id);
    boolean verifyEmailToken(String token);
    boolean resetPassword(String token, String password);
    boolean requestPasswordReset(String email);
     UserEntity createSuperAdmin();
}

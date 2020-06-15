package usersservice.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import usersservice.dto.UserDto;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto getUser(String userName);
    UserDto getUserByUserId(String id);
    List<UserDto> getUsers(int page, int limit);
    UserDto updateUser(String id, UserDto userDto);
    void deleteUser(String id);
    boolean verifyEmailToken(String token);
}

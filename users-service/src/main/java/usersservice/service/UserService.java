package usersservice.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import usersservice.dto.UserDto;

public interface UserService extends UserDetailsService {
    UserDto getUser(String userName);
}

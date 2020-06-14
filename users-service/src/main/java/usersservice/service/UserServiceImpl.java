package usersservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import usersservice.utils.Utils;
import usersservice.dto.UserDto;
import usersservice.exceptions.ErrorMessages;
import usersservice.exceptions.UserServiceException;
import usersservice.models.entity.UserEntity;
import usersservice.repository.UserRepository;


@Service
public class UserServiceImpl {

    @Autowired
    UserRepository userRepository;
    @Autowired
    Utils utils;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto createUser(UserDto user) {

        if ( userRepository.findByEmail(user.getEmail()) != null) {
            throw new UserServiceException(ErrorMessages.USER_ALREADY_EXISTS.name());
        }
        ModelMapper modelMapper = new ModelMapper();
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);

        String publicUserId = utils.generateUserId(30);
        userEntity.setUserId(publicUserId);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        UserEntity storedUserDetails = userRepository.save(userEntity);
        UserDto returnValue = modelMapper.map(storedUserDetails, UserDto.class);

        return returnValue;
    }

}

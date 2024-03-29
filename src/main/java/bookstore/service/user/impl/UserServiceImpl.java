package bookstore.service.user.impl;

import bookstore.dto.user.UserRegistrationRequestDto;
import bookstore.dto.user.UserResponseDto;
import bookstore.exeption.RegistrationException;
import bookstore.mapper.UserMapper;
import bookstore.model.User;
import bookstore.repository.user.UserRepository;
import bookstore.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto registrationRequestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(registrationRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Can`t register user");
        }
        User user = userMapper.toModel(registrationRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.toResponseDto(userRepository.save(user));
    }
}

package my_shop.users.service;

import jakarta.transaction.Transactional;
import my_shop.common.exceptions.ResourceNotFoundException;
import my_shop.users.dto.UserProfileResponseDto;
import my_shop.users.dto.UserUpdateRequestDto;
import my_shop.users.mapper.UserMapper;
import my_shop.users.model.User;
import my_shop.users.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public Boolean existsEmailUser(String email) {
        return userRepository.existsByEmail(email);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserProfileResponseDto getUserProfile(User user) {
        return userMapper.toUserProfileDto(user);
    }

    @Transactional
    public UserProfileResponseDto updateNameUser(Long id, UserUpdateRequestDto userUpdateRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        userMapper.updateUserFromDto(userUpdateRequestDto, user);
        User savedUser = userRepository.save(user);

        return userMapper.toUserProfileDto(savedUser);
    }
}

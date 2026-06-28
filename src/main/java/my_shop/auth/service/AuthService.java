package my_shop.auth.service;

import my_shop.auth.dto.AuthResponseDto;
import my_shop.auth.dto.LoginRequestDto;
import my_shop.auth.dto.RegisterRequestDto;
import my_shop.auth.mapper.AuthMapper;
import my_shop.common.config.JwtService;
import my_shop.common.exceptions.UserAlreadyExistsException;
import my_shop.users.model.User;
import my_shop.users.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthMapper authMapper;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService,
                       AuthenticationManager authenticationManager, AuthMapper authMapper) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.authMapper = authMapper;
    }


    public AuthResponseDto userRegister(RegisterRequestDto request) {
        if (userService.existsEmailUser(request.getEmail())) {
            throw new UserAlreadyExistsException("Ya existe una cuenta con este email");
        }

        User user = AuthMapper.INSTANCE.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User saveUser = userService.saveUser(user);

        String token = jwtService.generateToken(saveUser);

        AuthResponseDto response = AuthMapper.INSTANCE.toRegisterResponse(saveUser);
        response.setToken(token);

        return response;
    }

    public AuthResponseDto userLogin(LoginRequestDto request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();

        String token = jwtService.generateToken(user);

        AuthResponseDto response = AuthMapper.INSTANCE.toLoginResponse(user);
        response.setToken(token);

        return response;
    }

    public AuthResponseDto checkStatus(User user) {
        String newToken = jwtService.generateToken(user);
        AuthResponseDto response = authMapper.toLoginResponse(user);
        response.setToken(newToken);

        return response;
    }
}

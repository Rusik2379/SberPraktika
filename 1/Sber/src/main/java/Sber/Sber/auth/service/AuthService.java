package Sber.Sber.auth.service;

import Sber.Sber.auth.request.LoginRequest;
import Sber.Sber.auth.request.SignUpRequest;
import Sber.Sber.auth.response.AuthResponse;
import Sber.Sber.models.User;
import Sber.Sber.repositories.UserRepository;
import Sber.Sber.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static Sber.Sber.models.enums.Role.ROLE_ADMIN;
import static Sber.Sber.models.enums.Role.ROLE_USER;
import static Sber.Sber.models.enums.Role.ROLE_DIRECTOR;


@Service
@RequiredArgsConstructor

public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;


    public AuthResponse register(SignUpRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return new AuthResponse("User with this email already exists", HttpStatus.BAD_REQUEST);
        }

        User user = User.builder()
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .middlename(registerRequest.getMiddlename())
                .email(registerRequest.getEmail())
                .password(new BCryptPasswordEncoder().encode(registerRequest.getPassword()))
                .companyname(registerRequest.getCompanyname().trim())
                .role(registerRequest.getCompanyname() == null || registerRequest.getCompanyname().trim() == ""? ROLE_USER : ROLE_DIRECTOR)
                .build();

        userRepository.save(user);

        return AuthResponse.builder()
                .message("User registered successfully")
                .response(HttpStatus.OK)
                .build();
    }

    public AuthResponse login(LoginRequest loginRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user);

        String message = user.getRole().equals(ROLE_ADMIN) ? "Admin logged in successfully" : "User logged in successfully";

        return AuthResponse.builder()
                .message(message)
                .response(HttpStatus.OK)
                .token(token)
                .email(user.getEmail())
                .role(String.valueOf(user.getRole()))
                .build();
    }

//    public AuthResponse logout(HttpServletRequest request) {
//        // Get the token from the request headers
//        String token = request.getHeader("Authorization");
//
//        // Invalidate the token
//        jwtService.invalidateToken(token);
//
//        // Remove the token from the user's session
//        request.getSession().removeAttribute("token");
//
//        // Return a response indicating that the user has been logged out
//        return AuthResponse.builder()
//                .message("Logged out successfully")
//                .response(HttpStatus.OK)
//                .build();
//    }
}

package Sber.Sber.Controllers;

import Sber.Sber.repositories.UserRepository;
import Sber.Sber.services.UserService;
import Sber.Sber.models.User;
import Sber.Sber.common.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")


public class UserController {

    private final UserService userService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/byCompany/{companyname}")
    public ResponseEntity<List<UserRepository.UserProjection>> userListByCompany(@PathVariable String companyname) {
        return ResponseEntity.ok(userService.listUserByCompany(companyname));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse(true, "User deleted successfully. ID: " + id));
    }

//    @CrossOrigin(origins = "http://localhost:3000")
//    @PostMapping("/logout")
//    public ResponseEntity<ApiResponse> logout(HttpServletRequest request, HttpServletResponse response) {
//        // Удалить информацию о текущем пользователе из сессии
//        request.getSession().invalidate();
//
//        // Удалить токен авторизации из cookies
//        Cookie cookie = new Cookie("JSESSIONID", null);
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
//
//        return ResponseEntity.ok(new ApiResponse(true, "Logged out successfully"));
//    }
}
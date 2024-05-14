package lms.be.controllers;

import jakarta.validation.Valid;
import lms.be.dtos.*;
import lms.be.models.User;
import lms.be.responses.LoginResponse;
import lms.be.responses.UserResponse;
import lms.be.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody UserDTO userLoginDTO
    ) {
        // Kiểm tra thông tin đăng nhập và sinh token
        try {
            String token = userService.login(
                    userLoginDTO.getEmail(),
                    userLoginDTO.getPassword()
            );
            // Trả về token trong response
            return ResponseEntity.ok(LoginResponse.builder()
                    .message("Login Successfully")
                    .token(token)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    LoginResponse.builder()
                            .message("Login fail")
                            .build()
            );
        }
    }
    @PostMapping("/loginGoogle")
    public ResponseEntity<LoginResponse> loginGoogle(
            @Valid @RequestBody UserDTO userDTO
    ) {
        // Kiểm tra thông tin đăng nhập và sinh token
        try {
            String token = userService.loginGoogle(
                    userDTO.getEmail()
            );
            // Trả về token trong response
            return ResponseEntity.ok(LoginResponse.builder()
                    .message("Login Successfully")
                    .token(token)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    LoginResponse.builder()
                            .message("Login fail")
                            .build()
            );
        }
    }

    @PostMapping("/details")
    public ResponseEntity<UserResponse> getUserDetails(@RequestHeader("Authorization") String token) {
        try {
            String extractedToken = token.substring(7); // Loai bo Bearer
            User user = userService.getUserDetailsFromToken(extractedToken);
            return ResponseEntity.ok(UserResponse.fromUser(user));

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/details/{userId}")
    public ResponseEntity<UserResponse> updateUserDetails(
            @PathVariable int userId,
            @RequestBody UpdateUserDTO updateUserDTO,
            @RequestHeader ("Authorization") String authorizationHeader) {
        try {
            String extractedToken = authorizationHeader.substring(7);
            User user = userService.getUserDetailsFromToken(extractedToken);
            if (user.getUserId() != userId){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            User updatedUser = userService.updateUser(userId, updateUserDTO);
            return ResponseEntity.ok(UserResponse.fromUser(updatedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/teachers")
    public ResponseEntity<List<UserResponse>> getAllTeachers() {
        try {
            List<User> teachers = userService.getAllTeachers();

            return ResponseEntity.ok(UserResponse.fromUserList(teachers));

        }catch (Exception e) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }
    }
}

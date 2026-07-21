package id.my.agungdh.rakawikchat.controller;

import id.my.agungdh.rakawikchat.dto.response.ApiResponse;
import id.my.agungdh.rakawikchat.dto.response.UserResponse;
import id.my.agungdh.rakawikchat.security.UserContext;
import id.my.agungdh.rakawikchat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(
            @AuthenticationPrincipal UserContext userContext) {
        return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                .success(true)
                .data(userService.getCurrentUser(userContext))
                .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.<List<UserResponse>>builder()
                .success(true)
                .data(userService.getAllUsers())
                .build());
    }
}

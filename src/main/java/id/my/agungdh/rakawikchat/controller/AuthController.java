package id.my.agungdh.rakawikchat.controller;

import id.my.agungdh.rakawikchat.dto.request.LoginRequest;
import id.my.agungdh.rakawikchat.dto.response.ApiResponse;
import id.my.agungdh.rakawikchat.dto.response.LoginResponse;
import id.my.agungdh.rakawikchat.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.<LoginResponse>builder()
                .success(true)
                .message("Login successful")
                .data(response)
                .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        authService.logout(token);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Logout successful")
                .build());
    }
}

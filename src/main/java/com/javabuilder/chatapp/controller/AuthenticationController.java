package com.javabuilder.chatapp.controller;

import com.javabuilder.chatapp.dto.request.LoginRequest;
import com.javabuilder.chatapp.dto.response.ApiResponse;
import com.javabuilder.chatapp.dto.response.LoginResponse;
import com.javabuilder.chatapp.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> authenticate(@RequestBody @Valid LoginRequest request) {
        var data = authenticationService.login(request);

        return ApiResponse.<LoginResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Login successful")
                .data(data)
                .build();
    }

}

package com.javabuilder.chatapp.controller;

import com.javabuilder.chatapp.dto.request.CreateUserRequest;
import com.javabuilder.chatapp.dto.response.ApiResponse;
import com.javabuilder.chatapp.dto.response.CreateUserResponse;
import com.javabuilder.chatapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<CreateUserResponse> createUser(@RequestBody @Valid CreateUserRequest request) {
        var data = userService.createUser(request);

        return ApiResponse.<CreateUserResponse>builder()
                .code(HttpStatus.OK.value())
                .message("User created successfully")
                .data(data)
                .build();
    }

}

package com.example.userservice.controller;

import com.example.userservice.vo.UserResponse;
import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.example.userservice.vo.Hello;
import com.example.userservice.vo.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UsersController {
    private final Environment env;
    private final Hello hello;
    private final UserService userService;


    /**
     * 작동 상태 확인
     */
    @GetMapping("/health_check")
    public String status(HttpServletRequest request){
//        return String.format("Server port on %s",env.getProperty("local.server.port"));
        return String.format("Server port on %s",request.getServerPort());

    }

    // 방법 1. Enviroment 활용
//    @GetMapping("/hello")
//    public String hello(){
//        return env.getProperty("hello.message");
//    }

    /**
     * 환영 메시지
     */
    // 방법 2. Class 생성
    @GetMapping("/hello")
    public String hello(){
        return hello.getMessage();
    }


    /**
     * 회원 가입
     */
    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody SignupRequest user){
        ModelMapper modelMapper = new ModelMapper();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userService.createUser(userDto);

        UserResponse userResponse = modelMapper.map(userDto, UserResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }


    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> findAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> findUserByUserId(@PathVariable("userId") String userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByUserId(userId));
    }
}

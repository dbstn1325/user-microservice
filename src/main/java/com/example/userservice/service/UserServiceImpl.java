package com.example.userservice.service;

import com.example.userservice.client.OrderServiceClient;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.exception.UserIdNotFoundException;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.vo.OrderResponse;
import com.example.userservice.vo.UserResponse;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final RestTemplate restTemplate;
    private final OrderServiceClient orderServiceClient;
    private final ErrorDecoder errorDecoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString()); //랜덤 uuid 생성

        ModelMapper modelMapper = new ModelMapper();;
        User user = modelMapper.map(userDto, User.class);
        user.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        userRepository.save(user);

        UserDto userToDto = modelMapper.map(user, UserDto.class);

        return userToDto;
    }


    @Override
    public UserResponse getUserByUserId(String userId) {
        User findUser = userRepository.findByUserId(userId);

        if(findUser == null ){
            throw new UserIdNotFoundException("해당 유저는 존재하지 않습니다.");
        }

        ModelMapper modelMapper = new ModelMapper();

        UserResponse userResponse = modelMapper.map(findUser, UserResponse.class);
        System.out.println("1234" + userResponse.getEmail());

        /* 방법 1. RestTemplate 사용 */
//        String orderUrl = String.format("http://order-service/order-service/%s/orders", userId); //%s: 사용자의 id
//        ResponseEntity<List<OrderResponse>> orderListResponse = restTemplate
//                .exchange(orderUrl, HttpMethod.GET,
//                        null, //parameter 전달값
//                        new ParameterizedTypeReference<List<OrderResponse>>() {});
//        List<OrderResponse> orderResult = orderListResult.get();

        /* 방법 2. FeignClient 사용 */


//        try {
//            orderResult = orderServiceClient.getOrders(userId);
//        }catch(FeignException ex){
//            log.error(ex.getMessage());
//        }
        List<OrderResponse> orderResult = orderServiceClient.getOrders(userId);
        System.out.println("helllo" + userResponse.getEmail());
        userResponse.setOrders(orderResult);

        return userResponse;
    }

    @Override
    public List<UserResponse> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserResponse> findUsers = new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();
        users.forEach(v-> findUsers.add( modelMapper.map(v, UserResponse.class)));
        return findUsers;
    }

    /**
     * 사용자가 로그인을 할 때, 그 값에 대한 체크를 해주기 위한 메서드입니다.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username); //username := email

        if(user == null){
            throw new UsernameNotFoundException(username);
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncryptedPassword(),
                true, true, true, true,
                new ArrayList<>());
//
//        return null;
    }
}

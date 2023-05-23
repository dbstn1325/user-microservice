package com.example.userservice.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FeignErrorDecoder implements ErrorDecoder {

    //Feign의 ErrorDecoder 내 인터페이스를 구현하겠습니다.
    @Override
    public Exception decode(String methodKey, Response response) {
        switch(response.status()){
            case 400:
                break;
            case 404:
                if(methodKey.contains("getOrders")){               //특정 메서드 명
                    return new ResponseStatusException(
                            HttpStatus.valueOf(response.status()), //response의 상태코드
                            "User's order is empty"                //보내고자 하는 message
                    );
                }
                break;
            default:
                return new Exception(response.reason());
        }

        return null;
    }
}

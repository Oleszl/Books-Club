package com.excellentbook.excellentbook.aspect;

import com.excellentbook.excellentbook.dto.user.UserDtoResponse;
import com.excellentbook.excellentbook.exception.InvalidJWTTokenException;
import com.excellentbook.excellentbook.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class SecurityAspect {
    private final UserService userService;

    public SecurityAspect(UserService userService) {
        this.userService = userService;
    }

    @Before("(execution(* com.excellentbook.excellentbook.service.UserService.getPersonalUserBooksByStatus(..)) " +
            "|| execution(* com.excellentbook.excellentbook.service.UserService.getUserBooksByStatus(..)) " +
            "|| execution(* com.excellentbook.excellentbook.service.UserService.updateUserById(..)) " +
            "|| execution(* com.excellentbook.excellentbook.service.UserService.updateUserPhoto(..)) " +
            "|| execution(* com.excellentbook.excellentbook.service.UserService.updateUserAddress(..)) " +
            ") && args(userId, status)")
    public void logOperations(JoinPoint joinPoint, Long userId, String status) {
        UserDtoResponse user = userService.getUser();
        if (user.getId() != userId){
            throw new InvalidJWTTokenException("You do not have permission to perform this action");
        }
    }

}

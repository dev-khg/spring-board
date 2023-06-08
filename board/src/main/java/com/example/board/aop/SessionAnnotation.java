package com.example.board.aop;

import com.example.board.common.exception.UnAuthorizationRuntimeException;
import com.example.board.utils.SessionUtils;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class SessionAnnotation {

    @Before("@annotation(com.example.board.aop.annotation.SessionCheck)")
    public void sessionCheck(JoinPoint joinPoint) {
        HttpSession httpSession = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes())
                .getRequest()
                .getSession();

        Long memberId = SessionUtils.getSessionValue(httpSession);
        if (memberId == null) {
            throw new UnAuthorizationRuntimeException(HttpStatus.FORBIDDEN,"세션이 존재하지 않습니다.");
        }
    }
}

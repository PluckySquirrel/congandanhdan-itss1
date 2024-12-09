package vn.edu.hust.soict.japango.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class RepositoryLoggingAspect {
    @Around("execution(* vn.edu.hust.soict.japango.repository.*.*(..))")
    public Object logRepository(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        log.info("---------> Request: {} called with arguments: {}", methodName, Arrays.toString(args));

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("X--------- Exception: {} threw an exception: {}", methodName, throwable.getMessage());
            throw throwable;
        }

        log.info("<--------- Response: {} returned: {}", methodName, result);
        return result;
    }
}

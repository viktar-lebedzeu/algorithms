package org.example.algorithms.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Viktar Lebedzeu
 */
@Aspect
public class LogMethodTimeAspect {
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(LogMethodTimeAspect.class);

    @Pointcut(value = "execution(* org.example.algorithms..*(..)) && @annotation(logMethodTime)")
    public void logMethodTimePointcut(LogMethodTime logMethodTime) {
    }


    @Around(value = "logMethodTimePointcut(LogMethodTime) && @annotation(LogMethodTime) && @annotation(logMethodTime)")
    // @Around(value = "logMethodTimePointcut()")
    public Object logMethodTime(ProceedingJoinPoint joinPoint, LogMethodTime logMethodTime) throws Throwable {
        long start = System.currentTimeMillis();

        logger.debug("Entered into {} ", joinPoint.getSignature());

        Object[] args = joinPoint.getArgs();
        logger.debug("Args: {} [{}]", args, args.length);

        /*
        Object target = joinPoint.getTarget();
        Object pointThis = joinPoint.getThis();
        JoinPoint.StaticPart staticPart = joinPoint.getStaticPart();
        */
        Object proceed = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;

        logger.debug("{} executed in {}ms", joinPoint.getSignature(), executionTime);
        return proceed;
    }
}

package org.example.algorithms.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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

    // @Pointcut(value = "@annotation(LogMethodTime)")
    // @Pointcut(value = "@annotation(LogMethodTime) && execution(* org.example.algorithms..*(..))")
    @Pointcut(value = "@annotation(LogMethodTime) && execution(* *(..))")
    public void logMethodTimeAnnotationPointcut() {
    }

    @Around(value = "@annotation(logMethodTime) && logMethodTimeAnnotationPointcut()")
    public Object logMethodTime(final LogMethodTime logMethodTime, final ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        // logger.debug("Entered into {} ", joinPoint.getSignature());

        // Object[] args = joinPoint.getArgs();
        // logger.debug("Args: {} [{}]", args, args.length);

        /*
        Object target = joinPoint.getTarget();
        Object pointThis = joinPoint.getThis();
        JoinPoint.StaticPart staticPart = joinPoint.getStaticPart();
        */
        Object proceed = joinPoint.proceed();

        logger.debug("proceed = {}", joinPoint.getSignature());
        long executionTime = System.currentTimeMillis() - start;

        logger.debug("{} executed in {}ms", joinPoint.getSignature(), executionTime);
        return proceed;
    }
}

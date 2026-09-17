package com.dtorrez.main.common.aop;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LogginAspect {

    private final Logger log = LogManager.getLogger(LogginAspect.class);

    @Around("execution(* bo.com.micrium.modulobase.modulos.producto.services.presentacion.list..*(..))")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        log.info("Entering: {}", joinPoint.getSignature());

        try {

            Object result = joinPoint.proceed();

            long time = System.currentTimeMillis() - start;

            log.info("Exiting: {} ({} ms)",
                    joinPoint.getSignature(),
                    time);

            return result;

        } catch (Exception ex) {

            log.error("Exception in {} : {}",
                    joinPoint.getSignature(),
                    ex.getMessage());

            throw ex;
        }
    }

}

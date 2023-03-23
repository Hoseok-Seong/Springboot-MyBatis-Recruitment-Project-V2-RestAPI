package shop.mtcoding.job.config.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CurrnetIdAdvice {
    @Pointcut("@annotation(shop.mtcoding.job.config.aop.CurrnetId)")
    public void CurrnetId() {
    }

    @Around("CurrnetId()")
    public Object currnetIdAdvice(ProceedingJoinPoint jp) throws Throwable { // ProceedingJoinPoint는 메서드 정보를 갖고 있다
        Object[] args = jp.getArgs();
        System.out.println("Parameter size: " + args.length);

        for (Object arg : args) {
            if (arg instanceof String) {
                String username = (String) arg;
                System.out.println(username + "님 안녕하세요");
            }
        }
        return jp.proceed();
    }
}

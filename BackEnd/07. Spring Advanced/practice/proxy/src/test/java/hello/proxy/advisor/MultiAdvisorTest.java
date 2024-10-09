package hello.proxy.advisor;

import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

@Slf4j
public class MultiAdvisorTest {

    @Test
    void multiAdvisorTest1() {
        //client -> proxy2 (Advisor2) -> proxy1 (Advisor1) -> target
        ServiceInterface target = new ServiceImpl();

        ProxyFactory proxyFactory1 = new ProxyFactory(target);
        Advisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
        proxyFactory1.addAdvisor(advisor1);
        ServiceInterface proxy1 = (ServiceInterface) proxyFactory1.getProxy();

        ProxyFactory proxyFactory2 = new ProxyFactory(proxy1);
        Advisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());
        proxyFactory2.addAdvisor(advisor2);
        ServiceInterface proxy2 = (ServiceInterface) proxyFactory2.getProxy();

        proxy2.save();
        proxy2.find();
    }

    @Test
    void multiAdvisorTest2() {
        ServiceInterface target = new ServiceImpl();
        Advisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
        Advisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());

        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvisor(advisor2);
        proxyFactory.addAdvisor(advisor1);

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }


    static class Advice1 implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("advice1 호출");
            return invocation.proceed();
        }
    }

    static class Advice2 implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("advice2 호출");
            return invocation.proceed();
        }
    }
}

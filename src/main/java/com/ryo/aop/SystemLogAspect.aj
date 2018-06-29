package com.ryo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
@Order(1000)
public class SystemLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    //切入点
    @Pointcut("execution(* com.ryo.controller.*.*(..))")
   /* @Pointcut("@annotation(com.ryo.aop.SystemLog)")*/
    public void controllerAspect() {
    }

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("==========开始执行controller环绕通知===============");
        long start = System.currentTimeMillis();
        try {
            ((ProceedingJoinPoint) joinPoint).proceed();
            long end = System.currentTimeMillis();
            if (logger.isInfoEnabled()) {
                logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms!");
            }
            System.out.println("==========结束执行controller环绕通知===============");
        } catch (Throwable e) {
            long end = System.currentTimeMillis();
            if (logger.isInfoEnabled()) {
                logger.info("around " + joinPoint + "\tUse time : " + (end - start) + " ms with exception : " + e.getMessage());
            }
        }

    }

    @After("controllerAspect()")
    public void after(JoinPoint joinPoint) {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        try
        {
            Object[] arguments = joinPoint.getArgs();
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            String operationType = "";
            String operationName = "";
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        operationType = method.getAnnotation(SystemLog.class).description();
                        operationName = method.getAnnotation(SystemLog.class).operationName();
                        break;
                    }
                }
            }
            //*========控制台输出=========*//
            System.out.println("=====controller后置通知开始=====");
            System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()") + "." + operationType);
            System.out.println("方法描述:" + operationName);
        }
        catch (Exception ex)
        {
            //记录本地异常日志
            logger.error("==异常通知异常==");
            logger.error("异常信息:{}", ex.getMessage());

        }


    }

    //在执行目标方法的过程中，会执行这个方法，可以在这里实现日志的记录
    @Around("controllerAspect()")
    public Object aroundExec(ProceedingJoinPoint pjp) throws Throwable {
        Object ret = pjp.proceed();
        try {

            logger.info("执行方法中");
            //保存操作结果
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    //记录异常日志
    @AfterThrowing(pointcut = "controllerAspect()",throwing="e")
    public  void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        try {
            info(joinPoint);
            logger.error("错误"+e.getMessage());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void info(JoinPoint joinPoint) {
        logger.debug("--------------------------------------------------");
        logger.debug("King:\t" + joinPoint.getKind());
        logger.debug("Target:\t" + joinPoint.getTarget().toString());
        Object[] os = joinPoint.getArgs();
        logger.debug("Args:");
        for (int i = 0; i < os.length; i++) {
            logger.debug("\t==>参数[" + i + "]:\t" + os[i].toString());
        }
        logger.debug("Signature:\t" + joinPoint.getSignature());
        logger.debug("SourceLocation:\t" + joinPoint.getSourceLocation());
        logger.debug("StaticPart:\t" + joinPoint.getStaticPart());
        logger.debug("--------------------------------------------------");
    }
    /**
     * 获取远程客户端Ip
     * @param request
     * @return
     */
    private  String getRemoteHost(javax.servlet.http.HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
    }
}

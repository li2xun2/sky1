package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class aspectAutoFill {
      @Pointcut("execution(* com.sky.mapper.*.*(..))&&@annotation(com.sky.annotation.AutoFill)")
      public void autoFillPointCut(){};

      @Before("autoFillPointCut()")
      public void autoFillBefore(JoinPoint joinPoint)  {
            log.info("开始拦截{}",joinPoint);
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);
            OperationType value = annotation.value();

            Object[] args = joinPoint.getArgs();
            if (args.length == 0 || args == null)
            {
                  return;
            }
            Object employee = args[0];
            // 获取时间 和 id
            LocalDateTime now = LocalDateTime.now();
            Long currentId = BaseContext.getCurrentId();

            if(value==OperationType.INSERT)
            {

                  try {
                        Method  createTime = employee.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                        Method createUser = employee.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                        Method updateTime = employee.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);

                        Method updateUser = employee.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                        createTime.invoke(employee,now);
                        createUser.invoke(employee,currentId);
                        updateTime.invoke(employee,now);
                        updateUser.invoke(employee,currentId);
                  } catch (Exception e) {
                        throw new RuntimeException(e);
                  }

            }else {


                  try {
                        Method  updateUser = employee.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);
                        Method  updateTime = employee.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);

                        updateTime.invoke(employee,now);
                        updateUser.invoke(employee,currentId);
                  } catch (Exception e) {
                        throw new RuntimeException(e);
                  }



            }

}

}

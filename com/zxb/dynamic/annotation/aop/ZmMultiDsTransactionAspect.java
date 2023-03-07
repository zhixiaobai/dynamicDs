package com.zxb.dynamic.annotation.aop;

import com.zxb.dynamic.connection.ZmConnection;
import com.zxb.dynamic.context.TransactionContext;
import com.zxb.dynamic.core.MultiDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mr.M
 * @date 2023/3/7
 * @Description
 */
@Aspect
@Configuration
public class ZmMultiDsTransactionAspect {
    @Pointcut("@annotation(com.zxb.dynamic.annotation.ZmMultiDsTransaction)")
    public void transactPoint() {}

    @Around("transactPoint()")
    public Object multiTranAop(ProceedingJoinPoint joinPoint) throws Throwable {
        // 开启事务
        TransactionContext.openTransaction();
        try {
            // 执行业务
            Object proceed = joinPoint.proceed();
            // 提交事务
            for (ZmConnection connection : MultiDataSource.MULTI_TRAN_CONNECTION.get()) {
                connection.commitMultiDbTran();
                connection.closeMultiDbTran();
            }
            return proceed;
        } catch (Throwable t) {
            t.printStackTrace();
            for (ZmConnection connection : MultiDataSource.MULTI_TRAN_CONNECTION.get()) {
                // 事务回滚
                connection.rollback();
                connection.closeMultiDbTran();
            }
            throw t;
        } finally {
            // 清空 事务 连接，关闭当前事务
            MultiDataSource.MULTI_TRAN_CONNECTION.get().clear();
            TransactionContext.closeTransaction();
        }
    }
}

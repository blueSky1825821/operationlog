package com.beacon.operationlog.start.support.aop;

import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * <p>
 * 配置日志注解方法
 * </p>
 *
 * @author miaoyj
 * @version 1.0.0-SNAPSHOT
 * @since 2021-04-30
 */
public class LogRecordPointcut extends StaticMethodMatcherPointcut implements Serializable {


    private LogRecordOperationSource logRecordOperationSource;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean matches(@NotNull Method method, @NotNull Class<?> targetClass) {
        return ObjectUtils.isNotEmpty(logRecordOperationSource.computeLogRecordOperation(method, targetClass));
    }

    void setLogRecordOperationSource(LogRecordOperationSource logRecordOperationSource) {
        this.logRecordOperationSource = logRecordOperationSource;
    }
}

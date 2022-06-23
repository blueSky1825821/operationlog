package com.beacon.operationlog.start.annotation;

import com.beacon.operationlog.start.support.LogRecordConfigureSelector;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * DESCRIPTION:
 * <P>
 * 如何使用enable注解
 * @link https://juejin.cn/post/7055664320616595492
 * </p>
 *
 * @author wangmin
 * @since 2022-04-20 14:29
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LogRecordConfigureSelector.class)
public @interface EnableLogRecord {

    /**
     * <p>
     * 租户
     * </p>
     *
     * @return /
     */
    String tenant() default "1";

    /**
     * <p>
     * 代理方式
     * </p>
     *
     * @return /
     */
    AdviceMode mode() default AdviceMode.PROXY;

}

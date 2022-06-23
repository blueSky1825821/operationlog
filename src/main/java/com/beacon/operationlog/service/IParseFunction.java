package com.beacon.operationlog.service;

/**
 * DESCRIPTION:
 * <P>
 * </p>
 *
 * @author wangmin
 * @since 2022-04-20 14:07
 */
public interface IParseFunction<T> {
    /**
     * <p>
     * 是否在方法前运行
     * </p>
     *
     * @return /
     */
    default boolean executeBefore() {
        return false;
    }

    /**
     * <p>
     * 方法名
     * </p>
     *
     * @return 方法名
     */
    String functionName();

    /**
     * <p>
     * 方法回调
     * </p>
     *
     * @param value 参数
     * @return 结果
     */
    String apply(T value);
}

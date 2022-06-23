package com.beacon.operationlog.service;

/**
 * DESCRIPTION:
 * <P>
 * </p>
 *
 * @author wangmin
 * @since 2022-04-20 14:13
 */
public interface IFunctionService {
    /**
     * <p>
     * 方法回调
     * </p>
     *
     * @param functionName 方法名
     * @param value        参数
     * @return 结果
     */
    String apply(String functionName, Object value);

    /**
     * <p>
     * 是否方法前运行
     * </p>
     *
     * @param functionName 方法名
     * @return /
     */
    boolean beforeFunction(String functionName);
}

package com.beacon.operationlog.service.impl;

import com.beacon.operationlog.service.IParseFunction;

/**
 * DESCRIPTION:
 * <P>
 * </p>
 *
 * @author wangmin
 * @since 2022-04-23 10:00
 */
public class DefaultParseFunction implements IParseFunction<Object> {


    @Override
    public boolean executeBefore() {
        return IParseFunction.super.executeBefore();
    }

    @Override
    public String functionName() {
        return null;
    }

    @Override
    public String apply(Object value) {
        return null;
    }
}

package com.beacon.operationlog.service.impl;

import com.beacon.operationlog.service.IParseFunction;
import org.springframework.stereotype.Component;

/**
 * DESCRIPTION:
 * <P>
 * </p>
 *
 * @author wangmin
 * @since 2022/6/22 20:33
 */
@Component
public class TestParseParseFunction implements IParseFunction<Object> {
    @Override
    public boolean executeBefore() {
        return IParseFunction.super.executeBefore();
    }

    @Override
    public String functionName() {
        return "getTestParse";
    }

    @Override
    public String apply(Object value) {
        if (value == null) {
            return "null";
        }
        return "hi:" + value.toString();
    }
}

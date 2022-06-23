package com.beacon.operationlog.service.impl;


import com.beacon.operationlog.biz.Operator;
import com.beacon.operationlog.service.IOperatorGetService;

/**
 * <p>
 * 缺省操作人
 * </p>
 *
 * @author miaoyj
 * @version 1.0.0-SNAPSHOT
 * @since 2021-04-30
 */
public class DefaultOperatorGetServiceImpl implements IOperatorGetService {

    /**
     * {@inheritDoc}
     */
    @Override
    public Operator getUser() {
        Operator operator = new Operator();
        operator.setOperatorId("默认用户");
        return operator;
    }
}

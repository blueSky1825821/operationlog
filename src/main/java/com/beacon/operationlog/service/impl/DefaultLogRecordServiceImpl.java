package com.beacon.operationlog.service.impl;

import com.beacon.operationlog.biz.LogRecord;
import com.beacon.operationlog.service.ILogRecordService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * DESCRIPTION:
 * <P>
 * </p>
 *
 * @author wangmin
 * @since 2022-04-20 14:27
 */
@Slf4j
public class DefaultLogRecordServiceImpl implements ILogRecordService {
    @Override
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void record(LogRecord logRecord) {
        log.info("【logRecord】log={}", logRecord);
    }

    @Override
    public List<LogRecord> queryLog(String bizKey) {
        return null;
    }

    @Override
    public List<LogRecord> queryLogByBizNo(String bizNo) {
        return null;
    }
}

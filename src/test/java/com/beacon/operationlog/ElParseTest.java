package com.beacon.operationlog;

import com.beacon.operationlog.biz.LogRecord;
import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * DESCRIPTION:
 * <P>
 * </p>
 *
 * @author wangmin
 * @since 2022-04-20 15:00
 */
public class ElParseTest {
    @Test
    public void test() {
        SpelExpressionParser parser = new SpelExpressionParser();
//        Expression expression = parser.parseExpression("#root.bizKey + 1 + #root.bizKey");
        Expression expression = parser.parseExpression("1 + 1");
        LogRecord record = new LogRecord();
        record.setBizKey("test");
        System.out.println(expression.getValue(record));

    }
}

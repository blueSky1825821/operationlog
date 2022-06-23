package com.beacon.operationlog.controller;

import com.beacon.operationlog.service.IOperatorGetService;
import com.beacon.operationlog.common.enums.LogCategoryType;
import com.beacon.operationlog.start.annotation.LogRecordAnnotation;
import com.beacon.operationlog.context.LogRecordContext;
import com.beacon.operationlog.common.enums.LogType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * DESCRIPTION:
 * <P>
 * </p>
 *
 * @author wangmin
 * @since 2022/6/21 21:03
 */
@RestController
public class TestController {
    @Resource
    private IOperatorGetService operatorGetService;

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test() {
        return "test";
    }

    @RequestMapping(method = RequestMethod.GET)
    @LogRecordAnnotation(
            value = "@1+1", category = LogCategoryType.MANAGER,
            type = LogType.ADD, module = "版本", testParse = "@\"修改\" + (1+1) + #testParse  + {getTestParse{#testParse}}"
    )
    public String index(@RequestParam(name = "testParse") String testParse) {
        LogRecordContext.putVariable("getUser", operatorGetService.getUser());
//        LogRecordContext.putVariable("getTestParse", operatorGetService.getTestParse(testParse));
        return testParse;
    }

    public String getTestParse(String testParse) {
        return "hello:" + testParse;
    }
}

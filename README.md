- 操作日志
- 使用注解及SpEL设置操作日志处理


```java
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
```


- 参考 https://tech.meituan.com/2021/09/16/operational-logbook.html
- https://github.com/miaoyinjun/jjche-boot/blob/ec14441ac5c022628aed25f4b57810ec721ba473/jjche-boot/jjche-boot-parent/jjche-boot-eladmin/jjche-boot-eladmin-system/src/main/java/org/jjche/system/modules/mnt/rest/DeployController.java
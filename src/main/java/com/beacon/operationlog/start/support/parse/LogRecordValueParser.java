package com.beacon.operationlog.start.support.parse;

import com.beacon.operationlog.service.IFunctionService;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * DESCRIPTION:
 * <P>
 * </p>
 *
 * @author wangmin
 * @since 2022-04-23 10:16
 */
public class LogRecordValueParser implements BeanFactoryAware {
    /**
     * {query{#userCode}}
     */
    private static final Pattern PATTERN = Pattern.compile("\\{\\s*(\\w*)\\s*\\{(.*?)}}");
    private final LogRecordExpressionEvaluator expressionEvaluator = new LogRecordExpressionEvaluator();
    protected BeanFactory beanFactory;
    private IFunctionService functionService;

    /**
     * <p>
     * 解析日志表达式模板
     * </p>
     *
     * @param templates                      模板
     * @param ret                            结果
     * @param targetClass                    目的类
     * @param method                         方法
     * @param args                           对象
     * @param errorMsg                       错误 信息
     * @param beforeFunctionNameAndReturnMap a {@link Map} object.
     *                                                                             todo 模版处理
     * @return map
     */
    public Map<String, String> processTemplate(Collection<String> templates, Object ret,
                                               Class<?> targetClass, Method method, Object[] args,
                                               String errorMsg, Map<String, String> beforeFunctionNameAndReturnMap) {
        Map<String, String> expressionValues = Maps.newHashMap();
        EvaluationContext evaluationContext = expressionEvaluator.createEvaluationContext(method, args, targetClass, ret, errorMsg, beanFactory);
        AnnotatedElementKey annotatedElementKey = new AnnotatedElementKey(method, targetClass);
        for (String expressionTemplate : templates) {
            //@为了标记是SpEL，减少不必要的计算
            if (StringUtils.isNotBlank(expressionTemplate)) {
                if (expressionTemplate.startsWith("@")) {
                    String expressionNotAit = expressionTemplate.replaceFirst("@", "");
                    StringBuffer parsedStr = new StringBuffer();
                    Matcher matcher = PATTERN.matcher(expressionNotAit);
                    while (matcher.find()) {
                        String expression = matcher.group(2);
                        Object value = expressionEvaluator.parseExpression(expression, annotatedElementKey, evaluationContext);
                        String functionReturnValue = getFunctionReturnValue(beforeFunctionNameAndReturnMap, value, matcher.group(1));
                        //字符串转为SpEL格式
                        matcher.appendReplacement(parsedStr, StringUtils.isBlank(functionReturnValue) ? "" : "\"" + functionReturnValue + "\"");
                    }
                    matcher.appendTail(parsedStr);
                    Object expressionResult = expressionEvaluator.parseExpression(parsedStr.toString(), annotatedElementKey, evaluationContext);
                    if (!Objects.isNull(expressionResult)) {
                        expressionValues.put(expressionTemplate, expressionResult.toString());
                    }
                } else {
                    expressionValues.put(expressionTemplate, expressionTemplate);
                }
            }
        }
        return expressionValues;
    }

    /**
     * <p>
     * 处理方法执行前
     * </p>
     *
     * @param templates   模板
     * @param targetClass 目标类
     * @param method      方法
     * @param args        参数
     * @return /
     */
    public Map<String, String> processBeforeExecuteFunctionTemplate(Collection<String> templates, Class<?> targetClass, Method method, Object[] args) {
        Map<String, String> functionNameAndReturnValueMap = Maps.newHashMap();
        EvaluationContext evaluationContext = expressionEvaluator.createEvaluationContext(method, args, targetClass, null, null, beanFactory);
        AnnotatedElementKey annotatedElementKey = new AnnotatedElementKey(method, targetClass);

        for (String expressionTemplate : templates) {
            if (StringUtils.isNotBlank(expressionTemplate)) {
                //@为了标记是SpEL，减少不必要的计算
                if (expressionTemplate.startsWith("@")) {
                    String expressionNotAit = expressionTemplate.replaceFirst("@", "");
                    Matcher matcher = PATTERN.matcher(expressionNotAit);
                    while (matcher.find()) {
                        String expression = matcher.group(2);
                        if (expression.contains("#_ret") || expression.contains("#_errorMsg")) {
                            continue;
                        }
                        String functionName = matcher.group(1);
                        if (functionService.beforeFunction(functionName)) {
                            Object value = expressionEvaluator.parseExpression(expression, annotatedElementKey, evaluationContext);
                            String functionReturnValue = getFunctionReturnValue(null, value, functionName);
                            functionNameAndReturnValueMap.put(functionName, functionReturnValue);
                        }
                    }
                }
            }
        }
        return functionNameAndReturnValueMap;
    }

    private String getFunctionReturnValue(Map<String, String> beforeFunctionNameAndReturnMap, Object value, String functionName) {
        String functionReturnValue = "";
        if (beforeFunctionNameAndReturnMap != null) {
            functionReturnValue = beforeFunctionNameAndReturnMap.get(functionName);
        }
        if (StringUtils.isBlank(functionReturnValue)) {
            functionReturnValue = functionService.apply(functionName, String.valueOf(value));
        }
        return functionReturnValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBeanFactory(@NotNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * <p>Setter for the field <code>functionService</code>.</p>
     *
     * @param functionService a {@link IFunctionService} object.
     */
    public void setFunctionService(IFunctionService functionService) {
        this.functionService = functionService;
    }
}

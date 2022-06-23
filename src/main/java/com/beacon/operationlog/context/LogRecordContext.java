package com.beacon.operationlog.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Stack;

/**
 * DESCRIPTION:
 * <P>
 * </p>
 *
 * @author wangmin
 * @since 2022-04-21 10:09
 */
public class LogRecordContext {
    private static final TransmittableThreadLocal<Stack<Map<String, Object>>> VARIABLE_MAP_STACK = new TransmittableThreadLocal<>();

    /**
     * <p>putVariable.</p>
     *
     * @param name  a {@link String} object.
     * @param value a {@link Object} object.
     */
    public static void putVariable(String name, Object value) {
        if (VARIABLE_MAP_STACK.get() == null) {
            Stack<Map<String, Object>> stack = new Stack<>();
            VARIABLE_MAP_STACK.set(stack);
        }
        Stack<Map<String, Object>> mapStack = VARIABLE_MAP_STACK.get();
        if (mapStack.size() == 0) {
            VARIABLE_MAP_STACK.get().push(Maps.newHashMap());
        }
        VARIABLE_MAP_STACK.get().peek().put(name, value);
    }

    /**
     * <p>getVariable.</p>
     *
     * @param key a {@link String} object.
     * @return a {@link Object} object.
     */
    public static Object getVariable(String key) {
        Map<String, Object> variableMap = VARIABLE_MAP_STACK.get().peek();
        return variableMap.get(key);
    }

    /**
     * <p>getVariables.</p>
     *
     * @return a {@link Map} object.
     */
    public static Map<String, Object> getVariables() {
        Stack<Map<String, Object>> mapStack = VARIABLE_MAP_STACK.get();
        return mapStack.peek();
    }

    /**
     * <p>clear.</p>
     */
    public static void clear() {
        if (VARIABLE_MAP_STACK.get() != null) {
            VARIABLE_MAP_STACK.get().pop();
        }
    }

    /**
     * 日志使用方不需要使用到这个方法
     * 每进入一个方法初始化一个 span 放入到 stack中，方法执行完后 pop 掉这个span
     */
    public static void putEmptySpan() {
        Stack<Map<String, Object>> mapStack = VARIABLE_MAP_STACK.get();
        if (mapStack == null) {
            Stack<Map<String, Object>> stack = new Stack<>();
            VARIABLE_MAP_STACK.set(stack);
        }
        VARIABLE_MAP_STACK.get().push(Maps.newHashMap());
    }
}

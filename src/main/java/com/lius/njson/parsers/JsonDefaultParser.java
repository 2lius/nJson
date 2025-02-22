package com.lius.njson.parsers;

import com.lius.njson.beans.JsonArray;
import com.lius.njson.beans.JsonObject;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.regex.Pattern;

class JsonDefaultParser {
    private static final Stack<Object> stack = new Stack<>();
    private static final AtomicReference<StringBuffer> sb = new AtomicReference<>(null);
    private static final Pattern numberPattern = Pattern.compile("^[+-]?([0-9]+|[0-9]+\\.[0-9]+)$");
    private static final StringBuffer container = new StringBuffer();
    private static Object numberHandler(String value) {
        return new BigDecimal(value);
    }

    private static StringBuffer getContainer(){
        container.setLength(0);
        return container;
    }
    private static StringBuffer getContainer(char data){
        getContainer();
        container.append(data);
        return container;
    }

    private static Object dynamicValue(Object value){
        if(value instanceof String){
            String str = (String) value;
            if(str.startsWith("\"")&&str.endsWith("\"")){
                return str.substring(1,str.length()-1);
            }else if(str.equals("true")){
                return true;
            }else if(str.equals("false")){
                return false;
            }else if(str.equals("null")){
                return null;
            }else if (numberPattern.matcher(str).find()){
                return numberHandler(str);
            }else{
                return str;
            }
        }
        return value;
    }

    private static void putValue(Object value) {
        value = dynamicValue(value);
        if(stack.peek() instanceof String){
            Object key = stack.pop();
            key = dynamicValue(key);
            ((JsonObject)stack.peek()).put(key,value);
        }else{
            ((JsonArray)stack.peek()).add(value);
        }
    }

    public static <T> T convertObject(Object fieldValue, Class<T> clazz) {
        if (fieldValue.getClass().isAssignableFrom(clazz)){
            return (T)fieldValue;
        }else{
            if (fieldValue instanceof JsonObject){
                fieldValue = ((JsonObject) fieldValue).toJavaObject(clazz);
            }else if (fieldValue instanceof BigDecimal) {
                if (clazz.isAssignableFrom(Integer.class)){
                    fieldValue = ((BigDecimal) fieldValue).intValue();
                }else if (clazz.isAssignableFrom(Long.class)){
                    fieldValue = ((BigDecimal) fieldValue).longValue();
                }else if (clazz.isAssignableFrom(Short.class)){
                    fieldValue = ((BigDecimal) fieldValue).shortValue();
                }else if (clazz.isAssignableFrom(Byte.class)){
                    fieldValue = ((BigDecimal) fieldValue).byteValue();
                }else if (clazz.isAssignableFrom(Float.class)){
                    fieldValue = ((BigDecimal) fieldValue).floatValue();
                }else if (clazz.isAssignableFrom(Double.class)){
                    fieldValue = ((BigDecimal) fieldValue).doubleValue();
                } else if (clazz.isAssignableFrom(Boolean.class)) {
                    fieldValue = ((BigDecimal) fieldValue).intValue()==0;
                }
            }
        }
        return (T)fieldValue;
    }

    public static JsonObject parseObject(String json){
        Object parseResult = parse(json);
        if (parseResult instanceof JsonObject){
            return (JsonObject) parseResult;
        }
        throw new RuntimeException("解析失败！！");
    }

    public static JsonArray parseArray(String json){
        Object parseResult = parse(json);
        if (parseResult instanceof JsonArray){
            return (JsonArray) parseResult;
        }
        throw new RuntimeException("解析失败！！");
    }

    public static Object parse(String json) {
        char[] charArray = json.toCharArray();
        Object jsonData = null;
        Supplier<Object> getValue = () -> {
            if (Objects.isNull(sb.get())) {
                return stack.pop();
            }
            String result = sb.get().toString();
            sb.set(null);
            return result;
        };
        for (int i = 0; i < charArray.length; i++) {
            char currentChar = charArray[i];
            boolean isAppend = Objects.nonNull(sb.get())&&sb.get().length()>0&&sb.get().charAt(0) == '"';
            if (isAppend&&currentChar!='"'){
                sb.get().append(currentChar);
            }else{
                switch (currentChar){
                    case '{':
                        stack.push(new JsonObject());
                        break;
                    case '}':
                        Object popData = getValue.get();
                        putValue(popData);
                        if(stack.size()==1){
                            popData = stack.pop();
                            jsonData = popData;
                        }
                        break;
                    case '[':
                        stack.push(new JsonArray());
                        break;
                    case ']':
                        popData = getValue.get();
                        putValue(popData);
                        if(stack.size()==1){
                            popData = stack.pop();
                            jsonData = popData;
                        }
                        break;
                    case ':':
                        if(Objects.isNull(sb.get())){
                            sb.set(getContainer());
                        }
                        break;
                    case ',':
                        Object val = getValue.get();
                        putValue(val);
                        break;
                    case '"':
                        if(Objects.isNull(sb.get())){
                            sb.set(getContainer('"'));
                        }else if(sb.get().length()>0&&sb.get().charAt(0)=='"'){
                            sb.get().append("\"");
                            stack.push(sb.get().toString());
                            sb.set(null);
                        }
                        break;
                    default:
                        if (sb.get()==null){
                            if(currentChar!=' '&&currentChar!='\n'&&currentChar!='\r'){
                                sb.set(getContainer(currentChar));
                            }
                        }else{
                            sb.get().append(currentChar);
                        }
                        break;
                }
            }
        }
        return jsonData;
    }
}

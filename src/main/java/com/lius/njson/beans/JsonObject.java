package com.lius.njson.beans;


import com.lius.njson.parsers.JSON;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.lius.njson.parsers.JSON.convertObject;


public class JsonObject extends HashMap<Object, Object> {

    public <T> T toJavaObject(Class<T> clazz) {
        if (clazz == Map.class) {
            return (T) this;
        }

        if (clazz == Object.class && !containsKey(JSON.DEFAULT_TYPE_KEY)) {
            return (T) this;
        }

        if (clazz.isInstance(this)) {
            return (T) this;
        }
        try {
            Field[] fields = clazz.getDeclaredFields();
            T result = clazz.newInstance();
            for (Field field : fields) {
                String fieldName = field.getName();
                if (containsKey(fieldName)) {
                    field.setAccessible(true);
                    Object fieldValue = get(fieldName);
                    fieldValue = convertObject(fieldValue, field.getType());
                    field.set(result,fieldValue);
                }
            }
            return result;
        }catch (Exception e){
            e.printStackTrace(System.err);
        }
        return null;
    }

    private String valueHandle(Object value){
        if(Objects.nonNull(value)){
            return value instanceof String?String.format("\"%s\"",value): value.toString();
        }
        return "null";
    }

    public String toString() {
        String entryStr = this.entrySet().stream().map(entry -> String.format("\"%s\":%s", entry.getKey(), valueHandle(entry.getValue())))
                .collect(Collectors.joining(","));
        return String.format("{%s}", entryStr);
    }
}

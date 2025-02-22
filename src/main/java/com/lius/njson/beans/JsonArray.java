package com.lius.njson.beans;

import com.lius.njson.parsers.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonArray extends ArrayList<Object> {
    public <T> List<T> toJavaObject(Class<T> clazz) {
        return this.stream().map(item -> JSON.convertObject(item, clazz)).collect(Collectors.toList());
    }


    @Override
    public String toString() {
        if(this.size()>0){
            return String.format("[%s]",this.stream().map(item -> item instanceof String?String.format("\"%s\"",item): item.toString()).collect(Collectors.joining(",")));
        }
        return "[]";
    }
}

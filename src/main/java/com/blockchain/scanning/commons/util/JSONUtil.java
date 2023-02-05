package com.blockchain.scanning.commons.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * JSON utility class
 */
public class JSONUtil {

    private static Logger logger = LoggerFactory.getLogger(JSONUtil.class);

    /**
     * Convert any object to another java object
     * @param obj
     * @param cls
     * @param <T>
     * @return
     */
    public static  <T> T toJavaObject(Object obj, Class<T> cls) {
        if(obj == null){
            return null;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            if(obj instanceof String){
                return objectMapper.readValue(obj.toString(), cls);
            } else {
                return objectMapper.readValue(objectMapper.writeValueAsString(obj), cls);
            }
        } catch (Exception e){
            logger.error("An exception occurs when converting an object to another Java object through jackson", e);
            return null;
        }
    }

    /**
     * Convert any object to a Map
     * @param obj
     * @return
     */
    public static Map<String, Object> toMap(Object obj) {
        if(obj instanceof Map){
            return (Map<String, Object>) obj;
        }
        Map<String, Object> map = toJavaObject(obj, HashMap.class);
        if(map == null){
            return new HashMap<>();
        }
        return map;
    }

    /**
     * Convert any object to JSON string
     * @param obj
     * @return
     */
    public static String toJSONString(Object obj) {
        if(obj == null){
            return "";
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            if(obj instanceof  String){
                return obj.toString();
            } else {
                return objectMapper.writeValueAsString(obj);
            }
        } catch (Exception e){
            logger.error("Convert object to JSON string exception", e);
            return "";
        }
    }
}

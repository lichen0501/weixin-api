package com.onedt.wx.utils;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

/**
 * jackson第三方javabean与json之间的转换，共三个jar包：jackson-annotation,jackson-core,jackson-databind
 * 
 * @author chao
 * 
 */
public class JacksonUtils {
    /**
     * 将javabean直接转为json字符串
     * 
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static String getJsonCamelLower(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);// 空值的属性不参与转换
        return mapper.writeValueAsString(object);
    }

    /**
     * 将字符串json直接转为javabean,转换成的目标对象含有泛型
     * 
     * @param type
     * @param json
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> T getObjectCamelLower(TypeReference<T> type, String json) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        // 解析器支持解析单引号
        mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);// 忽略实体类中没有的字段
        return mapper.readValue(json, type);
    }

    /**
     * 将字符串json直接转为javabean
     * 
     * @param clazz
     * @param json
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> T getObjectCamelLower(Class<T> clazz, String json) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        // 解析器支持解析单引号
        mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);// 忽略实体类中没有的字段
        return mapper.readValue(json, clazz);
    }

    /**
     * 将字符串json直接转为javabean,返回一个对象集合
     * 
     * @param clazz
     * @param json
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> List<T> getObjectsCamelLower(Class<T> clazz, String json) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        // 解析器支持解析单引号
        mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);// 忽略实体类中没有的字段
        return mapper.readValue(json, mapper.getTypeFactory().constructParametricType(List.class, clazz));
    }

    /**
     * 将驼峰格式javabean转为带下划线的json,自定义转换的规则
     * 
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static String getJsonUnderLower(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);// 空值的属性不参与转换
        mapper.setPropertyNamingStrategy(new PropertyNamingStrategy.PropertyNamingStrategyBase() {
            private static final long serialVersionUID = 1L;

            public String translate(String name) {
                int length = name.length();
                if (length > 0) {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < length; i++) {
                        char c = name.charAt(i);
                        if (Character.isUpperCase(c)) {
                            sb.append("_" + Character.toLowerCase(c));
                        } else {
                            sb.append(Character.toLowerCase(c));
                        }
                    }
                    return sb.toString();
                }
                return name;
            }
        });
        return mapper.writeValueAsString(object);
    }

    /**
     * 将带下划线字符串json转换为驼峰javabean，自定义转换规则
     * 
     * @param clazz
     * @param json
     * @return
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     */
    public static <T> T getObjectUnderLower(Class<T> clazz, String json) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);// 忽略实体类中没有的字段
        mapper.setPropertyNamingStrategy(new PropertyNamingStrategy.PropertyNamingStrategyBase() {
            private static final long serialVersionUID = 1L;

            public String translate(String name) {
                int length = name.length();
                if (length > 0) {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < length; i++) {
                        char c = name.charAt(i);
                        if (Character.isUpperCase(c)) {
                            sb.append("_" + Character.toLowerCase(c));
                        } else {
                            sb.append(Character.toLowerCase(c));
                        }
                    }
                    return sb.toString();
                }
                return name;
            }
        });
        return mapper.readValue(json, clazz);
    }
}

package com.onedt.wx.utils;

import com.thoughtworks.xstream.io.naming.NameCoder;

/**
 * 驼峰命名与下划线的xstream转换器
 * 
 * @author chao
 * 
 */
public class XstreamNameCoder implements NameCoder {
    private String spliter;

    public XstreamNameCoder() {
        this.spliter = "_";
    }

    public XstreamNameCoder(String spliter) {
        this.spliter = spliter;
    }

    @Override
    public String decodeAttribute(String arg0) {
        return arg0;
    }

    @Override
    public String encodeAttribute(String arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    /**
     * 将带下划线的xml节点转为javabean的驼峰格式属性名
     */
    public String decodeNode(String name) {
        String[] arr = name.split(spliter);
        if (arr != null && arr.length > 1) {
            StringBuffer sb = new StringBuffer(arr[0]);
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] != null && arr[i].length() > 1) {
                    String str = arr[i].substring(0, 1).toUpperCase() + arr[i].substring(1);
                    sb.append(str);
                }
            }
            return sb.toString();
        }
        return name;
    }

    /**
     * 将javabean的驼峰格式的属性名转为xml的下划线格式节点名
     */
    public String encodeNode(String name) {
        int length = name.length();
        StringBuffer sb = new StringBuffer();
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                char c = name.charAt(i);
                if (Character.isUpperCase(c)) {
                    sb.append(spliter + Character.toLowerCase(c));
                } else {
                    sb.append(Character.toLowerCase(c));
                }
            }
        }
        return sb.toString();
    }

}

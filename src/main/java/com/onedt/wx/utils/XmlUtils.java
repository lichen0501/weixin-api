package com.onedt.wx.utils;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.onedt.wx.constants.WechatConstants;
import com.onedt.wx.entity.MessageText;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * 利用第三方工具xstream进行xml和java实体的转换
 * 
 * @author chao
 * 
 */
public class XmlUtils {
    protected static final String PREFIX_CDATA = "<![CDATA[";
    protected static final String SUFFIX_CDATA = "]]>";

    /**
     * 将javabean直接转为xml
     * 
     * @param object
     * @return
     */
    public static String toXmlDirect(Object object) {
        XStream xStream = new XStream();
        xStream.setMode(XStream.NO_REFERENCES);
        String xml = xStream.toXML(object);
        return xml;
    }

    /**
     * 将javabean直接转为xml,只是在节点值上多加了cdata
     * 
     * @param object
     * @return
     */
    public static String toXmlCdata(Object object) {
        XppDriver driver = new XppDriver() {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if (text.startsWith(WechatConstants.PARAM_NOTE) || text.endsWith(WechatConstants.PARAM_NOTE)) {
                            text = text.replace(WechatConstants.PARAM_NOTE, "");
                            writer.write(text);
                        } else {
                            writer.write(PREFIX_CDATA + text + SUFFIX_CDATA);
                        }
                    }
                };
            }
        };
        XStream xStream = new XStream(driver);
        xStream.setMode(XStream.NO_REFERENCES);
        xStream.alias("xml", object.getClass());
        String xml = xStream.toXML(object);
        return xml;
    }

    /**
     * 将驼峰式javabean转为带下划线的xml
     * 
     * @param object
     * @return
     */
    public static String toXmlCamel(Object object) {
        // XppDriver是转换成xml时用到的一个转换器
        XppDriver driver = new XppDriver() {
            public HierarchicalStreamWriter createWriter(Writer out) {
                // namecode设定了xml中的<name>value</name>的节点名字name的写法(是否包括下划线),同样也设定javaBean的属性名写法（是否遵守驼峰命名规范）
                NameCoder nameCoder = new XstreamNameCoder();
                return new PrettyPrintWriter(out, nameCoder) {
                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if (text.startsWith(PREFIX_CDATA) && text.endsWith(SUFFIX_CDATA)) {
                            writer.write(text);
                        } else {
                            writer.write(PREFIX_CDATA + text + SUFFIX_CDATA);
                        }
                    }
                };
            }
        };
        XStream xStream = new XStream(driver);
        xStream.alias("xml", object.getClass());
        return xStream.toXML(object);
    }

    /**
     * 将xml直接转换为javabean
     * 
     * @param clazz
     * @param xml
     * @return
     */
    public static <T> T toBeanDirect(Class<T> clazz, String xml) {
        XStream xStream = new XStream();
        xStream.alias("xml", clazz);
        return (T) xStream.fromXML(xml);
    }

    /**
     * 将xml直接转换为key-value集合map
     * 
     * @param InputStream
     * @return
     * @throws DocumentException
     */
    public static Map<String, String> toMapDirect(InputStream in) throws DocumentException {
        SAXReader reader = new SAXReader();
        return parseElement(reader.read(in));
    }

    /**
     * 解析Document的节点和节点值成key-value集合map
     * 
     * @param document
     * @return
     */
    public static Map<String, String> parseElement(Document document) {
        Map<String, String> result = new HashMap<String, String>();
        Element element = document.getRootElement();
        @SuppressWarnings("unchecked")
        List<Element> eleList = element.elements();
        if (eleList != null) {
            for (Element ele : eleList) {
                String text = ele.getText();
                if (text.startsWith(PREFIX_CDATA) && text.endsWith(SUFFIX_CDATA)) {
                    text = text.replace(PREFIX_CDATA, "");
                    text = text.replace(SUFFIX_CDATA, "");
                }
                result.put(ele.getName(), text);
            }
        }
        return result;
    }

    /**
     * 将xml直接转换为key-value集合map
     * 
     * @param xml
     * @return
     * @throws DocumentException
     * @throws UnsupportedEncodingException
     */
    public static Map<String, String> toMapDirect(String xml) throws DocumentException, UnsupportedEncodingException {
        Document document = DocumentHelper.parseText(xml);
        return parseElement(document);
    }

    /**
     * 将带下划线的xml转为驼峰式javabean
     * 
     * @param clazz
     * @param xml
     * @return
     */
    public static <T> T toBeanCamel(Class<T> clazz, String xml) {
        // DomDriver是转换成javaban时所用用到的一个转换器
        DomDriver driver = new DomDriver("UTF-8", new XstreamNameCoder());
        XStream xStream = new XStream(driver);
        return (T) xStream.fromXML(xml);
    }

    /**
     * @param args
     * @throws DocumentException
     * @throws UnsupportedEncodingException
     */
    public static void main(String[] args) throws DocumentException, UnsupportedEncodingException {
        String xml = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>1348831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[this is a test]]></Content><MsgId>1234567890123456</MsgId></xml>";
        Map<String, String> map = toMapDirect(xml);
        System.out.println(map.keySet());
        System.out.println(map.values());
        System.out.println("---------------------------------------");
        MessageText text = new MessageText();
        text.setContent("内容");
        text.setCreateTime(123465789 + "#");
        text.setFromUserName("来自");
        text.setToUserName("天边");
        text.setMsgType("text");
        System.out.println(toXmlCdata(text));
    }

}

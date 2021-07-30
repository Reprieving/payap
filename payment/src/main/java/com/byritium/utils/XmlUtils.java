package com.byritium.utils;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlUtils {
    private static final String PREFIX_XML = "<xml>";
    private static final String SUFFIX_XML = "</xml>";

    private static final String PREFIX_CDATA = "<![CDATA[";
    private static final String SUFFIX_CDATA = "]]>";

    public static String mapToXml(Map<String, String> parm, boolean isAddCDATA) {
        StringBuilder stringBuffer = new StringBuilder(PREFIX_XML);
        if (null != parm) {
            for (Map.Entry<String, String> entry : parm.entrySet()) {
                stringBuffer.append("<").append(entry.getKey()).append(">");
                if (isAddCDATA) {
                    stringBuffer.append(PREFIX_CDATA);
                    if (null != entry.getValue()) {
                        stringBuffer.append(entry.getValue());
                    }
                    stringBuffer.append(SUFFIX_CDATA);
                } else {
                    if (null != entry.getValue()) {
                        stringBuffer.append(entry.getValue());
                    }
                }
                stringBuffer.append("</").append(entry.getKey()).append(">");
            }
        }
        return stringBuffer.append(SUFFIX_XML).toString();
    }

    /**
     * @param xml
     * @return Map
     * @description 将xml字符串转换成map
     */
    public static Map<String, String> xmlToMap(String xml) {
        Map<String, String> map = new HashMap<>();
        Document doc;
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            List<Element> list = rootElt.elements();// 获取根节点下所有节点
            for (Element element : list) { // 遍历节点
                map.put(element.getName(), element.getText()); // 节点的name为map的key，text为map的value
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}

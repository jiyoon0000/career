package com.example.career.global.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlUtil {

    private static final XmlMapper xmlMapper = new XmlMapper();

    static {
        xmlMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T fromXml(String xml, Class<T> valueType) {
        try {
            xml = xml.replaceAll("<script.*?>.*?</script>", "").replaceAll("<script/>", "");

            return xmlMapper.readValue(xml, valueType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("XML 파싱 실패", e);
        }
    }
}

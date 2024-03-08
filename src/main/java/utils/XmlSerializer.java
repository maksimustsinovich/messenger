package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlSerializer {
    private static final ObjectMapper objectMapper = new XmlMapper();

    public static Request<?> convertXmlToRequest(String xml) throws JsonProcessingException {
        return objectMapper.readValue(xml, Request.class);
    }

    public static String convertRequestToXml(Request<?> request) throws JsonProcessingException {
        return objectMapper.writeValueAsString(request);
    }

    public static Response<?> convertXmlToResponse(String xml) throws JsonProcessingException {
        return objectMapper.readValue(xml, Response.class);
    }

    public static String convertResponseToXml(Response<?> response) throws JsonProcessingException {
        return objectMapper.writeValueAsString(response);
    }
}

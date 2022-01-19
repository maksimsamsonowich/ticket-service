package com.senla.ticketservice.mapper.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.ticketservice.exception.entity.NoSuchEntityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Objects;

@Component
public class JsonMapper {

    @Value("${no.constructor.error.message}")
    private String ERROR_MESSAGE;

    private final ObjectMapper objectMapper;

    public JsonMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SuppressWarnings("unchecked")
    public <T> T toEntity(String jsonString, Class<?> someClass) {
        StringReader reader = new StringReader(jsonString);
        try {
            return Objects.isNull(someClass) ? null : (T) objectMapper.readValue(reader, someClass);
        } catch (IOException ex) {
            throw new NoSuchEntityException(ERROR_MESSAGE);
        }
    }
    
    public String toJson(Object someClass) {
        StringWriter writer = new StringWriter();
        try {
            objectMapper.writeValue(writer, someClass);
            return writer.toString();
        } catch (IOException exception) {
            throw new NoSuchEntityException("Input/Output error;");
        }
    }
    
}

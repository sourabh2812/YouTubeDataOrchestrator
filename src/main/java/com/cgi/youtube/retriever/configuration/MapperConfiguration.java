package com.cgi.youtube.retriever.configuration;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Component;

@Component
public class MapperConfiguration {

    @Bean
    public XmlMapper xmlMapper(MappingJackson2XmlHttpMessageConverter converter) {
        return (XmlMapper) converter.getObjectMapper();
    }
}

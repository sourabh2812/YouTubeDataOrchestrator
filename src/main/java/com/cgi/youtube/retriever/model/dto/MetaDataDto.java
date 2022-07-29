package com.cgi.youtube.retriever.model.dto;

import lombok.Data;

import java.util.Map;

@Data
public class MetaDataDto {

    private DataSnippetDto snippet;

    private Map<String, String> id;
}

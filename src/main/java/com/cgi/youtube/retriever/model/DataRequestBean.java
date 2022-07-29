package com.cgi.youtube.retriever.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DataRequestBean {

    @NotNull
    private String searchString;

    @NotNull
    private String type;

    @NotNull
    private String maxResults;
}

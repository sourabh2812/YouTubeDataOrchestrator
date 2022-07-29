package com.cgi.youtube.retriever.model;

import com.cgi.youtube.retriever.model.dto.MetaDataDto;
import lombok.Data;

import java.util.List;

@Data
public class DataResponseBean {

    private List<MetaDataDto> items;
}

package com.cgi.youtube.retriever.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class QueueMessageDto implements Serializable {

    private String url;

    private String title;
}

package com.cgi.youtube.retriever.controller;

import com.cgi.youtube.retriever.model.DataRequestBean;
import com.cgi.youtube.retriever.service.YouTubeDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class YouTubeRetrieverController {

    private final YouTubeDataService youTubeRetrieverService;

    public YouTubeRetrieverController(YouTubeDataService youTubeRetrieverService) {
        this.youTubeRetrieverService = youTubeRetrieverService;
    }

    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> enqueueVideoMetadata(@RequestBody @Valid DataRequestBean requestBean) {
        youTubeRetrieverService.fetchAndProduceYTMetaData(requestBean);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}

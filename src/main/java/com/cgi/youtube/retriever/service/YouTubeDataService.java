package com.cgi.youtube.retriever.service;

import com.cgi.youtube.retriever.model.DataRequestBean;

public interface YouTubeDataService {

    public void fetchAndProduceYTMetaData(DataRequestBean requestBean);
}

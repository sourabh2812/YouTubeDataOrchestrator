package com.cgi.youtube.retriever.service.impl;

import com.cgi.youtube.retriever.configuration.ApiConfiguration;
import com.cgi.youtube.retriever.model.DataRequestBean;
import com.cgi.youtube.retriever.model.DataResponseBean;
import com.cgi.youtube.retriever.model.dto.QueueMessageDto;
import com.cgi.youtube.retriever.producer.JmsDataProducer;
import com.cgi.youtube.retriever.service.YouTubeDataService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cgi.youtube.retriever.constants.AppConstants.*;

@Service
public class YouTubeDataServiceImpl implements YouTubeDataService {

    private final XmlMapper xmlMapper;

    private RestTemplate restTemplate;

    private final JmsDataProducer jmsDataProducer;

    private final ApiConfiguration apiConfiguration;

    public YouTubeDataServiceImpl(XmlMapper xmlMapper, JmsDataProducer jmsDataProducer, ApiConfiguration apiConfiguration) {
        this.xmlMapper = xmlMapper;
        this.jmsDataProducer = jmsDataProducer;
        this.apiConfiguration = apiConfiguration;
    }

    @Override
    @Async()
    public void fetchAndProduceYTMetaData(DataRequestBean requestBean) {
        restTemplate = new RestTemplate();
        Map<String, String> params = createAPIParameterMap(requestBean);
        // call API and fetch results
        DataResponseBean dataList = restTemplate.getForObject(apiConfiguration.getDataApiUrl(), DataResponseBean.class, params);
        // Send data to queue
        sendDataToQueue(dataList);
    }

    @Async()
    private void sendDataToQueue(DataResponseBean dataList) {
        dataList.getItems().stream().map(e -> {
            return QueueMessageDto.builder().title(e.getSnippet().getTitle())
                    .url(VIDEO_URL_PREFIX + e.getId().get(VIDEO_ID)).build();
        }).collect(Collectors.toList()).forEach(data -> {
            try {
                jmsDataProducer.sendMessage(DATA_QUEUE_1,
                        xmlMapper.writeValueAsString(data));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Map<String, String> createAPIParameterMap(DataRequestBean requestBean) {
        Map<String, String> params = new HashMap<>();
        // add parameters for request
        params.put(SECRET_KEY, apiConfiguration.getSecretKey());
        params.put(DATA_PART, apiConfiguration.getDataPart());
        params.put(TYPE, requestBean.getType());
        params.put(MAX_RESULTS, requestBean.getMaxResults());
        params.put(SEARCH_STRING, requestBean.getSearchString());
        return params;
    }
}

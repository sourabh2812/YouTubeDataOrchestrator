package com.cgi.youtube.retriever.consumer;


import com.cgi.youtube.retriever.model.dto.QueueMessageDto;
import com.cgi.youtube.retriever.producer.JmsDataProducer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static com.cgi.youtube.retriever.constants.AppConstants.*;

@Component
public class JmsDataConsumer {

    private static Logger log = LoggerFactory.getLogger(JmsDataConsumer.class);

    private final JmsDataProducer jmsDataProducer;

    private final XmlMapper xmlMapper;

    public JmsDataConsumer(JmsDataProducer jmsDataProducer, XmlMapper xmlMapper) {
        this.jmsDataProducer = jmsDataProducer;
        this.xmlMapper = xmlMapper;
    }


    @JmsListener(destination = DATA_QUEUE_1)
    public void receiveProcessAndRepublishMessage(@Payload String data) throws JsonProcessingException {
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("######   Retrieved Message from QUEUE-1   #####");
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info(data);
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");

        // Process message and send to Queue 2
        processAndDelegate(data);

        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
        log.info("###  Processed and Sent Message to QUEUE-2  ###");
        log.info("- - - - - - - - - - - - - - - - - - - - - - - -");
    }

    private void processAndDelegate(String data) throws JsonProcessingException {
        QueueMessageDto retrievedMsg = xmlMapper.readValue(data, QueueMessageDto.class);
        jmsDataProducer.sendMessage(DATA_QUEUE_2,
                xmlMapper.writeValueAsString(QueueMessageDto.builder()
                        .title(retrievedMsg.getTitle().replaceAll(TELECOM_REGEX, TELCO))
                        .url(retrievedMsg.getUrl()).build()));
    }
}

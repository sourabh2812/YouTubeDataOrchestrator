package com.cgi.youtube.retriever.producer;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class JmsDataProducer {
    private final JmsTemplate jmsTemplate;

    public JmsDataProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(final String destinationQueue, final String message) {
        jmsTemplate.convertAndSend( destinationQueue, message);
    }

}

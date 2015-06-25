package org.smallpawn.example.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.File;

@Component
public class JmsUtil {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendJMSMessage(String queue, final String message) {
        // Clean out any ActiveMQ data from a previous run
        FileSystemUtils.deleteRecursively(new File("activemq-data"));
        // Send a message
        MessageCreator messageCreator = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        };
        jmsTemplate.send(queue, messageCreator);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

package org.smallpawn.example.jms;

import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.logging.Logger;

@Component
public class RequeueReceiver implements SessionAwareMessageListener<TextMessage> {

    private Logger logger = Logger.getLogger(RequeueReceiver.class.getName());

    @Override
    public void onMessage(TextMessage textMessage, Session session) throws JMSException {
        String msg = textMessage.getText();
        logger.info("GOT MESSAGE From ReQueue " + msg);
    }
}

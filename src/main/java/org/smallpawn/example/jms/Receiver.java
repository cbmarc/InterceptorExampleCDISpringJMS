package org.smallpawn.example.jms;

import org.smallpawn.example.MessageBinding;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class Receiver implements SessionAwareMessageListener<TextMessage> {

    @Override
    @MessageBinding
    public void onMessage(TextMessage textMessage, Session session) throws JMSException {
        String msg = textMessage.getText();
        System.out.println("GOT MESSAGE From DefaultQueue " + msg);
    }
}

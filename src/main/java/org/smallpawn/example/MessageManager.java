package org.smallpawn.example;

import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class MessageManager {

    private Logger logger = Logger.getLogger(MessageManager.class.getName());

    /**
     * Converts the given message to lower case
     * @param message The message to prepare
     * @return String converted to lowercase
     */
    // Our binding, configured to apply the MessageInterceptor
    @MessageBinding
    public String prepareMessage(String message) {
        logger.info("Called prepareMessage");
        // Just returns the message converted to lowercase
        return message.toLowerCase();
    }

}

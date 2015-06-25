package org.smallpawn.example;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.smallpawn.example.util.JmsUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.jms.TextMessage;
import java.util.logging.Logger;

@MessageBinding
@Interceptor
public class MessageInterceptor {

    private Logger logger = Logger.getLogger(MessageInterceptor.class.getName());

    @Autowired
    private JmsUtil jmsUtil;

    @AroundInvoke
    public Object stealMessage(InvocationContext context) throws Exception {
        logger.info("INTERCEPTOR stealMessage invoked intercepting method " + context.getMethod().getName());
        Object[] params = context.getParameters();
        if (params.length > 0 && params[0] instanceof TextMessage) {

            TextMessage message = (TextMessage) params[0];
            logger.info("Message stolen: " + message.getText());
            Class<?> test = Class.forName("org.smallpawn.example.tests.SimpleTest");
            JUnitCore junit = new JUnitCore();
            Result result = junit.run(test);
            logger.info("Test result failures = " + result.getFailureCount());
            jmsUtil.sendJMSMessage("ReQueue", message.getText());
        } else {
            logger.info("Params length is 0");
        }
        return null;
    }

}

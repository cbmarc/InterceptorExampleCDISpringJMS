package org.smallpawn.example.tests;

import org.junit.Assert;
import org.junit.Test;

import java.util.logging.Logger;

public class SimpleTest {

    private Logger logger = Logger.getLogger(SimpleTest.class.getName());

    @Test
    public void simpleTest() {
        logger.info("Simple Test launched!");
        Assert.assertTrue(true);
    }
}

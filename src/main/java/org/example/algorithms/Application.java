package org.example.algorithms;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple java application
 * @author Viktar Lebedzeu
 */
public class Application {
    /** Logger */
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    /**
     * Application's inty point
     * @param args Application arguments
     */
    public static void main(String... args) {
        logger.debug(StringUtils.repeat("=", 50));
        logger.info("This application is used for test purpose only.");
        logger.info("Please use unit tests instead. Quiting...");
        logger.debug(StringUtils.repeat("=", 50));
    }
}

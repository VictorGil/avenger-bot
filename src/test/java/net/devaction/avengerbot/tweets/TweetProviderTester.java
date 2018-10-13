package net.devaction.avengerbot.tweets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.devaction.avengerbot.tweetstobesentprovider.TweetProvider;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
public class TweetProviderTester {
    private static final Logger log = LogManager.getLogger(TweetProviderTester.class);

    private TweetProvider tweetProvider;
    {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/beans.xml");
        tweetProvider = (TweetProvider) appContext.getBean("tweetProvider");
        ((ConfigurableApplicationContext) appContext).close();
    }
    
    public static void main(String[] args){
        new TweetProviderTester().run1();
    }
    
    private void run1(){
        log.info("First tweet: " + tweetProvider.provide());
        log.info("Second tweet: " + tweetProvider.provide());
    }
}


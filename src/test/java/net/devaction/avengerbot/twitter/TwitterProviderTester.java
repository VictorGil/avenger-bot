package net.devaction.avengerbot.twitter;

import org.apache.logging.log4j.Logger;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.devaction.avengerbot.twitter.TwitterProvider;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
public class TwitterProviderTester{
    private static final Logger log = LogManager.getLogger(TwitterProviderTester.class);

    private TwitterProvider twitterProvider;
    {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/beans.xml");
        twitterProvider = (TwitterProvider) appContext.getBean("twitterProvider");
        ((ConfigurableApplicationContext) appContext).close();
    }
    
    public static void main(String[] args){
        //new TwitterProviderTester().run1();
        new TwitterProviderTester().run2();
    }
    
    private void run1(){
        Paging paging = new Paging(1, 200);
        List<Status> statuses = null;
        Twitter twitter = twitterProvider.provide();
        
        try{
            statuses = twitter.getUserTimeline("devactionnet", paging);
        } catch(TwitterException ex){
            String errMessage = "Unable to retrieve timeline: " + ex; 
            log.error(errMessage, ex);
            throw new RuntimeException(errMessage, ex);
        }
        log.info("Number of tweets in the response: " + statuses.size());
    }
    
    private void run2(){
        Twitter twitter = twitterProvider.provide();
        Status status = null;
        //https://twitter.com/orange_es/status/1030023378688782337
        //https://twitter.com/solerbriz/status/1041037452708913154
        //long statusId = 1030023378688782337L;
        long statusId = 1041037452708913154L;
        
        try{
            status = twitter.showStatus(statusId);
        } catch(TwitterException ex){
            String errMessage = "Unable to retrieve status: " + ex; 
            log.error(errMessage, ex);
            throw new RuntimeException(errMessage, ex);
        }
        log.info("status.getText():" + status.getText());
        log.info("status.getInReplyToStatusId(): " + status.getInReplyToStatusId()); //if it is -1 it means that it is not a reply
        log.info("status.getInReplyToScreenName(): " + status.getInReplyToScreenName()); //if it is null it means that it is not a reply
    }
}


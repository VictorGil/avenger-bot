package net.devaction.avengerbot.tweetscapturedconsumer;

import twitter4j.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.devaction.avengerbot.twitter.TweetProcessor;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
public class TweetsConsumerImpl implements Runnable, TweetsConsumer{
    private static final Logger log = LogManager.getLogger(TweetsConsumer.class);

    //these two are set by Spring
    private TweetsQueueForConsumer queue;
    private TweetProcessor processor;
    
    private volatile boolean isStopped;
    
    @Override
    public void start(){
        Thread tweetsConsumerThread = new Thread(this);
        tweetsConsumerThread.setName("tweetsConsumerThread");
        tweetsConsumerThread.start(); 
    }

    @Override
    public void stop(){
        if (isStopped){
            log.warn("The tweets consumer is already stopped");
            return;
        }
        queue.insertFakeTweet();
    }

    @Override
    public boolean isStopped(){
        return isStopped;
    }

    @Override
    public void run(){
        while(true){
            Status status = queue.take();
            
            if (status.getId() == -1){
                log.info("Received special tweet, stopping tweets consumer.");
                isStopped = true;
                return;
            }
            
            processor.process(status);
        }        
    }

    public void setQueue(TweetsQueueForConsumer queue){
        this.queue = queue;
    }

    public void setProcessor(TweetProcessor processor){
        this.processor = processor;
    }
}


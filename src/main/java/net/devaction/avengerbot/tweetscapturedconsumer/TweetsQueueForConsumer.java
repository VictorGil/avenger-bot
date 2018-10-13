package net.devaction.avengerbot.tweetscapturedconsumer;

import twitter4j.Status;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
public interface TweetsQueueForConsumer{
    //this is a blocking method
    public Status take();
    
    public void insertFakeTweet();
}

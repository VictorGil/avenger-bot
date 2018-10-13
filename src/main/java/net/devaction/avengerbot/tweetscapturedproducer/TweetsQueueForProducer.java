package net.devaction.avengerbot.tweetscapturedproducer;

import twitter4j.Status;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
public interface TweetsQueueForProducer{
    public boolean offer(Status status);
}

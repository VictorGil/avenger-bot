package net.devaction.avengerbot.twitter;

import twitter4j.Status;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
public interface TweetProcessor{
    public void process(Status status);
}


package net.devaction.avengerbot.tweetscapturedproducer;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
public interface TweetsProducer{
    public void start();
    public void stop();
    public boolean isStopped();
}

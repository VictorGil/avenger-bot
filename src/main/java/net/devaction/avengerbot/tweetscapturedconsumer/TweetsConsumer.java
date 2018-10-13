package net.devaction.avengerbot.tweetscapturedconsumer;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
public interface TweetsConsumer{
    public void start();
    public void stop();
    public boolean isStopped();
}

package net.devaction.avengerbot.tweetscapturedproducer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.devaction.avengerbot.tweetscapturedconsumer.FakeStatus;
import net.devaction.avengerbot.tweetscapturedconsumer.TweetsQueueForConsumer;
import twitter4j.Status;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
public class TweetsQueueImpl implements TweetsQueueForProducer, TweetsQueueForConsumer{
    private static final Logger log = LogManager.getLogger(TweetsQueueImpl.class);
    private static final int QUEUE_CAPACITY = 2400; 
    private BlockingQueue<Status> blockingQueue = new LinkedBlockingQueue<Status>(QUEUE_CAPACITY);
    
    @Override
    public boolean offer(Status status){
            log.debug("status captured: " + status.getText());
            return blockingQueue.offer(status);
    }

    @Override
    public void insertFakeTweet(){
                log.debug("Going to add a fake tweet to tell the tweets consumer to stop");
                blockingQueue.clear();
                blockingQueue.add(new FakeStatus());    
    }

    @Override
    public Status take(){
            try{
                return blockingQueue.take();
            } catch (InterruptedException ex){
                //this should never happen
                log.error(ex, ex);
                throw new RuntimeException(ex);        
            }
    }    
}


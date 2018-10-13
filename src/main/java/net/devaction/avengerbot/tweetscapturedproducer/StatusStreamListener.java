package net.devaction.avengerbot.tweetscapturedproducer;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
public class StatusStreamListener implements StatusListener{
    private static final Logger log = LogManager.getLogger(StatusStreamListener.class);
    
    //this is set by Spring
    private TweetsQueueForProducer queue;
    
    private boolean isItStopping;
    
    //we only need this to send the stop message when the queue is full
    private TweetsProducer tweetsProducer;

    @Override
    public void onException(Exception ex){
        log.error(ex, ex);        
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice){
        log.warn("Deletion notice for status id: " + statusDeletionNotice.getStatusId() + 
                ", user id: " + statusDeletionNotice.getUserId());        
    }

    @Override
    public void onScrubGeo(long arg0, long arg1){        
        
    }

    @Override
    public void onStallWarning(StallWarning stallWarning){
        log.warn("Stall warning object received: " + stallWarning);        
    }

    @Override
    public void onStatus(Status status){
        if (isItStopping)
            return;
        
        if (!queue.offer(status)) {
            isItStopping = true;
            log.error("The queue is full. Going to stop listening for new tweets");
            tweetsProducer.stop();            
        }
    }

    @Override
    public void onTrackLimitationNotice(int arg0){
        log.warn("Track limitation notice received: " + arg0);
        
    }
    
    //this is not called by spring 
    public void setTweetsProducer(TweetsProducer tweetsProducer){
        this.tweetsProducer = tweetsProducer;
    }

    public void setQueue(TweetsQueueForProducer queue){
        this.queue = queue;
    }
}


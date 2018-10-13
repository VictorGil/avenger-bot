package net.devaction.avengerbot.twitter;

import java.util.Date;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.devaction.avengerbot.tweetstobesentprovider.TweetProvider;
import twitter4j.GeoLocation;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
public class TweetProcessorImpl implements TweetProcessor{
    private static final Logger log = LogManager.getLogger(TweetProcessorImpl.class);
    
    //this is one of the Twitter API error codes
    //https://stackoverflow.com/questions/40826934/why-is-the-twitter-api-throwing-this-error
    private static final int TOKEN_SUSPENDED = 261;
    
    private long lastTweetSentTS;
    
    private GeolocationProvider geolocationProvider;
    private OurUserIdProvider ourUserIdProvider; 
    
    private TwitterProvider twitterProvider;
    private TweetProvider tweetProvider;
    
    private final Random random = new Random();    
    
    @Override
    public void process(Status status){
        //if (status.isRetweet())
        //    return;
        
        //if (status.getInReplyToStatusId() > 0)
        //    return;
        
        User user = status.getUser();
        if (user == null)
            return;
        
        if (user.getId() == ourUserIdProvider.getOurUserId())
            return;
        
        String screenName = user.getScreenName();
        long statusId = status.getId();
        
        String message = tweetProvider.provide() + " \u0040" + screenName;
        StatusUpdate statusUpdate = new StatusUpdate(message);
        statusUpdate.setInReplyToStatusId(statusId);
        
        GeoLocation location= new GeoLocation(geolocationProvider.getLatitude(), 
                geolocationProvider.getLongitude());
        statusUpdate.setLocation(location);        
        
        //we need to wait at least 36 seconds before publishing a new tweet
        final int THIRTY_SIX_SECONDS = 36000;
        long currentTS = new Date().getTime();
        long waitingTime1 = THIRTY_SIX_SECONDS + provideRandomTime() - (currentTS - lastTweetSentTS);
        
        if (waitingTime1 > 0){
            log.info("Going to wait " + (int) (waitingTime1 / 1000) + " seconds before publishing a new tweet.");
            sleep(waitingTime1); 
        }
        
        Status newStatus = null;
        int apiErrCode = -1;
        do {
            try{
                newStatus = twitterProvider.provide().updateStatus(statusUpdate);
                //we successfully published the new tweet
                apiErrCode = -1;
            } catch (TwitterException ex){
                int httpErrCode = ex.getStatusCode();
                apiErrCode = ex.getErrorCode();            
                log.error("Error when trying to publish a tweet: " + ex + 
                        "\nHTTP error code: " + httpErrCode + 
                        "\nTwitter API error code: " + apiErrCode, ex);
                
                if (apiErrCode == TOKEN_SUSPENDED){
                    final long FIFTEEN_MINUTES = 15 * 60 * 1000;
                    final long waitingTime2 = FIFTEEN_MINUTES + provideRandomTime();            
                    log.info("Going to wait " + (int) (waitingTime2 / 1000) + " seconds before trying to publish the tweet again.");
                    sleep(waitingTime2);
                }
            }
        } while(apiErrCode == TOKEN_SUSPENDED);
        
        lastTweetSentTS = new Date().getTime();
        
        log.info("Tweet published: " + newStatus.getText());
    }

    long provideRandomTime(){
        return 2 * 60 * 1000 + random.nextInt(60001);
    }
    
    private void sleep(long milliSeconds){
        try{
            Thread.sleep(milliSeconds);
        } catch (InterruptedException ex){
            log.error(ex, ex);
        }   
    }
    
    public TwitterProvider getTwitterProvider(){
        return twitterProvider;
    }

    public void setTwitterProvider(TwitterProvider twitterProvider){
        this.twitterProvider = twitterProvider;
    }

    public TweetProvider getTweetProvider(){
        return tweetProvider;
    }

    public void setTweetProvider(TweetProvider tweetProvider){
        this.tweetProvider = tweetProvider;
    }

    public void setGeolocationProvider(GeolocationProvider geolocationProvider){
        this.geolocationProvider = geolocationProvider;
    }

    public void setOurUserIdProvider(OurUserIdProvider ourUserIdProvider){
        this.ourUserIdProvider = ourUserIdProvider;
    }
}


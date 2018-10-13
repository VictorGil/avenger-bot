package net.devaction.avengerbot.tweetscapturedproducer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.devaction.avengerbot.config.twitterkey.DecryptedKeyPairProvider;
import net.devaction.avengerbot.config.twitterkey.KeyPair;
import net.devaction.avengerbot.config.twittertoken.DecryptedTokenPairProvider;
import net.devaction.avengerbot.config.twittertoken.TokenPair;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
public class TweetsProducerImpl implements Runnable, TweetsProducer{
    private static final Logger log = LogManager.getLogger(TweetsProducerImpl.class);
    
    private DecryptedKeyPairProvider decryptedKeyPairProvider;
    private DecryptedTokenPairProvider decryptedTokenPairProvider;
    
    private UserIdToFollowProvider userIdToFollowProvider;
    private KeywordsProvider keywordsProvider;
    
    private StatusStreamListener listener;
    
    private final Object lock = new Object();
    private volatile boolean isStopped;
    
    @Override
    public void start(){
        Thread tweetsProducerThread = new Thread(this);
        tweetsProducerThread.setName("tweetsProducerThread");
        tweetsProducerThread.start();
    }

    @Override
    public void stop(){
        if (isStopped){
            log.warn("The tweets producer is already stopped");
            return;
        }
        
        log.info("Going to stop the tweets producer thread.");
        synchronized (lock){
            lock.notify();
        }
    }
    
    @Override
    public boolean isStopped(){
        return isStopped;
    }
    
    @Override
    public void run(){
        log.info("Tweets producer has been started on a new thread");
        
        KeyPair keyPair = decryptedKeyPairProvider.provide();        
        log.info("Key pair: " + keyPair);   
        
        TokenPair tokenPair = decryptedTokenPairProvider.provide();
        log.info("Token pair: " + tokenPair);
        
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey(keyPair.getConsumerApiKey());
        cb.setOAuthConsumerSecret(keyPair.getConsumerApiSecret());
        cb.setOAuthAccessToken(tokenPair.getAccesToken());
        cb.setOAuthAccessTokenSecret(tokenPair.getAccessTokenSecret());
        
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();        
        
        listener.setTweetsProducer(this);
        twitterStream.addListener(listener);
        
        FilterQuery filterQuery = new FilterQuery();
        filterQuery.track(keywordsProvider.getKeywords());        
        filterQuery.follow(userIdToFollowProvider.getUserIdToFollow());
        
        twitterStream.filter(filterQuery);        
        log.info("Started listening for tweets");
        
        try {
            synchronized (lock){
              lock.wait();
            }
        } catch (InterruptedException ex) {
               log.error(ex, ex);            
        }
        log.info("Going to stop listening for tweets, Cleaning up the Twitter stream");
        twitterStream.cleanUp();
        
        try {
            Thread.sleep(1000);        
        } catch (InterruptedException ex) {
               log.error(ex, ex);            
        }
        
        log.info("Going to shutdown the Twitter stream");
        twitterStream.shutdown();
        
        log.info("Tweets producer thread has been stopped.");
        isStopped = true;
    }
    
    public void setDecryptedKeyPairProvider(DecryptedKeyPairProvider decryptedKeyPairProvider){
        this.decryptedKeyPairProvider = decryptedKeyPairProvider;
    }

    public void setDecryptedTokenPairProvider(DecryptedTokenPairProvider decryptedTokenPairProvider){
        this.decryptedTokenPairProvider = decryptedTokenPairProvider;
    }

    public void setKeywordsProvider(KeywordsProvider keywordsProvider){
        this.keywordsProvider = keywordsProvider;
    }

    public void setListener(StatusStreamListener listener){
        this.listener = listener;
    }

    public void setUserIdToFollowProvider(UserIdToFollowProvider userIdToFollowProvider){
        this.userIdToFollowProvider = userIdToFollowProvider;
    }  
}


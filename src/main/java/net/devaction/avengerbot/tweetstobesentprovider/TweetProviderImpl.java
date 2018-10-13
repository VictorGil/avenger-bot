package net.devaction.avengerbot.tweetstobesentprovider;

import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
public class TweetProviderImpl implements TweetProvider, InitializingBean{
    private static final Logger log = LogManager.getLogger(TweetProviderImpl.class);

    private TweetListProvider tweetListProvider;    
    private List<String> tweets;
    
    private final Random random = new Random();    
    
    @Override
    public void afterPropertiesSet() throws Exception {
          tweets = tweetListProvider.provideListOfTweets();          
    }
    
    @Override
    public String provide(){
        int randomIndex = random.nextInt(tweets.size());
        return tweets.get(randomIndex);
    }

    //to be used by Spring
    public void setTweetListProvider(TweetListProvider tweetListProvider){
        this.tweetListProvider = tweetListProvider;
    }

    //useful when testing
    public void setTweets(List<String> tweets){
        this.tweets = tweets;
    }    
}


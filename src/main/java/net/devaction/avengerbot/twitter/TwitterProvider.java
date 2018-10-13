package net.devaction.avengerbot.twitter;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import net.devaction.avengerbot.config.twitterkey.DecryptedKeyPairProvider;
import net.devaction.avengerbot.config.twitterkey.KeyPair;
import net.devaction.avengerbot.config.twittertoken.DecryptedTokenPairProvider;
import net.devaction.avengerbot.config.twittertoken.TokenPair;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import org.apache.logging.log4j.LogManager;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
public class TwitterProvider implements InitializingBean{
    private static final Logger log = LogManager.getLogger(TwitterProvider.class);
    
    private Twitter twitter;
    
    private DecryptedKeyPairProvider decryptedKeyPairProvider;
    private DecryptedTokenPairProvider decryptedTokenPairProvider;
    
    @Override
    public void afterPropertiesSet() throws Exception{
        Twitter twitter = TwitterFactory.getSingleton();
        
        KeyPair keyPair = decryptedKeyPairProvider.provide();        
        log.info("Key pair: " + keyPair);        
        twitter.setOAuthConsumer(keyPair.getConsumerApiKey(), keyPair.getConsumerApiSecret());
        
        TokenPair tokenPair = decryptedTokenPairProvider.provide();
        log.info("Token pair: " + tokenPair);
        AccessToken accessToken = new AccessToken(tokenPair.getAccesToken(), tokenPair.getAccessTokenSecret());        
        twitter.setOAuthAccessToken(accessToken);  
        
        this.twitter = twitter;        
    }
    
    public Twitter provide(){
        return twitter;
    }

    public void setDecryptedKeyPairProvider(DecryptedKeyPairProvider decryptedKeyPairProvider){
        this.decryptedKeyPairProvider = decryptedKeyPairProvider;
    }

    public void setDecryptedTokenPairProvider(DecryptedTokenPairProvider decryptedTokenPairProvider){
        this.decryptedTokenPairProvider = decryptedTokenPairProvider;
    }  
}


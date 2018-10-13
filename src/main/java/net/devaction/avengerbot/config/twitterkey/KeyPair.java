package net.devaction.avengerbot.config.twitterkey;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
public class KeyPair{
    private static final Logger log = LogManager.getLogger(KeyPair.class);
    
    private String consumerApiKey;
    private String consumerApiSecret;
    
    public KeyPair(String consumerApiKey, String consumerApiSecret){
        this.consumerApiKey = consumerApiKey;
        this.consumerApiSecret = consumerApiSecret;        
    } 
    
    public String getConsumerApiKey() {
        return consumerApiKey;
    }

    public String getConsumerApiSecret() {
        return consumerApiSecret;
    }   
    
    @Override
    public String toString(){
       return "{consumerApiKey: " + consumerApiKey + ",\nconsumerApiSecret: " + consumerApiSecret + "}"; 
    }
}



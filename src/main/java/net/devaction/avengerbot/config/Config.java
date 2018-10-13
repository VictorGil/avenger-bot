package net.devaction.avengerbot.config;

import org.apache.logging.log4j.Logger;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
//Only the ConfigReader class must depend on this class 
public class Config{
    private static final Logger log = LogManager.getLogger(Config.class);
    
    private String twitterConsumerApiKeyEncrypted;
    private String twitterConsumerApiSecretEncrypted;
    private String decryptPasswordEnvVarName;
    private String twitterAccessTokenEncrypted;
    private String twitterAccessTokenSecretEncrypted;    
    private String[] keywords;
    private long userIdToFollow;
    private long ourUserId;
    private double latitude;
    private double longitude;
    private String[] tweets;

    
    @Override
    public String toString(){
        return "Config [twitterConsumerApiKeyEncrypted=" + twitterConsumerApiKeyEncrypted + ", twitterConsumerApiSecretEncrypted="
                + twitterConsumerApiSecretEncrypted + ", decryptPasswordEnvVarName=" + decryptPasswordEnvVarName
                + ", twitterAccessTokenEncrypted=" + twitterAccessTokenEncrypted + ", twitterAccessTokenSecretEncrypted="
                + twitterAccessTokenSecretEncrypted + ", keywords=" + Arrays.toString(keywords) + ", userIdToFollow=" + userIdToFollow
                + ", ourUserId=" + ourUserId + ", latitude=" + latitude + ", longitude=" + longitude + ", tweets=" + Arrays.toString(tweets)
                + "]";
    }

    //getters and setters 
    public String getTwitterConsumerApiKeyEncrypted(){
        return twitterConsumerApiKeyEncrypted;
    }
    
    public void setTwitterConsumerApiKeyEncrypted(String twitterConsumerApiKeyEncrypted){
        this.twitterConsumerApiKeyEncrypted = twitterConsumerApiKeyEncrypted;
    }
    
    public String getTwitterConsumerApiSecretEncrypted(){
        return twitterConsumerApiSecretEncrypted;
    }
    
    public void setTwitterConsumerApiSecretEncrypted(String twitterConsumerApiSecretEncrypted){
        this.twitterConsumerApiSecretEncrypted = twitterConsumerApiSecretEncrypted;
    }
    
    public String getDecryptPasswordEnvVarName(){
        return decryptPasswordEnvVarName;
    }
    
    public void setDecryptPasswordEnvVarName(String decryptPasswordEnvVarName){
        this.decryptPasswordEnvVarName = decryptPasswordEnvVarName;
    }
    
    public String getTwitterAccessTokenEncrypted(){
        return twitterAccessTokenEncrypted;
    }
    
    public void setTwitterAccessTokenEncrypted(String twitterAccessTokenEncrypted){
        this.twitterAccessTokenEncrypted = twitterAccessTokenEncrypted;
    }
    
    public String getTwitterAccessTokenSecretEncrypted(){
        return twitterAccessTokenSecretEncrypted;
    }
    
    public void setTwitterAccessTokenSecretEncrypted(String twitterAccessTokenSecretEncrypted){
        this.twitterAccessTokenSecretEncrypted = twitterAccessTokenSecretEncrypted;
    }

    public String[] getTweets(){
        return tweets;
    }

    public void setTweets(String[] tweets){
        this.tweets = tweets;
    }

    public String[] getKeywords(){
        return keywords;
    }

    public void setKeywords(String[] keywords){
        this.keywords = keywords;
    }

    public long getUserIdToFollow(){
        return userIdToFollow;
    }

    public void setUserIdToFollow(long userIdToFollow){
        this.userIdToFollow = userIdToFollow;
    }

    public long getOurUserId(){
        return ourUserId;
    }

    public void setOurUserId(long ourUserId){
        this.ourUserId = ourUserId;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }    
}


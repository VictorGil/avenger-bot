package net.devaction.avengerbot.tweetscapturedconsumer;

import java.util.Date;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Scopes;
import twitter4j.Status;
import twitter4j.SymbolEntity;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
public class FakeStatus implements Status{
    private static final long serialVersionUID = -8219964072439936708L;

    private static final String ERROR_MESSAGE = "This is a special status, this method should not be called on this object.";

    @Override
    public long getId(){
        //this is how the consumer knows that this is a special Status object and needs to stop.
        return -1;
    }
    
    //The following methods should never be called
    @Override
    public int compareTo(Status o){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public RateLimitStatus getRateLimitStatus(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public int getAccessLevel(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public UserMentionEntity[] getUserMentionEntities(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public URLEntity[] getURLEntities(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public HashtagEntity[] getHashtagEntities(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public MediaEntity[] getMediaEntities(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public SymbolEntity[] getSymbolEntities(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public Date getCreatedAt(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public String getText(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public int getDisplayTextRangeStart(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public int getDisplayTextRangeEnd(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public String getSource(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public boolean isTruncated(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public long getInReplyToStatusId(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public long getInReplyToUserId(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public String getInReplyToScreenName(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public GeoLocation getGeoLocation(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public Place getPlace(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public boolean isFavorited(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public boolean isRetweeted(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public int getFavoriteCount(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public User getUser(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public boolean isRetweet(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public Status getRetweetedStatus(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public long[] getContributors(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public int getRetweetCount(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public boolean isRetweetedByMe(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public long getCurrentUserRetweetId(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public boolean isPossiblySensitive(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public String getLang(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public Scopes getScopes(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public String[] getWithheldInCountries(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public long getQuotedStatusId(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public Status getQuotedStatus(){
        throw new RuntimeException(ERROR_MESSAGE);
    }

    @Override
    public URLEntity getQuotedStatusPermalink(){
        throw new RuntimeException(ERROR_MESSAGE);
    }
}


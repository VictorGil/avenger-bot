package net.devaction.avengerbot.config;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.devaction.avengerbot.config.twitterkey.EncryptedKeyPairProvider;
import net.devaction.avengerbot.config.twittertoken.EncryptedTokenPairProvider;
import net.devaction.avengerbot.tweetscapturedproducer.KeywordsProvider;
import net.devaction.avengerbot.tweetscapturedproducer.UserIdToFollowProvider;
import net.devaction.avengerbot.tweetstobesentprovider.TweetListProvider;
import net.devaction.avengerbot.twitter.GeolocationProvider;
import net.devaction.avengerbot.twitter.OurUserIdProvider;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
public class ConfigReader implements InitializingBean, EncryptedKeyPairProvider, 
        EncryptedTokenPairProvider, TweetListProvider, KeywordsProvider, OurUserIdProvider,
        GeolocationProvider, UserIdToFollowProvider{
    
    private static final Logger log = LogManager.getLogger(ConfigReader.class);
    //this file must be present in the classpath
    private static final String CONFIG_FILE = "/config.json";
    
    private Config config;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        config = read();    
    }

    private Config read(){
        byte[] jsonBytes = null;
        log.debug("Going to read the configuration values from " + CONFIG_FILE);
        
        try{
           jsonBytes = readBytesFromClasspath(CONFIG_FILE); 
        } catch(URISyntaxException | IOException ex){
            String errorMessage = "Error when trying to read " + CONFIG_FILE + " file";
            log.error(errorMessage, ex);
            throw new RuntimeException(errorMessage, ex);
        }
        
        ObjectMapper objectMapper = new ObjectMapper();
        Config config = null;
        try{ 
            config = objectMapper.readValue(jsonBytes, Config.class);
        } catch(IOException ex){
            String errorMessage = "Error when trying to parse " + CONFIG_FILE + " file";
            log.error(errorMessage, ex);
            throw new RuntimeException(errorMessage, ex);
        }
        
        log.info("Application configuration: " + config);
        return config;
    }
    
    private byte[] readBytesFromClasspath(String filename) throws URISyntaxException, IOException{
        URL url = this.getClass().getResource(filename);
        URI uri = url.toURI();
        Path path = Paths.get(uri);
        log.debug("Path to the file: " + path);
        return Files.readAllBytes(path);
    }
    
    //getters for configuration properties
    @Override
    public String getTwitterConsumerApiKeyEncrypted(){
        return config.getTwitterConsumerApiKeyEncrypted();
    }
    
    @Override
    public String getTwitterConsumerApiSecretEncrypted(){
        return config.getTwitterConsumerApiSecretEncrypted();
    }
    
    @Override
    public String getDecryptPasswordEnvVarName(){
        return config.getDecryptPasswordEnvVarName();
    }
    
    @Override
    public String getTwitterAccessTokenEncrypted(){
        return config.getTwitterAccessTokenEncrypted();
    }
    
    @Override
    public String getTwitterAccessTokenSecretEncrypted(){
        return config.getTwitterAccessTokenSecretEncrypted();
    }
    
    @Override
    public List<String> provideListOfTweets(){        
        return Arrays.asList(config.getTweets());
    }

    @Override
    public String[] getKeywords(){
        return config.getKeywords();
    }

    @Override
    public long getUserIdToFollow(){
        return config.getUserIdToFollow();
    }

    @Override
    public double getLatitude(){
        return config.getLatitude();
    }

    @Override
    public double getLongitude(){
        return config.getLongitude();
    }

    @Override
    public long getOurUserId(){
        return config.getOurUserId();
    }
}


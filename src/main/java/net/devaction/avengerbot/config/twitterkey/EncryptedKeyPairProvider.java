package net.devaction.avengerbot.config.twitterkey;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
public interface EncryptedKeyPairProvider{
    public String getTwitterConsumerApiKeyEncrypted();    
    public String getTwitterConsumerApiSecretEncrypted();
    public String getDecryptPasswordEnvVarName();
}


package net.devaction.avengerbot.config.twittertoken;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
public interface EncryptedTokenPairProvider{
    public String getTwitterAccessTokenEncrypted();    
    public String getTwitterAccessTokenSecretEncrypted();
    public String getDecryptPasswordEnvVarName();
}


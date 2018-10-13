package net.devaction.avengerbot.config.twittertoken;

import org.apache.logging.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimplePBEConfig;
import org.springframework.beans.factory.InitializingBean;
import org.apache.logging.log4j.LogManager;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
public class DecryptedTokenPairProviderImpl implements InitializingBean, DecryptedTokenPairProvider{
    private static final Logger log = LogManager.getLogger(DecryptedTokenPairProviderImpl.class);
    
    private String accessTokenEncrypted;
    private String accessTokenSecretEncrypted;
    private String decryptPasswordEnvVarName;
        
    //These are meant to be used only when testing
    private String accessToken;
    private String accessTokenSecret;
    
    private EncryptedTokenPairProvider encryptedAccessTokenPairProvider;
    
    @Override
    public void afterPropertiesSet() throws Exception{
        accessTokenEncrypted = encryptedAccessTokenPairProvider.getTwitterAccessTokenEncrypted();
        if (accessTokenEncrypted == null || accessTokenEncrypted.length() == 0){
            String errMessage = "The encrypted Twitter API access token cannot be null nor empty";
            log.fatal(errMessage);
            throw new RuntimeException(errMessage);
        }
        
        accessTokenSecretEncrypted = encryptedAccessTokenPairProvider.getTwitterAccessTokenSecretEncrypted();
        if (accessTokenSecretEncrypted == null || accessTokenSecretEncrypted.length() == 0){
            String errMessage = "The encrypted Twitter API access token sercret cannot be null nor empty";
            log.fatal(errMessage);
            throw new RuntimeException(errMessage);
        }        
        
        decryptPasswordEnvVarName = encryptedAccessTokenPairProvider.getDecryptPasswordEnvVarName();
        if (decryptPasswordEnvVarName == null || decryptPasswordEnvVarName.length() == 0) {
            String errMessage= "The name of the env var which holds the decryption password cannot be null nor empty";
            log.fatal(errMessage);
            throw new RuntimeException(errMessage);
        }        
    }
    
    @Override
    public TokenPair provide(){
        if (accessToken == null){
            SimplePBEConfig config = new SimplePBEConfig(); 
            config.setPassword(getPassword());
        
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor(); 
            encryptor.setConfig(config);
            encryptor.initialize();
        
            String accessTokenDecrypted = encryptor.decrypt(accessTokenEncrypted);
            String accessTokenSecretDecrypted = encryptor.decrypt(accessTokenSecretEncrypted);
        
            return new TokenPair(accessTokenDecrypted, accessTokenSecretDecrypted);
        }
        
        return new TokenPair(accessToken, accessTokenSecret);        
    }
    
    private String getPassword(){
        String password = System.getenv(decryptPasswordEnvVarName);
        if (password == null || password.length() == 0) {
            String errMsg = "The password cannot be null nor empty";
            log.fatal(errMsg);
            throw new IllegalStateException(errMsg);
        }
        return password;
    }

    //setter method to be used by Spring
    public void setAccessTokenEncrypted(String accessTokenEncrypted){
        this.accessTokenEncrypted = accessTokenEncrypted;
    }

    //setters methods useful for testing
    public void setAccessTokenSecretEncrypted(String accessTokenSecretEncrypted){
        this.accessTokenSecretEncrypted = accessTokenSecretEncrypted;
    }

    public void setDecryptPasswordEnvVarName(String decryptPasswordEnvVarName){
        this.decryptPasswordEnvVarName = decryptPasswordEnvVarName;
    }

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public void setAccessTokenSecret(String accessTokenSecret){
        this.accessTokenSecret = accessTokenSecret;
    }

    public void setEncryptedAccessTokenPairProvider(EncryptedTokenPairProvider encryptedAccessTokenPairProvider){
        this.encryptedAccessTokenPairProvider = encryptedAccessTokenPairProvider;
    }
}


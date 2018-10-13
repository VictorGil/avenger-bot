package net.devaction.avengerbot.config.twitterkey;

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
public class DecryptedKeyPairProviderImpl implements InitializingBean, DecryptedKeyPairProvider{
    private static final Logger log = LogManager.getLogger(DecryptedKeyPairProviderImpl.class);
    
    private String consumerApiKeyEncrypted;
    private String consumerApiSecretEncrypted;
    private String decryptPasswordEnvVarName;
        
    //These are meant to be used only when testing
    private String consumerApiKey;
    private String consumerApiSecret;
    
    private EncryptedKeyPairProvider encryptedKeyPairProvider;
    
    @Override
    public void afterPropertiesSet() throws Exception{
        consumerApiKeyEncrypted = encryptedKeyPairProvider.getTwitterConsumerApiKeyEncrypted();
        if (consumerApiKeyEncrypted == null || consumerApiKeyEncrypted.length() == 0){
            String errMessage = "The encrypted Twitter consumer API key cannot be null nor empty";
            log.fatal(errMessage);
            throw new RuntimeException(errMessage);
        }
        
        consumerApiSecretEncrypted = encryptedKeyPairProvider.getTwitterConsumerApiSecretEncrypted();
        if (consumerApiSecretEncrypted == null || consumerApiSecretEncrypted.length() == 0){
            String errMessage = "The encrypted Twitter consumer API secret cannot be null nor empty";
            log.fatal(errMessage);
            throw new RuntimeException(errMessage);
        }        
        
        decryptPasswordEnvVarName = encryptedKeyPairProvider.getDecryptPasswordEnvVarName();
        if (decryptPasswordEnvVarName == null || decryptPasswordEnvVarName.length() == 0) {
            String errMessage= "The name of the env var which holds the decryption password cannot be null nor empty";
            log.fatal(errMessage);
            throw new RuntimeException(errMessage);
        }        
    }
    
    @Override
    public KeyPair provide(){
        if (consumerApiKey == null){
            SimplePBEConfig config = new SimplePBEConfig(); 
            config.setPassword(getPassword());
        
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor(); 
            encryptor.setConfig(config);
            encryptor.initialize();
        
            String consumerApiKeyDecrypted = encryptor.decrypt(consumerApiKeyEncrypted);
            String consumerApiSecretDecrypted = encryptor.decrypt(consumerApiSecretEncrypted);
        
            return new KeyPair(consumerApiKeyDecrypted, consumerApiSecretDecrypted);
        }
        
        return new KeyPair(consumerApiKey, consumerApiSecret);        
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
    public void setEncryptedKeyPairProvider(EncryptedKeyPairProvider encryptedKeyPairProvider){
        this.encryptedKeyPairProvider = encryptedKeyPairProvider;
    }

    //setter methods useful for testing
    public void setConsumerApiKeyEncrypted(String consumerApiKeyEncrypted){
        this.consumerApiKeyEncrypted = consumerApiKeyEncrypted;
    }

    public void setConsumerApiSecretEncrypted(String consumerApiSecretEncrypted){
        this.consumerApiSecretEncrypted = consumerApiSecretEncrypted;
    }

    public void setDecryptPasswordEnvVarName(String decryptPasswordEnvVarName){
        this.decryptPasswordEnvVarName = decryptPasswordEnvVarName;
    }

    public void setConsumerApiKey(String consumerApiKey){
        this.consumerApiKey = consumerApiKey;
    }

    public void setConsumerApiSecret(String consumerApiSecret){
        this.consumerApiSecret = consumerApiSecret;
    }
}


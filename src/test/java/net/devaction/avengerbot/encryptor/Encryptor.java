package net.devaction.avengerbot.encryptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimplePBEConfig;

public class Encryptor {
    private static final Logger log = LogManager.getLogger(Encryptor.class);
    
    public static void main(String[] args){
        String toBeEncrypted = args[0];
        String password = args[1];
        log.info("Original value: " + toBeEncrypted + "\npassword: " + password);
        
        String encrypted = encrypt(toBeEncrypted, password);
        log.info("Encrypted value: " + encrypted);
    }

    private static String encrypt(String tobeEncrypted, String password){
        SimplePBEConfig config = new SimplePBEConfig(); 
        config.setPassword(password);
        
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor(); 
        encryptor.setConfig(config);
        encryptor.initialize();
        return encryptor.encrypt(tobeEncrypted);
    }
}


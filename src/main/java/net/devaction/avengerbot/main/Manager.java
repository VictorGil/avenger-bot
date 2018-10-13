package net.devaction.avengerbot.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.devaction.avengerbot.tweetscapturedconsumer.TweetsConsumer;
import net.devaction.avengerbot.tweetscapturedproducer.TweetsProducer;
import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
@SuppressWarnings("restriction")
public class Manager implements SignalHandler {
    private static final Logger log = LogManager.getLogger(Manager.class);
    
    //this is used to gracefully shutdown the execution whenever we want to
    private static final String WINCH_SIGNAL = "WINCH";
    
    private TweetsProducer tweetsProducer;
    private TweetsConsumer tweetsConsumer;
    
    public void start(){
        registerThisAsOsSignalHandler();
        
        tweetsConsumer.start();
        tweetsProducer.start();
    }
    
    private void registerThisAsOsSignalHandler(){
        log.info("Going to register this object to handle the " + WINCH_SIGNAL + " signal");
        try{
            sun.misc.Signal.handle( new sun.misc.Signal(WINCH_SIGNAL), this);
        } catch(IllegalArgumentException ex){
            // Most likely this is a signal that's not supported on this
            // platform or with the JVM as it is currently configured
            log.error(ex, ex);
        } catch(Throwable th){
            // We may have a serious problem, including missing classes
            // or changed APIs
            log.error("I guess the signal is unsupported: " + WINCH_SIGNAL, th);
        }        
    }
    
    @Override
    public void handle(Signal signal) {
        log.info("Signal " + signal.getName() + " has been captured. Going to stop the tweets producer " + 
                "and also the tweets consumer");
        tweetsProducer.stop();
        tweetsConsumer.stop();
        
        try{
            Thread.sleep(2000);
        } catch (InterruptedException ex){
            log.error(ex, ex);
            throw new RuntimeException(ex);
        }
        log.info("Exiting");
        System.exit(0);
    }

    public void setTweetsProducer(TweetsProducer tweetsProducer){
        this.tweetsProducer = tweetsProducer;
    }

    public void setTweetsConsumer(TweetsConsumer tweetsConsumer){
        this.tweetsConsumer = tweetsConsumer;
    }
}


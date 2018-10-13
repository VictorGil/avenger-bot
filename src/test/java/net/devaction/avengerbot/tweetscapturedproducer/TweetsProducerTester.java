package net.devaction.avengerbot.tweetscapturedproducer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import net.devaction.avengerbot.tweetscapturedproducer.TweetsProducer;
import sun.misc.Signal;
/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
@SuppressWarnings("restriction")
public class TweetsProducerTester implements sun.misc.SignalHandler{
    private static final Logger log = LogManager.getLogger(TweetsProducerTester.class);
    
    //this is used to gracefully shutdown the execution whenever we want to
    private static final String WINCH_SIGNAL = "WINCH";
    
    private TweetsProducer tweetsProducer; 
    
    {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/beans.xml");
        tweetsProducer = (TweetsProducer) appContext.getBean("tweetsProducer");
        ((ConfigurableApplicationContext) appContext).close();
    }
    
    public static void main(String[] args) {
        new TweetsProducerTester().run();    
    }
    
    private void run(){
        registerThisAsOsSignalHandler();
        
        tweetsProducer.start();
        
        try{
            final int TEN_MINUTES = 600000; 
            Thread.sleep(TEN_MINUTES);
        } catch (InterruptedException ex){
            log.error(ex, ex);
            throw new RuntimeException(ex);
        }
        
        tweetsProducer.stop();
    }
    
    private void registerThisAsOsSignalHandler(){
        log.info("Going to register this object to handle the " + WINCH_SIGNAL + " signal");
        try{
            sun.misc.Signal.handle(new sun.misc.Signal(WINCH_SIGNAL), this);
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
        log.info("Signal " + signal.getName() + " has been captured. Going to stop the tweets producer.");
        tweetsProducer.stop();
        try{
            Thread.sleep(2000);
        } catch (InterruptedException ex){
            log.error(ex, ex);
            throw new RuntimeException(ex);
        }
        log.info("Exiting");
        System.exit(0);
    }
 }


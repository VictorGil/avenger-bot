package net.devaction.avengerbot.main;

import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.logging.log4j.LogManager;

/**
 * @author Víctor Gil
 * 
 * since October 2018 
 */
public class AvengerBotMain{
    private static final Logger log = LogManager.getLogger(AvengerBotMain.class);

    public static void main(String[] args){
        new AvengerBotMain().run();
    }
    
    void run(){
        log.info("Starting " + this.getClass().getSimpleName());
        ApplicationContext appContext = new ClassPathXmlApplicationContext("spring/beans.xml");
        Manager manager = (Manager) appContext.getBean("manager");
        ((ConfigurableApplicationContext) appContext).close();
        manager.start();
    }
}


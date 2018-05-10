package com.davidxl.config;

import com.davidxl.config.listener.ApplicationStartListener;
import com.davidxl.quartz.TestQuartsJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApplicationListenerConfig {


    @Bean
    public ApplicationStartListener applicationStartListener(){
        return new ApplicationStartListener();
    }


}
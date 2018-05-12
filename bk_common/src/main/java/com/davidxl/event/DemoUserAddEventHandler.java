package com.davidxl.event;

import com.davidxl.event.DemoUserAddEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DemoUserAddEventHandler {

    @EventListener
    public void doithUserAddEvent(DemoUserAddEvent event) {

        //throw  new RuntimeException("test event exception");
        System.out.printf("DemoUserAddEventHandler  event userid = " + event.getUser().getId() + "\n\n\n");
    }
}

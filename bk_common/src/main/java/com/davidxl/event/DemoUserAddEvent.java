package com.davidxl.event;

import com.davidxl.model.User;
import lombok.Data;
import org.springframework.context.ApplicationEvent;


@Data
public class DemoUserAddEvent extends ApplicationEvent {


    private User user;


    public DemoUserAddEvent(Object source,User user){
        super(source);
        this.user = user;
    }

}

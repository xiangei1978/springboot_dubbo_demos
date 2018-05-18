package com.davidxl.rocket_consumer;

import com.davidxl.common.status.SexType;
import com.davidxl.config.event.RocketmqEvent;
import com.davidxl.dubbo.service.UserService;
import com.davidxl.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;


/**
 * Created by xianglei on 2018/5/2.
 */
@Slf4j
@Component
public class ConsumerDemo {
    @Autowired
    UserService userService;

    private  int i=0;
    @EventListener(condition = "#event.msgs[0].topic=='TopicTest1' && ( #event.msgs[0].tags=='TagA' || #event.msgs[0].tags=='TagC') ")
    public void rocketmqMsgListen(RocketmqEvent event) throws UnsupportedEncodingException, InterruptedException {
//      DefaultMQPushConsumer consumer = event.getConsumer();
        try {
            String body =new String(event.getMsgs().get(0).getBody(),"utf-8");
            log.info("监听到一个消息达到-TopicTest1-TagA or TagC：" + event.getMsgs().get(0).getMsgId() +
                    "\n msg = " + event.getMsgs().get(0).toString() +
                    "\n body = " + body);
            i++;
            Assert.isTrue(i%2==0,"测试异常");


            User user = new User();


            user.setAge(4);
            user.setName("user" + i);
            user.setAmount(new BigDecimal("12.1"));
            user.setSex(SexType.female);

            Assert.state(userService.insertSelective(user)==1,"insert error");

            Thread.sleep(1000);


            // TODO 进行业务处理
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @EventListener(condition = "#event.msgs[0].topic=='TopicTest1' && #event.msgs[0].tags=='TagB'")
    public void rocketmqMsgListen1(RocketmqEvent event) throws UnsupportedEncodingException, InterruptedException {
//      DefaultMQPushConsumer consumer = event.getConsumer();
        try {
            i++;
            Assert.isTrue(i%2==0,"测试异常");
            Thread.sleep(1000);
            String body =new String(event.getMsgs().get(0).getBody(),"utf-8");
            log.info("监听到一个消息达到-TopicTest1-TagB：" + event.getMsgs().get(0).getMsgId() +
                    "\n msg = " + event.getMsgs().get(0).toString() +
                    "\n body = " + body);
            // TODO 进行业务处理
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @EventListener(condition = "#event.msgs[0].topic=='TopicTest2' && #event.msgs[0].tags=='TagA'")
    public void rocketmqMsgListen2(RocketmqEvent event) throws UnsupportedEncodingException, InterruptedException {
//      DefaultMQPushConsumer consumer = event.getConsumer();
        try {
            i++;
            Assert.isTrue(i%2==0,"测试异常");
            Thread.sleep(1000);
            String body =new String(event.getMsgs().get(0).getBody(),"utf-8");
            log.info("监听到一个消息达到-TopicTest2-TagA：" + event.getMsgs().get(0).getMsgId() +
                    "\n msg = " + event.getMsgs().get(0).toString() +
                    "\n body = " + body);
            // TODO 进行业务处理
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @EventListener(condition = "#event.msgs[0].topic=='TopicTest2' && #event.msgs[0].tags=='TagB'")
    public void rocketmqMsgListen3(RocketmqEvent event) throws UnsupportedEncodingException, InterruptedException {
//      DefaultMQPushConsumer consumer = event.getConsumer();
        try {
            i++;
            Assert.isTrue(i%2==0,"测试异常");
            Thread.sleep(1000);
            String body =new String(event.getMsgs().get(0).getBody(),"utf-8");
            log.info("监听到一个消息达到-TopicTest2-TagB：" + event.getMsgs().get(0).getMsgId() +
                    "\n msg = " + event.getMsgs().get(0).toString() +
                    "\n body = " + body);
            // TODO 进行业务处理
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}


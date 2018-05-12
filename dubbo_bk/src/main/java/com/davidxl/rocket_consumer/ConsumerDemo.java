package com.davidxl.rocket_consumer;

import com.davidxl.config.event.RocketmqEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Created by xianglei on 2018/5/2.
 */
@Slf4j
@Component
public class ConsumerDemo {
    @EventListener(condition = "#event.msgs[0].topic=='TopicTest1' && ( #event.msgs[0].tags=='TagA' || #event.msgs[0].tags=='TagC') ")
    public void rocketmqMsgListen(RocketmqEvent event) {
//      DefaultMQPushConsumer consumer = event.getConsumer();
        try {
            Thread.sleep(1000);
            String body =new String(event.getMsgs().get(0).getBody(),"utf-8");
            log.info("监听到一个消息达到-TopicTest1-TagA or TagC：" + event.getMsgs().get(0).getMsgId() +
                    "\n msg = " + event.getMsgs().get(0).toString() +
                    "\n body = " + body);
            // TODO 进行业务处理
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventListener(condition = "#event.msgs[0].topic=='TopicTest1' && #event.msgs[0].tags=='TagB'")
    public void rocketmqMsgListen1(RocketmqEvent event) {
//      DefaultMQPushConsumer consumer = event.getConsumer();
        try {
            Thread.sleep(1000);
            String body =new String(event.getMsgs().get(0).getBody(),"utf-8");
            log.info("监听到一个消息达到-TopicTest1-TagB：" + event.getMsgs().get(0).getMsgId() +
                    "\n msg = " + event.getMsgs().get(0).toString() +
                    "\n body = " + body);
            // TODO 进行业务处理
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @EventListener(condition = "#event.msgs[0].topic=='TopicTest2' && #event.msgs[0].tags=='TagA'")
    public void rocketmqMsgListen2(RocketmqEvent event) {
//      DefaultMQPushConsumer consumer = event.getConsumer();
        try {
            Thread.sleep(1000);
            String body =new String(event.getMsgs().get(0).getBody(),"utf-8");
            log.info("监听到一个消息达到-TopicTest2-TagA：" + event.getMsgs().get(0).getMsgId() +
                    "\n msg = " + event.getMsgs().get(0).toString() +
                    "\n body = " + body);
            // TODO 进行业务处理
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventListener(condition = "#event.msgs[0].topic=='TopicTest2' && #event.msgs[0].tags=='TagB'")
    public void rocketmqMsgListen3(RocketmqEvent event) {
//      DefaultMQPushConsumer consumer = event.getConsumer();
        try {
            Thread.sleep(1000);
            String body =new String(event.getMsgs().get(0).getBody(),"utf-8");
            log.info("监听到一个消息达到-TopicTest2-TagB：" + event.getMsgs().get(0).getMsgId() +
                    "\n msg = " + event.getMsgs().get(0).toString() +
                    "\n body = " + body);
            // TODO 进行业务处理
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


package com.davidxl.controller;


import com.davidxl.config.properties.MyRocketmqProperties;
import com.davidxl.model.User;
import com.davidxl.web.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xianglei on 2018/4/24.
 */

@Api(value="RocketMQ 测试controller")
@RestController
public class RocketMQTestController {

    @Autowired
    private DefaultMQProducer defaultProducer;

    @Autowired
    private TransactionMQProducer transactionProducer;


    private int i = 0;

    @ApiOperation(value="RocketMQ sendMsg 测试", notes="RocketMQ sendMsg 测试")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "user",
                    value = "{ \"name\": \"xlr\", \"age\": 3, \"amount\": 10.11, \"sex\": \"male=男;female=女;unknown=未知\"  }", required = true, dataType = "string")
    })
    @RequestMapping(value="/sendMsg_TagA", method= RequestMethod.POST)
    public CommonResult sendMsg_TagA(@RequestBody User user){
        CommonResult r = new CommonResult();
                Message msg = new Message("TopicTest1", // topic
                "TagA", // tag
                "OrderID00" + i, // key
                ("Hello zebra mq" + i).getBytes());// body

        sendMsg(msg);

        return   r ;

    }


    @ApiOperation(value="RocketMQ sendMsg 测试", notes="RocketMQ sendMsg 测试")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "user",
                    value = "{ \"name\": \"xlr\", \"age\": 3, \"amount\": 10.11, \"sex\": \"male=男;female=女;unknown=未知\"  }", required = true, dataType = "string")
    })
    @RequestMapping(value="/sendMsg_TagB", method= RequestMethod.POST)
    public CommonResult sendMsg_TagB(@RequestBody User user){
        CommonResult r = new CommonResult();
        Message msg = new Message("TopicTest1", // topic
                "TagB", // tag
                "OrderID00" + i, // key
                ("Hello zebra mq" + i).getBytes());// body

        sendMsg(msg);

        return   r ;

    }

    @ApiOperation(value="RocketMQ sendTransactionMsg 测试", notes="RocketMQ sendTransactionMsg 测试")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "user",
                    value = "{ \"name\": \"xlr\", \"age\": 3, \"amount\": 10.11, \"sex\": \"male=男;female=女;unknown=未知\"  }", required = true, dataType = "string")
    })
    @RequestMapping(value="/sendTransactionMsg", method= RequestMethod.POST)
    public CommonResult sendTransactionMsg(@RequestBody User user){
        CommonResult r = new CommonResult();

        sendTransactionMsg();

        return   r ;

    }

    @ApiOperation(value="RocketMQ sendMsgOrder 测试", notes="RocketMQ sendMsgOrder 测试")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "user",
                    value = "{ \"name\": \"xlr\", \"age\": 3, \"amount\": 10.11, \"sex\": \"male=男;female=女;unknown=未知\"  }", required = true, dataType = "string")
    })
    @RequestMapping(value="/sendMsgOrder", method= RequestMethod.POST)
    public CommonResult sendMsgOrder(@RequestBody User user){
        CommonResult r = new CommonResult();

        sendMsgOrder();

        return   r ;

    }



    private void sendMsg(Message msg) {
//        Message msg = new Message("TopicTest1", // topic
//                "TagA", // tag
//                "OrderID00" + i, // key
//                ("Hello zebra mq" + i).getBytes());// body
        try {
            defaultProducer.send(msg, new SendCallback() {

                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("send Msg onSuccess: \n" +
                                        "msg = " + msg.toString() + "\n" +
                                        "sendResult = " + sendResult + "\n\n");
                    // TODO 发送成功处理
                }

                @Override
                public void onException(Throwable e) {
                    System.out.println("send Msg onException: \n" +
                            "msg = " + msg.getBody() + "\n\n"  );
                    System.out.println(e);
                    // TODO 发送失败处理
                }
            });
            i++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String sendTransactionMsg() {
        SendResult sendResult = null;
        try {
            // 构造消息
            Message msg = new Message("TopicTest1", // topic
                    "TagA", // tag
                    "OrderID001", // key
                    ("Hello zebra mq").getBytes());// body

            // 发送事务消息，LocalTransactionExecute的executeLocalTransactionBranch方法中执行本地逻辑
            sendResult = transactionProducer.sendMessageInTransaction(msg, (Message msg1, Object arg) -> {
                int value = 1;

                // TODO 执行本地事务，改变value的值
                // ===================================================
                System.out.println("执行本地事务。。。完成");
                if (arg instanceof Integer) {
                    value = (Integer) arg;
                }
                // ===================================================

                if (value == 0) {
                    throw new RuntimeException("Could not find db");
                } else if ((value % 5) == 0) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } else if ((value % 4) == 0) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                }
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }, 4);
            System.out.println(sendResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sendResult.toString();
    }


    private void sendMsgOrder() {
        Message msg = new Message("TopicTest1", // topic
                "TagA", // tag
                "OrderID00" + i, // key
                ("Hello zebra mq" + i).getBytes());// body
        try {
            defaultProducer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    System.out.println("MessageQueue" + arg);
                    int index = ((Integer) arg) % mqs.size();
                    return mqs.get(index);
                }
            }, i);// i==arg
            i++;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

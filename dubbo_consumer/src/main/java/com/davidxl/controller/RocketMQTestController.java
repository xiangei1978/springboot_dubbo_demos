package com.davidxl.controller;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;
import com.davidxl.model.User;
import com.davidxl.web.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xianglei on 2018/4/24.
 */

@Api(value="RocketMQ 测试controller")
@RestController
public class RocketMQTestController {

    @ApiOperation(value="RocketMQ 测试", notes="RocketMQ 测试")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", name = "user",
                    value = "{ \"name\": \"xlr\", \"age\": 3, \"amount\": 10.11, \"sex\": \"male=男;female=女;unknown=未知\"  }", required = true, dataType = "string")
    })
    @RequestMapping(value="/rocketmqtest", method= RequestMethod.POST)
    public CommonResult insertUser(@RequestBody User user){
        CommonResult r = new CommonResult();


        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("192.168.0.253:9876");
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 3; i++) {
            try {
                Message msg = new Message("u_topic",
                        "TagA",
                        ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET)
                );
                SendResult sendResult = producer.send(msg);
                LocalTransactionExecuter tranExecuter = new LocalTransactionExecuter() {

                    @Override
                    public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg) {
                        // TODO Auto-generated method stub


                        return null;
                    }
                };

                //producer.sendMessageInTransaction(msg, tranExecuter, arg)
                System.out.println(sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

        producer.shutdown();


//        if (userService.insertSelective(user) !=1)
//            throw  new  NormalException(NormalResultType.err_database_insert);

        return   r ;

    }
}

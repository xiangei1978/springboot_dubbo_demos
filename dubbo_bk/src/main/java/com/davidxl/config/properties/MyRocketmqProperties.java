package com.davidxl.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by xianglei on 2018/5/2.
 */

@Configuration
@Data
@ConfigurationProperties(prefix = MyRocketmqProperties.PREFIX )
public class MyRocketmqProperties {
    public static final String PREFIX = "project.rocketmq";

    private String namesrvAddr;

    private String producerGroupName;

    private String transactionProducerGroupName;

    private String consumerGroupName;

    private String producerInstanceName;

    private String consumerInstanceName;

    private String producerTranInstanceName;

    private int consumerBatchMaxSize;

    private boolean consumerBroadcasting;

    private boolean enableHisConsumer;

    private boolean enableOrderConsumer;

    private List<String> subscribe  ;

}


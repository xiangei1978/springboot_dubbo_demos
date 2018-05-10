package com.davidxl.config.listener;


import com.davidxl.config.properties.QuartzProperties;
import com.davidxl.quartz.TestQuartsJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;


public class ApplicationStartListener implements ApplicationListener<ContextRefreshedEvent>{

    @Autowired
    Scheduler scheduler;

    @Autowired
    QuartzProperties quartzProperties;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(contextRefreshedEvent.getApplicationContext().getParent() != null){
            return;
        }

        try {
            createQuartsJob();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建创建商品定时任务
     */
    private  void createQuartsJob( ) throws Exception
    {
        List<String> list = quartzProperties.getJobs();
//        List<String> subscribeList = new ArrayList<>();
//        subscribeList.add("TopicTest1:TagA");
        for (String job : list) {

        //任务名称

//        String name =TestQuartsJob.class.getName();
        String name =  job.split(";")[0];
        //任务所属分组
//        String group = TestQuartsJob.class.getName();
        String group = job.split(";")[0];

        Class c = Class.forName(name);

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.split(";")[1]);
        //创建任务
        JobDetail jobDetail = JobBuilder.newJob(c).withIdentity(name,group).build();
        //创建任务触发器
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name,group).withSchedule(scheduleBuilder).build();
        //将触发器与任务绑定到调度器内
        scheduler.deleteJob(jobDetail.getKey());
        scheduler.scheduleJob(jobDetail, trigger);
        }

    }
}
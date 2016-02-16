package com.spring.study.quartz.service;


import com.spring.study.quartz.job.QuartzJobFactory;
import com.spring.study.quartz.bean.ScheduleJobBin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * ClassName: BackTablesDelQZ
 * Description: 删除回收站表定时器
 * Date: 2016/2/14 8:40
 *
 * @author sm12652
 * @version V1.0
 */
@Component
public class DynamicQuartz {

    private static final Logger logger = LogManager.getLogger(DynamicQuartz.class);

    private static Scheduler scheduler;
    static {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring/applicationContext.xml");
        scheduler = (StdScheduler) context.getBean("schedulerFactoryBean");
    }


    public void standBy() throws SchedulerException {
        scheduler.standby();
    }

    public void start() throws SchedulerException {
        scheduler.start();
    }

    public static void deploy(ScheduleJobBin job) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        //获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        //不存在，创建一个
        if (null == trigger) {

            String clazzName = job.getJobClazz();
            Class clazz = null;
            try {
                clazz = Class.forName("com.spring.study.quartz.job." + clazzName);
            } catch (ClassNotFoundException e) {
            }
            JobDetail jobDetail = JobBuilder.newJob(clazz)
                    .withIdentity(job.getJobName(), job.getJobGroup()).build();
            jobDetail.getJobDataMap().put("scheduleJob", job);

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
                    .getCronExpression());
            //按新的cronExpression表达式构建一个新的trigger
            trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            // Trigger已存在，那么更新相应的定时设置
            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
                    .getCronExpression());
            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder).build();
            //按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        }
    }

    public static void listALL() throws SchedulerException {
        GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
        Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
        List<ScheduleJobBin> jobList = new ArrayList<>();
        for (JobKey jobKey : jobKeys) {
            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            for (Trigger trigger : triggers) {
                ScheduleJobBin job = new ScheduleJobBin();
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                job.setDesc("触发器:" + trigger.getKey());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                jobList.add(job);
            }
        }
        System.out.println("++++++++++++++ "+jobList);
    }

    public static void listRunning() throws SchedulerException {
        List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
        List<ScheduleJobBin> jobList = new ArrayList<>(executingJobs.size());
        for (JobExecutionContext executingJob : executingJobs) {
            ScheduleJobBin job = new ScheduleJobBin();
            JobDetail jobDetail = executingJob.getJobDetail();
            JobKey jobKey = jobDetail.getKey();
            Trigger trigger = executingJob.getTrigger();
            job.setJobName(jobKey.getName());
            job.setJobGroup(jobKey.getGroup());
            job.setDesc("触发器:" + trigger.getKey());
            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
            job.setJobStatus(triggerState.name());
            if (trigger instanceof CronTrigger) {
                CronTrigger cronTrigger = (CronTrigger) trigger;
                String cronExpression = cronTrigger.getCronExpression();
                job.setCronExpression(cronExpression);
            }
            jobList.add(job);
        }
    }

    /**
     * 暂停任务
     * @param job
     * @throws SchedulerException
     */
    public static void pause(ScheduleJobBin job) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复任务
     * @param job
     */
    public static void resume(ScheduleJobBin job) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        scheduler.resumeJob(jobKey);
    }

    /**
     *  删除任务
     */
    public static void delete(ScheduleJobBin job) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        scheduler.deleteJob(jobKey);
    }

    /**
     * 立即运行任务
     * @param job
     */
    public static void trigger(ScheduleJobBin job) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
        scheduler.triggerJob(jobKey);
    }

    /**
     * 更新任务的时间表达式
     * @param job
     * @throws SchedulerException
     */
    public static void updateCons(ScheduleJobBin job) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        //获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        //表达式调度构建器
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
                .getCronExpression());
        //按新的cronExpression表达式重新构建trigger
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                .withSchedule(scheduleBuilder).build();
        //按新的trigger重新设置job执行
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    public static void main(String[] args) {


        ScheduleJobBin job1 = new ScheduleJobBin();
        job1.setJobId("1000" + 1);
        job1.setJobName("job_" + 1);
        job1.setJobGroup("dataWork");
        job1.setJobStatus("1");
        job1.setCronExpression("0/5 * * * * ?");
        job1.setDesc("数据导入任务");
        job1.setJobClazz("FirstQuartz");

        ScheduleJobBin job2 = new ScheduleJobBin();
        job2.setJobId("1000" + 2);
        job2.setJobName("job_" + 2);
        job2.setJobGroup("dataWork");
        job2.setJobStatus("1");
        job2.setCronExpression("0/5 * * * * ?");
        job2.setDesc("数据导入任务");
        job2.setJobClazz("SecondQuartz");


        try {
            deploy(job1);
            deploy(job2);

            listALL();

        } catch (SchedulerException e) {
            logger.info("..............挂了");
        }
    }
}

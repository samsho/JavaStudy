package com.spring.study.quartz.job;

import org.quartz.*;

/**
 *
 */
@DisallowConcurrentExecution
//　　QuartzJobBean和MethodInvokingJob是无状态的，StatefulMethodInvokingJob是有状态的。
public abstract class QuartzJobFactory implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        this.run(jobDetail);
    }

    protected void run(JobDetail jobDetail) throws JobExecutionException{}

}
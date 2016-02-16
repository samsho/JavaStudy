package com.spring.study.quartz.job;

import org.quartz.JobDetail;
import org.quartz.JobExecutionException;

/**
 * ClassName: FirstQuartz
 * Description:
 * Date: 2016/2/16 14:39
 *
 * @author sm12652
 * @version V1.0
 */
public class SecondQuartz extends QuartzJobFactory {

    @Override
    protected void run(JobDetail jobDetail) throws JobExecutionException {
        System.out.println(".....SecondQuartz.....");
    }
}

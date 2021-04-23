package com.ibos.test;

import com.ibos.jobs.TimingJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

@Component
public class QuartzJobExecutor {
    private Scheduler scheduler;

    public void executeJob() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();

        JobDetail job = JobBuilder.newJob(TimingJob.class)
                .withIdentity("myJob", "myGroup")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("myTrigger", "myGroup")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(3)
                        .withRepeatCount(5))
                .build();

        scheduler.scheduleJob(job, trigger);

        scheduler.start();
    }
}

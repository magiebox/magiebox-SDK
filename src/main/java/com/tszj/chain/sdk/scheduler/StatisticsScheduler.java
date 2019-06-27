package com.tszj.chain.sdk.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component("statisticsScheduler")
@Slf4j
public class StatisticsScheduler {

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    @Scheduled(cron = "0 58 23  * * * ")
    public void statisticsDate() {

    }
}

package com.tszj.chain.sdk.start;

import com.tszj.chain.sdk.service.LockCoinsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DataProcessStarter implements ApplicationRunner {

    @Value("${start.investment}")
    Boolean isInvestment;

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    LockCoinsService lockCoinsService;

    @Override
    public void run(ApplicationArguments args) {

        if (isInvestment) {
            taskExecutor.execute(() -> lockCoinsService.queryChainSaveLockCoins());
        }
    }


}

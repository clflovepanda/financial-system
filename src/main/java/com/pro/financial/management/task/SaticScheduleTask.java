package com.pro.financial.management.task;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SaticScheduleTask {

    /**
     * 修改支出状  超过30天
     */
    @Scheduled(cron = "0 0 1 * * ?")
    private void exchangeExpenditureState() {
        System.out.println("do something");
    }
}

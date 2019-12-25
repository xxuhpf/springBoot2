package com.xxu.demo.scheduled;

import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledDemo {
	//cron表示触发
	@Scheduled(cron="0/2 * * * * ?")
	public void scheduledMethod(){
	 System.out.println("定时器被触发"+new Date());
	}
}

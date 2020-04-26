package com.phu.onlineshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@SuppressWarnings("HideUtilityClassConstructor")
public class OnlineShopApp
{
    public static void main(final String[] args)
    {
        SpringApplication.run(OnlineShopApp.class, args);
    }
}

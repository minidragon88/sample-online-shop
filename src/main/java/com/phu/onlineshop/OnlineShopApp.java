package com.phu.onlineshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineShopApp
{
    private OnlineShopApp()
    {}

    public static void main(final String[] args)
    {
        SpringApplication.run(OnlineShopApp.class, args);
    }
}

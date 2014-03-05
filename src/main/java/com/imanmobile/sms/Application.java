package com.imanmobile.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * Created by jome on 2014/02/28.
 */

@ComponentScan(basePackages = {"com.imanmobile.sms", "com.imanmobile.sms.oneapi"})
@EnableAutoConfiguration
@PropertySources(value = {
        @PropertySource(value = { "file:./application.properties"})})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
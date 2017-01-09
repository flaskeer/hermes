package com.asterisk.opensource.config;

import com.asterisk.opensource.spider.transfer.SinaTransfer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenSourceConfiguration {

    @Bean
    public SinaTransfer sinaTransfer() {
        return new SinaTransfer();
    }



}

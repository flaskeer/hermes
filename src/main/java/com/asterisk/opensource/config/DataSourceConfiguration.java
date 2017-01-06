package com.asterisk.opensource.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
public class DataSourceConfiguration {

    @Bean(initMethod = "init",destroyMethod = "close")
    public DataSource druidDataSource(@Value("${jdbc.driverClassName}")String driver,
                                      @Value("${jdbc.url}")String url,
                                      @Value("${jdbc.username}")String username,
                                      @Value("${jdbc.password}")String password) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }


}

package com.asterisk.opensource;

import com.asterisk.opensource.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class HermesApplication {


    private static final Logger LOGGER = LoggerFactory.getLogger(HermesApplication.class);

    @Resource
    private Environment environment;

    @PostConstruct
    public void initApplication() {
        LOGGER.info("Running with Spring profile:{}", Arrays.toString(environment.getActiveProfiles()));
        List<String> activeProfiles = Arrays.asList(environment.getActiveProfiles());
        if (activeProfiles.contains(Constants.DEV_KEY) && activeProfiles.contains(Constants.PROD_KEY)) {
            LOGGER.error("You have misconfigured your application! It should not run " +
                    "with both the 'dev' and 'prod' profiles at the same time.");
        }

    }

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(HermesApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOGGER.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\thttp://127.0.0.1:{}\n\t",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"));

    }

}

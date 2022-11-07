package com.nttdata.bootcamp.msbankdebitcard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**.*/
@SpringBootApplication
@EnableEurekaClient
public class MsBankDebitCardApplication {

  public static void main(String[] args) {
    SpringApplication.run(MsBankDebitCardApplication.class, args);
  }

}

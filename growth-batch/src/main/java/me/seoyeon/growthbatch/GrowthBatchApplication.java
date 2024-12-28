package me.seoyeon.growthbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"me.seoyeon.mailclient", "me.seoyeon.growthbatch"})
@SpringBootApplication
public class GrowthBatchApplication {

  public static void main(String[] args) {
    SpringApplication.run(GrowthBatchApplication.class, args);
  }
}

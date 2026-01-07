package com.mvabal.techtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TechtestApplication {

  public static void main(String[] args) {
    SpringApplication.run(TechtestApplication.class, args);
  }

}

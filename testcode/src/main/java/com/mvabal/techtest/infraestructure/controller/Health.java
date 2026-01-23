// Health entrypoint for the application
//
package com.mvabal.techtest.infraestructure.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Health {
  @GetMapping("/health")
  public String health() {
    return "OK";
  }
}

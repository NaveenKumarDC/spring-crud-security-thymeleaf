package com.learning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Author: Naveen Kumar D C
 * Date: 07/07/24
 */
@Controller
public class DummyController {
  // create a mapping for "/hello"
  @GetMapping("/hello")
  public String sayHello(Model theModel) {

    theModel.addAttribute("theDate", java.time.LocalDateTime.now());

    return "helloworld";
  }

  // add a request mapping for /manager

  @GetMapping("/manager")
  public String showManagers() {

    return "manager/manger-details";
  }

  // add request mapping for /admin

  @GetMapping("/admin")
  public String showSystems() {

    return "admin/admin-details";
  }
}
package com.learning.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserLoginController {

    @GetMapping("/custom-login")
    public String loginPage() {

      /*  return "plain-login";
        return "custom-login-v1";*/
        return "login/custom-login-v2";
    }

    // add request mapping for /access-denied

    @GetMapping("/access-denied")
    public String showAccessDenied() {

        return "login/access-denied";
    }


}

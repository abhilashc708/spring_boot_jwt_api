package com.testapi.springbootrespapijwt.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping(value="/demo")
	public String demo() {
		return "Welcome to Jwt Api Demo";
	}
}

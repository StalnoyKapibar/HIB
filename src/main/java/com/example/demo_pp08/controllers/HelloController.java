package com.shortn0tes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created on 1/11/2018.
 */
@Controller
public class HelloController {

	@RequestMapping("/")
	@ResponseBody
	String hello() {
		return "Hello World!";
	}
}

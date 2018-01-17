package com.blgroup.jkl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping(value="/index")
public class TestController {
	private static Logger log = LoggerFactory.getLogger(TestController.class);
	@RequestMapping(value="/a")
	public String test(){
		System.out.println(1111);
		log.trace("======trace");  
        log.debug("======debug");  
        log.info("======info");  
        log.warn("======warn");  
        log.error("======error");  
		return "index";
	}
}

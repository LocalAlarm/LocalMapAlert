package com.spring.dongnae.bbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ch.qos.logback.core.model.Model;

@Controller
public class BbsController {

	@GetMapping("/eventAccidents")
    @ResponseBody
	public String getEventAccidents(@RequestParam("mapIdx") String mapIdx) {
	    System.out.println("mapIdx: " + mapIdx);
	    return "event_accidents";
	}
}

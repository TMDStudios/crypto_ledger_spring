package com.tmdstudios.cls.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tmdstudios.cls.services.UserService;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String index(Model model) {
	    return "index.jsp";
	}

}

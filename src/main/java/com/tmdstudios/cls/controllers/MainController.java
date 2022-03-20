package com.tmdstudios.cls.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmdstudios.cls.models.LoginUser;
import com.tmdstudios.cls.models.User;
import com.tmdstudios.cls.services.CoinService;
import com.tmdstudios.cls.services.UserService;

@Controller
public class MainController {
	
	private boolean showWatchlist = false;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CoinService coinService;
	
	@GetMapping("/")
	public String index(Model model) {
	    return "index.jsp";
	}
	
	@GetMapping("/login")
	public String auth(Model model) {
	    model.addAttribute("newUser", new User());
	    model.addAttribute("newLogin", new LoginUser());
	    return "login.jsp";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newUser") User newUser, 
			BindingResult result, Model model, HttpSession session) {

	    User user = userService.register(newUser, result);
	     
	    if(result.hasErrors()) {
	        model.addAttribute("newLogin", new LoginUser());
	        return "login.jsp";
	    }

	    session.setAttribute("user", user);
	 
	    return "redirect:/";
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin, 
			BindingResult result, Model model, HttpSession session) {
	     
		User user = userService.login(newLogin, result);
	 
	    if(result.hasErrors() || user==null) {
	        model.addAttribute("newUser", new User());
	        return "login.jsp";
	    }
	     
	    session.setAttribute("user", user);
	 
	    return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.setAttribute("user", null); 
	    return "redirect:/";
	}
	
	@GetMapping("/prices")
	public String viewPrices(HttpSession session, Model model) {
	 
		User user = (User) session.getAttribute("user");
//		if(user==null) {
//			return "redirect:/logout";
//		}
		
		if(showWatchlist) {
			User thisUser = userService.findById(user.getId());
			model.addAttribute("coins", thisUser.getCoins());
		}else {
			model.addAttribute("coins", coinService.allCoins());
		}
		
		session.setAttribute("starOutline", "https://tmdstudios.files.wordpress.com/2022/03/goldstaroutline-1.png");
		session.setAttribute("starFull", "https://tmdstudios.files.wordpress.com/2022/03/goldstar.png");
		session.setAttribute("upArrow", "https://tmdstudios.files.wordpress.com/2022/03/uparrow-1.png");
		session.setAttribute("downArrow", "https://tmdstudios.files.wordpress.com/2022/03/downarrow-1.png");
		session.setAttribute("showWatchlist", showWatchlist);
//		User thisUser = userService.findById(user.getId());
//		model.addAttribute("myCoins", thisUser.getCoins());
		 
		return "view_prices.jsp";
	}
	
	@RequestMapping("/watchlist")
	public String watchlist(HttpSession session, Model model) {
	 
//		User user = (User) session.getAttribute("user");
//		if(user==null) {
//			return "redirect:/logout";
//		}
		
		showWatchlist = !showWatchlist;
		session.setAttribute("showWatchlist", showWatchlist);
		 
		return "redirect:/prices";
	}
	
	@GetMapping("/coins/{id}")
	public String coinDetails(HttpSession session, Model model, @PathVariable("id") Long id) {
		model.addAttribute("coin", coinService.findById(id));
		 
		return "coin_details.jsp";
	}

}

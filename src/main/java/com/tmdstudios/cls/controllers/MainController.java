package com.tmdstudios.cls.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.tmdstudios.cls.models.Coin;
import com.tmdstudios.cls.models.LoginUser;
import com.tmdstudios.cls.models.User;
import com.tmdstudios.cls.services.CoinDataService;
import com.tmdstudios.cls.services.CoinService;
import com.tmdstudios.cls.services.UserService;

@Controller
public class MainController {
	
	private CoinDataService coinDataService = new CoinDataService();
	
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
		
		try {
			List<Coin> coins = coinDataService.fetchCoinData();
			List<Long> updatedCoins = new ArrayList<>();
			if(coinService.allCoins().size()>0) {
				for(Coin coin:coins) {
					Coin dbCoin = coinService.findBySymbol(coin.getSymbol());
					if(dbCoin!=null) {
						updatedCoins.add(dbCoin.getId());
						List<User> users = dbCoin.getUsers();
						Long coinId = dbCoin.getId();
						dbCoin = coin;

						dbCoin.setId(coinId);
						dbCoin.setUsers(users);
						
						coinService.updateCoin(dbCoin);
					}else {
						coinService.addCoin(coin);
						System.out.println("COIN ID: "+coin.getId());
						updatedCoins.add(coin.getId());
					}
				}
				for(Coin coin:coinService.allCoins()) {
					if(!updatedCoins.contains(coin.getId())) {
						try {
							coinService.deleteCoin(coin);
						}catch(DataIntegrityViolationException e) {
							coin.setCoinRank(101l);
							coinService.updateCoin(coin);
						}
					}
				}
			}else {
				for(Coin coin:coins) {
					coinService.addCoin(coin);
				}
			}
			model.addAttribute("coins", coinService.allCoins());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		session.setAttribute("starOutline", "https://tmdstudios.files.wordpress.com/2022/03/goldstaroutline-1.png");
		session.setAttribute("starFull", "https://tmdstudios.files.wordpress.com/2022/03/goldstar.png");
		session.setAttribute("upArrow", "https://tmdstudios.files.wordpress.com/2022/03/uparrow-1.png");
		session.setAttribute("downArrow", "https://tmdstudios.files.wordpress.com/2022/03/downarrow-1.png");
//		User thisUser = userService.findById(user.getId());
//		model.addAttribute("myCoins", thisUser.getCoins());
		 
		return "view_prices.jsp";
	}

}

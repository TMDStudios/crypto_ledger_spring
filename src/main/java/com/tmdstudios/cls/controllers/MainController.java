package com.tmdstudios.cls.controllers;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMethod;

import com.tmdstudios.cls.models.Coin;
import com.tmdstudios.cls.models.LoginUser;
import com.tmdstudios.cls.models.OwnedCoin;
import com.tmdstudios.cls.models.User;
import com.tmdstudios.cls.services.CoinService;
import com.tmdstudios.cls.services.OwnedCoinService;
import com.tmdstudios.cls.services.UserService;

@Controller
public class MainController {
	
	private boolean showWatchlist = false;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CoinService coinService;
	
	@Autowired
	private OwnedCoinService ownedCoinService;
	
	@GetMapping("/")
	public String index(Model model) {
	    return "index.jsp";
	}
	
	@GetMapping("/login")
	public String authLogin(Model model) {
	    model.addAttribute("newUser", new User());
	    model.addAttribute("newLogin", new LoginUser());
	    return "login.jsp";
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
	 
	    return "redirect:/home";
	}
	
	@GetMapping("/register")
	public String authRegister(Model model) {
	    model.addAttribute("newUser", new User());
	    model.addAttribute("newLogin", new LoginUser());
	    return "register.jsp";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("newUser") User newUser, 
			BindingResult result, Model model, HttpSession session) {

	    User user = userService.register(newUser, result);
	     
	    if(result.hasErrors()) {
	        model.addAttribute("newLogin", new LoginUser());
	        return "register.jsp";
	    }

	    session.setAttribute("user", user);
	 
	    return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		showWatchlist = false;
		session.setAttribute("showWatchlist", showWatchlist);
		session.setAttribute("user", null); 
	    return "redirect:/";
	}
	
	@GetMapping("/prices")
	public String viewPrices(HttpSession session, Model model) {
	 
		User user = (User) session.getAttribute("user");
		if(user==null) {
//			return "redirect:/logout";
		}else {
			User thisUser = userService.findById(user.getId());
			model.addAttribute("myCoins", thisUser.getCoins());
		}
		
		if(showWatchlist) {
			User thisUser = userService.findById(user.getId());
			model.addAttribute("coins", thisUser.getCoins());
		}else {
			model.addAttribute("coins", coinService.topCoins());
		}
		
		session.setAttribute("starOutline", "https://tmdstudios.files.wordpress.com/2022/03/goldstaroutline-1.png");
		session.setAttribute("starFull", "https://tmdstudios.files.wordpress.com/2022/03/goldstar.png");
		session.setAttribute("upArrow", "https://tmdstudios.files.wordpress.com/2022/03/uparrow-1.png");
		session.setAttribute("downArrow", "https://tmdstudios.files.wordpress.com/2022/03/downarrow-1.png");
		session.setAttribute("showWatchlist", showWatchlist);
		 
		return "view_prices.jsp";
	}
	
	@RequestMapping("/watchlist")
	public String watchlist(HttpSession session, Model model) {
		
		showWatchlist = !showWatchlist;
		session.setAttribute("showWatchlist", showWatchlist);
		 
		return "redirect:/prices";
	}
	
	@RequestMapping("/coins/watch/{id}")
	public String watchCoin(HttpSession session, @PathVariable("id") Long id) {
	 
		User user = (User) session.getAttribute("user");
		if(user==null) {
			return "redirect:/logout";
		}
		
		Coin coin = coinService.findById(id);
		User thisUser = userService.findById(user.getId());
		boolean coinFound = false;
		for(Coin userCoin:thisUser.getCoins()) {
			if(userCoin.getSymbol()==coin.getSymbol()) {
				coinFound=true;
				thisUser.getCoins().remove(thisUser.getCoins().indexOf(userCoin));
				break;
			}
		}
		if(!coinFound) {
			thisUser.getCoins().add(coin);
		}
		userService.updateUser(thisUser);
//		boolean userFound = false;
//		for(User coinUser:coin.getUsers()) {
//			if(coinUser.getId()==user.getId()) {
//				userFound = true;
//				break;
//			}
//		}
//		User thisUser = userService.findById(user.getId());
//		if(userFound) {
//			coin.getUsers().remove(thisUser);
//		}else {
//			coin.getUsers().add(thisUser);
//		}
//		coinService.updateCoin(coin);
		 
		return "redirect:/prices";
	}
	
	@RequestMapping(value="/coins/{id}", method = { RequestMethod.GET, RequestMethod.POST })
	public String coinDetails(HttpSession session, Model model, @PathVariable("id") Long id) {
		model.addAttribute("coin", coinService.findById(id));
		 
		return "coin_details.jsp";
	}
	
	@RequestMapping(value="/home", method = { RequestMethod.GET, RequestMethod.POST })
	public String home(HttpSession session, Model model) {
		Double overallTotal = 0.0;
		Double currentProfit = 0.0;
		Double overallProfit = 0.0;
	 
		User user = (User) session.getAttribute("user");
		if(user==null) {
			return "redirect:/logout";
		}else {
			User thisUser = userService.findById(user.getId());
			List<OwnedCoin> ownedCoins = ownedCoinService.findByOwnerDesc(thisUser);
			model.addAttribute("ownedCoins", ownedCoins);
			
			for(OwnedCoin ownedCoin:ownedCoins) {
				if(!ownedCoin.getSold()&&!ownedCoin.getMerged()) {
					overallTotal += ownedCoin.getTotalValue();
					currentProfit += ownedCoin.getTotalProfit();
				}
				if(ownedCoin.getSold()) {
					overallProfit += ownedCoin.getGain();
				}
				try {
					ownedCoin.setCoinRef(coinService.findBySymbol(ownedCoin.getSymbol()).getId()); // fixes coinRef issue?
					ownedCoin.setCurrentPrice(coinService.findBySymbol(ownedCoin.getSymbol()).getPrice());
					ownedCoin.setPriceDifference(coinService.findBySymbol(ownedCoin.getSymbol()).getPrice()/ownedCoin.getPurchasePrice()*100-100);
					ownedCoin.setTotalProfit(ownedCoin.getCurrentPrice()*ownedCoin.getTotalAmount()-ownedCoin.getPurchasePrice()*ownedCoin.getTotalAmount());
					ownedCoinService.updateOwnedCoin(ownedCoin);
				}catch(NullPointerException e){
					System.out.println("Coin not found: "+ownedCoin.getSymbol());
				}
			}
		}
		
		model.addAttribute("overallTotal", overallTotal);
		model.addAttribute("currentProfit", currentProfit);
		model.addAttribute("overallProfit", overallProfit);
		 
		return "home.jsp";
	}

}

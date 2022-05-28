package com.tmdstudios.cls.controllers;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.tmdstudios.cls.models.Coin;
import com.tmdstudios.cls.models.LoginUser;
import com.tmdstudios.cls.models.OwnedCoin;
import com.tmdstudios.cls.models.Settings;
import com.tmdstudios.cls.models.User;
import com.tmdstudios.cls.services.CoinService;
import com.tmdstudios.cls.services.OwnedCoinService;
import com.tmdstudios.cls.services.SettingsService;
import com.tmdstudios.cls.services.UserService;

@Controller
public class MainController {
	
	private boolean showWatchlist = false;
	private boolean darkMode = true;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SettingsService settingsService;
	
	@Autowired
	private CoinService coinService;
	
	@Autowired
	private OwnedCoinService ownedCoinService;
	
	@GetMapping("/")
	public String index(Model model, HttpSession session) {
		session.setAttribute("darkMode", darkMode);
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
	     
	    session.setAttribute("userId", user.getId());
	 
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

	    session.setAttribute("userId", user.getId());
	 
	    return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		showWatchlist = false;
		session.setAttribute("showWatchlist", showWatchlist);
		session.setAttribute("userId", null); 
	    return "redirect:/";
	}
	
	@GetMapping("/prices")
	public String viewPrices(HttpSession session, Model model) {
	 
		if(session.getAttribute("userId") == null) {
//			return "redirect:/logout";
		}else {
			Long userId = (Long) session.getAttribute("userId");		
			User user = userService.findById(userId);
			model.addAttribute("myCoins", user.getCoins());
		}
		
		if(showWatchlist) {
			Long userId = (Long) session.getAttribute("userId");
			User user = userService.findById(userId);
			Long newRank = 1l;
			for(Coin coin:coinService.userCoins(user)) {
				coin.setCoinRank(newRank);
				newRank++;
			}
			model.addAttribute("coins", coinService.userCoins(user));
		}else {
			// Workaround for coin rank issue
			Long newRank = 1l;
			for(Coin coin:coinService.topCoins()) {
				coin.setCoinRank(newRank);
				newRank++;
				coinService.updateCoin(coin);
			}
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
		
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}else {
			Long userId = (Long) session.getAttribute("userId");		
			User user = userService.findById(userId);
			List<OwnedCoin> ownedCoins = ownedCoinService.findByOwnerDesc(user);
			ArrayList<OwnedCoin> activeCoins = new ArrayList<>();
			ArrayList<OwnedCoin> inactiveCoins = new ArrayList<>();
			
			for(OwnedCoin ownedCoin:ownedCoins) {
				if(!ownedCoin.getSold()&&!ownedCoin.getMerged()) {
					activeCoins.add(ownedCoin);
					overallTotal += ownedCoin.getTotalValue();
					currentProfit += ownedCoin.getTotalProfit();
				}else if(ownedCoin.getSold()) {
					inactiveCoins.add(ownedCoin);
					overallProfit += ownedCoin.getGain();
				}else {
					inactiveCoins.add(ownedCoin);
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
			
			model.addAttribute("activeCoins", activeCoins);
			model.addAttribute("inactiveCoins", inactiveCoins);
		}
		
		model.addAttribute("overallTotal", overallTotal);
		model.addAttribute("currentProfit", currentProfit);
		model.addAttribute("overallProfit", overallProfit);
		 
		return "home.jsp";
	}
	
	@GetMapping("/settings")
	public String settings(HttpSession session, Model model) {
		Long userId = (Long) session.getAttribute("userId");		
		User user = userService.findById(userId);
		model.addAttribute("user", user);
		return "settings.jsp";
	}
	
	@PostMapping("/settings")
	public String updateSettings(
			HttpSession session, 
			Model model,
			@RequestParam(value="darkMode", required=false) Boolean formDarkMode
			) {
		
		Long userId = (Long) session.getAttribute("userId");		
		User user = userService.findById(userId);
		
		Settings settings = settingsService.findByUser(user);
		
		// NEEDS BETTER SOLUTION
		
		settings.setDarkMode(formDarkMode==null ? false : true);
		settingsService.updateSettings(settings);
		
		session.setAttribute("darkMode", settings.getDarkMode());
		 
		return "redirect:/settings";
	}

}

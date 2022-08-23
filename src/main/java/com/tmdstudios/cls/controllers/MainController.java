package com.tmdstudios.cls.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	@Value("${NOMICS_API}")
	private String nomicsApi;
	
	private boolean showWatchlist = false;
	private boolean darkMode = true;
	private int sortBy = 0;
	private boolean ascending = true;
	private String searchTerm = "";
	
	private String[] banners = {
			"https://tmdstudios.files.wordpress.com/2021/02/plclogolight.png?h=120",
			"https://tmdstudios.files.wordpress.com/2021/11/clbanner-1.png?h=120",
			"https://tmdstudios.files.wordpress.com/2022/03/tmdlogowide.png?h=120",
			"https://tmdstudios.files.wordpress.com/2022/03/nfts.png?h=120",
			"https://tmdstudios.files.wordpress.com/2021/04/galagames.png?h=120",
			"https://tmdstudios.files.wordpress.com/2019/02/bitcoinbanner.png?h=120"
			};
	
	private String[] links = {
			"https://play.google.com/store/apps/details?id=com.tmdstudios.python",
			"https://play.google.com/store/apps/details?id=com.tmdstudios.cryptoledgerkotlin",
			"https://tmdstudios.wordpress.com",
			"https://tmdstudios.wordpress.com/nfts/",
			"https://tmdstudios.wordpress.com/2021/04/06/gala-games/",
			"https://freebitco.in/?r=15749838"
			};
	
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
		session.setAttribute("nomicsApi", nomicsApi);
	    return "index.jsp";
	}
	
	@GetMapping("/login")
	public String authLogin(HttpSession session, Model model) {
	    model.addAttribute("newUser", new User());
	    model.addAttribute("newLogin", new LoginUser());
	    
	    setBanner(session);
		
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
	public String authRegister(HttpSession session, Model model) {
	    model.addAttribute("newUser", new User());
	    model.addAttribute("newLogin", new LoginUser());
	    
	    setBanner(session);
	    
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
	
	@GetMapping("/reset-password/{token}")
	public String resetPassword(@PathVariable("token") String token, Model model) {
	    model.addAttribute("token", token);
	    return "password_reset.jsp";
	}
	
	@PostMapping("/reset-password/{token}")
	public String updatePassword(
			RedirectAttributes redirectAttributes,
			@RequestParam(value = "pw") String pw,
			@RequestParam(value = "pwConfirm") String pwConfirm) {  
		if(pw.equals(pwConfirm)&&pw.length()>=8) {
			redirectAttributes.addFlashAttribute("message", "Your password has been reset!");
		}
	    return "redirect:/reset-password/{token}";
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
		
		session.setAttribute("nomicsApi", nomicsApi);

		if(session.getAttribute("userId") != null) {
			Long userId = (Long) session.getAttribute("userId");		
			User user = userService.findById(userId);
			model.addAttribute("myCoins", user.getCoins());
		}
		
		List<Coin> coinData = coinService.getCoinData();
		
		if(showWatchlist) {
			Long userId = (Long) session.getAttribute("userId");
			User user = userService.findById(userId);
			ArrayList<Coin> userCoins = new ArrayList<Coin>();
			
			for(Coin coin : coinData) {
				if(user.getCoins().contains(coin.getSymbol())) {
					userCoins.add(coin);
				}
			}
			
			if(sortBy==1) {
				ascending = !ascending;
				if(ascending) {
					userCoins.sort(Comparator.comparing(Coin::getCoinRank));
				}else {
					userCoins.sort(Comparator.comparing(Coin::getCoinRank).reversed());
				}
			}else if(sortBy==2) {
				ascending = !ascending;
				if(ascending) {
					userCoins.sort(Comparator.comparing(Coin::getName));
				}else {
					userCoins.sort(Comparator.comparing(Coin::getName).reversed());
				}
			}else if(sortBy==3) {
				ascending = !ascending;
				if(ascending) {
					userCoins.sort(Comparator.comparing(Coin::getPrice));
				}else {
					userCoins.sort(Comparator.comparing(Coin::getPrice).reversed());
				}
			}else if(sortBy==4) {
				ascending = !ascending;
				if(ascending) {
					userCoins.sort(Comparator.comparing(Coin::getPriceChangePercentage1d));
				}else {
					userCoins.sort(Comparator.comparing(Coin::getPriceChangePercentage1d).reversed());
				}
			}else if(sortBy==5) {
				ascending = !ascending;
				if(ascending) {
					userCoins.sort(Comparator.comparing(Coin::getPriceChangePercentage7d));
				}else {
					userCoins.sort(Comparator.comparing(Coin::getPriceChangePercentage7d).reversed());
				}
			}else if(sortBy==6) {
				ascending = !ascending;
				if(ascending) {
					userCoins.sort(Comparator.comparing(Coin::getPriceChangePercentage30d));
				}else {
					userCoins.sort(Comparator.comparing(Coin::getPriceChangePercentage30d).reversed());
				}
			}else if(sortBy==7) {
				ArrayList<Coin> filteredCoinData = new ArrayList<Coin>();
				for(Coin coin : userCoins) {
					if(coin.getName().toLowerCase().contains(searchTerm.toLowerCase())||coin.getSymbol().toLowerCase().contains(searchTerm.toLowerCase())) {
						filteredCoinData.add(coin);
					}
					userCoins = filteredCoinData;
				}
			}
			model.addAttribute("coins", userCoins);
		}else {
			if(sortBy==1) {
				ascending = !ascending;
				if(ascending) {
					coinData.sort(Comparator.comparing(Coin::getCoinRank));
				}else {
					coinData.sort(Comparator.comparing(Coin::getCoinRank).reversed());
				}
			}else if(sortBy==2) {
				ascending = !ascending;
				if(ascending) {
					coinData.sort(Comparator.comparing(Coin::getName));
				}else {
					coinData.sort(Comparator.comparing(Coin::getName).reversed());
				}
			}else if(sortBy==3) {
				ascending = !ascending;
				if(ascending) {
					coinData.sort(Comparator.comparing(Coin::getPrice));
				}else {
					coinData.sort(Comparator.comparing(Coin::getPrice).reversed());
				}
			}else if(sortBy==4) {
				ascending = !ascending;
				if(ascending) {
					coinData.sort(Comparator.comparing(Coin::getPriceChangePercentage1d));
				}else {
					coinData.sort(Comparator.comparing(Coin::getPriceChangePercentage1d).reversed());
				}
			}else if(sortBy==5) {
				ascending = !ascending;
				if(ascending) {
					coinData.sort(Comparator.comparing(Coin::getPriceChangePercentage7d));
				}else {
					coinData.sort(Comparator.comparing(Coin::getPriceChangePercentage7d).reversed());
				}
			}else if(sortBy==6) {
				ascending = !ascending;
				if(ascending) {
					coinData.sort(Comparator.comparing(Coin::getPriceChangePercentage30d));
				}else {
					coinData.sort(Comparator.comparing(Coin::getPriceChangePercentage30d).reversed());
				}
			}else if(sortBy==7) {
				ArrayList<Coin> filteredCoinData = new ArrayList<Coin>();
				for(Coin coin : coinData) {
					if(coin.getName().toLowerCase().contains(searchTerm.toLowerCase())||coin.getSymbol().toLowerCase().contains(searchTerm.toLowerCase())) {
						filteredCoinData.add(coin);
					}
					coinData = filteredCoinData;
				}
			}
			model.addAttribute("coins", coinData);
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
		
		sortBy = 0;
		searchTerm="";
		showWatchlist = !showWatchlist;
		session.setAttribute("showWatchlist", showWatchlist);
		 
		return "redirect:/prices";
	}
	
	@RequestMapping("/sort/{sortType}")
	public String sort(@PathVariable("sortType") Integer sortType) {
		//0 - Default, 1 - Rank, 2 - Name, 3 - Price, 4 - Price change 1d, 5 - Price change 7d, 6 - Price change 30d

		sortBy = sortType;
		 
		return "redirect:/prices";
	}
	
	@RequestMapping("/search")
	public String search(@RequestParam(value = "searchTerm", defaultValue = "") String searchTerm) {
		//7 - Search for coin

		sortBy = 7;
		this.searchTerm = searchTerm;
		 
		return "redirect:/prices";
	}
	
	@RequestMapping("/watchlist/search")
	public String watchlistSearch(@RequestParam(value = "searchTerm", defaultValue = "") String searchTerm) {
		//8 - Search for coin

		sortBy = 7;
		this.searchTerm = searchTerm;
		 
		return "redirect:/prices";
	}
	
	@RequestMapping(value="/coins/{symbol}", method = { RequestMethod.GET, RequestMethod.POST })
	public String coinDetails(HttpSession session, Model model, @PathVariable("symbol") String symbol) {
		if(session.getAttribute("userId") != null) {
			Long userId = (Long) session.getAttribute("userId");		
			User user = userService.findById(userId);
			model.addAttribute("apiKey", user.getApiKey()); 
		}
		
		model.addAttribute("coin", coinService.findBySymbol(symbol));		
		
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
					ownedCoin.setCurrentPrice(coinService.findBySymbol(ownedCoin.getSymbol()).getPrice());
					ownedCoin.setPriceDifference(coinService.findBySymbol(ownedCoin.getSymbol()).getPrice()/ownedCoin.getPurchasePrice()*100-100);
					ownedCoin.setTotalProfit(ownedCoin.getCurrentPrice()*ownedCoin.getTotalAmount()-ownedCoin.getPurchasePrice()*ownedCoin.getTotalAmount());
					activeCoins.add(ownedCoin);
					overallTotal += ownedCoin.getTotalValue();
					currentProfit += ownedCoin.getTotalProfit();
				}else if(ownedCoin.getSold()) {
					inactiveCoins.add(ownedCoin);
					overallProfit += ownedCoin.getGain();
				}else {
					inactiveCoins.add(ownedCoin);
				}
			}
			
			model.addAttribute("apiKey", user.getApiKey()); 
			model.addAttribute("activeCoins", activeCoins);
//			model.addAttribute("inactiveCoins", inactiveCoins);
			model.addAttribute("inactiveCoins", ownedCoinService.customHistory(user, user.getSettings().getHistoryLength()));
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
			@RequestParam(value="darkMode", required=false) Boolean formDarkMode,
			@RequestParam(value="historyLength", required=false) Integer historyLength
			) {
		
		Long userId = (Long) session.getAttribute("userId");		
		User user = userService.findById(userId);
		
		Settings settings = settingsService.findByUser(user);
		
		settings.setHistoryLength(historyLength);
		
		// STILL NEEDS BETTER SOLUTION?
		settings.setDarkMode(formDarkMode==null ? false : true);
		settingsService.updateSettings(settings);
		
		session.setAttribute("darkMode", settings.getDarkMode());
		 
		return "redirect:/settings";
	}
	
	@GetMapping("/api/docs")
	public String apiDocs(Model model) {
	    return "docs.jsp";
	}
	
	@PostMapping("/api/docs")
	public String getApiToken(RedirectAttributes redirectAttributes, HttpSession session) {  
		if(session.getAttribute("userId") == null) {
			return "redirect:/logout";
		}
			
		Long userId = (Long) session.getAttribute("userId");		
		User user = userService.findById(userId);
		
		String apiKey = "";
		
		if(user.getApiKey()!=null) {
			apiKey = user.getApiKey();
		}else {
			apiKey = userService.generateApiKey(user);
		}
		
		redirectAttributes.addFlashAttribute("api_key", apiKey);
	    return "redirect:/api/docs";
	}
	
	private void setBanner(HttpSession session) {
		int indexVal = new Random().nextInt(banners.length);
		session.setAttribute("banner", banners[indexVal]);
		session.setAttribute("link", links[indexVal]);
	}

}
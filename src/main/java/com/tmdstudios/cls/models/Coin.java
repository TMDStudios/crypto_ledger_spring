package com.tmdstudios.cls.models;

public class Coin implements Comparable<Coin> {
    private String name;
    private String symbol;
    private String logo;
    private Double price;
    private Long coinRank;
    private Double priceChangePercentage1d;
    private Double priceChangePercentage7d;
    private Double priceChangePercentage30d;
    
    public Coin() {}
    
    public Coin(
    		String name, 
    		String symbol, 
    		String logo, 
    		Double price, 
    		Long rank,
    		Double priceChangePercentage1d,
    		Double priceChangePercentage7d,
    		Double priceChangePercentage30d) {
    	this.name = name;
    	this.symbol = symbol;
    	this.logo = logo;
    	this.price = price;
    	this.coinRank = rank;
    	this.priceChangePercentage1d = priceChangePercentage1d;
    	this.priceChangePercentage7d = priceChangePercentage7d;
    	this.priceChangePercentage30d = priceChangePercentage30d;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getCoinRank() {
		return coinRank;
	}

	public void setCoinRank(Long coinRank) {
		this.coinRank = coinRank;
	}

	public Double getPriceChangePercentage1d() {
		return priceChangePercentage1d;
	}

	public void setPriceChangePercentage1d(Double priceChangePercentage1d) {
		this.priceChangePercentage1d = priceChangePercentage1d;
	}

	public Double getPriceChangePercentage7d() {
		return priceChangePercentage7d;
	}

	public void setPriceChangePercentage7d(Double priceChangePercentage7d) {
		this.priceChangePercentage7d = priceChangePercentage7d;
	}

	public Double getPriceChangePercentage30d() {
		return priceChangePercentage30d;
	}

	public void setPriceChangePercentage30d(Double priceChangePercentage30d) {
		this.priceChangePercentage30d = priceChangePercentage30d;
	}
	
	@Override
	public int compareTo(Coin c) {
		if(coinRank==null||c.coinRank==null) {
			return 0;
		}
		return coinRank.compareTo(c.coinRank);
	}

}
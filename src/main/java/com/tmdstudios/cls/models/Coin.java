package com.tmdstudios.cls.models;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="coins")
public class Coin {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String symbol;
    
    private String logo;
    private Double price;
    private Long coinRank;
    private Double priceChangePercentage1d;
    private Double priceChangePercentage7d;
    private Double priceChangePercentage30d;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
    
    @ManyToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinTable(
			name = "users_coins",
			joinColumns = @JoinColumn(name = "coin_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
	)
    List<User> users;
    
//    @Column(updatable=false)
//    @OneToMany(mappedBy="coin", fetch = FetchType.LAZY)
//    private List<Comment> comments;
    
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
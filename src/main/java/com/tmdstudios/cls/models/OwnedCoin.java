package com.tmdstudios.cls.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="owned_coins")
public class OwnedCoin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
    private String symbol;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dateBought;
    @PrePersist
	protected void onCreate() {
		this.dateBought = new Date();
	}
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dateSold;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updatedAt;
    private Double sellPrice = 0.0;
    private Double sellAmount = 0.0;
    private Double totalSpent = 0.0;
    private Boolean merged = false;
    private Boolean sold = false;
    private Double gain = 0.0;
    private Double amount = 0.0;
    private Double totalAmount = 0.0;
    private Double value = 0.0;
    private Double totalValue = 0.0;
    private Double customPrice = 0.0;
    private Double purchasePrice = 0.0;
    private Double currentPrice = 0.0;
    private Double priceDifference = 0.0;
    private Double totalProfit = 0.0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name="user_owned_coin_id")
    private User owner;
    
    public OwnedCoin() {}
    
    public OwnedCoin(String name, String symbol, Double amount, Double purchasePrice) {
    	this.name = name;
    	this.symbol = symbol;
    	this.amount = amount;
    	this.purchasePrice = purchasePrice;
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

	public Date getDateBought() {
		return dateBought;
	}

	public void setDateBought(Date dateBought) {
		this.dateBought = dateBought;
	}

	public Date getDateSold() {
		return dateSold;
	}

	public void setDateSold(Date dateSold) {
		this.dateSold = dateSold;
	}

	public Double getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Double getSellAmount() {
		return sellAmount;
	}

	public void setSellAmount(Double sellAmount) {
		this.sellAmount = sellAmount;
	}

	public Double getTotalSpent() {
		return totalSpent;
	}

	public void setTotalSpent(Double totalSpent) {
		this.totalSpent = totalSpent;
	}

	public Boolean getMerged() {
		return merged;
	}

	public void setMerged(Boolean merged) {
		this.merged = merged;
	}

	public Boolean getSold() {
		return sold;
	}

	public void setSold(Boolean sold) {
		this.sold = sold;
	}

	public Double getGain() {
		return gain;
	}

	public void setGain(Double gain) {
		this.gain = gain;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Double getCustomPrice() {
		return customPrice;
	}

	public void setCustomPrice(Double customPrice) {
		this.customPrice = customPrice;
	}

	public Double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public Double getPriceDifference() {
		return priceDifference;
	}

	public void setPriceDifference(Double priceDifference) {
		this.priceDifference = priceDifference;
	}

	public Double getTotalProfit() {
		return totalProfit;
	}

	public void setTotalProfit(Double totalProfit) {
		this.totalProfit = totalProfit;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
    
}

package com.jpmorgan.supersimplestocks.model;

import java.time.Instant;

public class Trade {

	private long Id;

	private int quantity;

	private double tradePrice;

	private TradeIndicator type;

	private Instant timestamp;
	
	private long stockId;
	
	public Trade(long stockId, int quantity, double tradePrice, TradeIndicator type, long id, Instant timestamp) {
		this.stockId = stockId;
		this.quantity = quantity;
		this.tradePrice = tradePrice;
		this.type = type;
		this.timestamp = timestamp;
		this.Id = id;
	}
	
	public long getStockId() {
		return stockId;
	}

	public long getId() {
		return Id;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getTradePrice() {
		return tradePrice;
	}

	public TradeIndicator getType() {
		return type;
	}

	public Instant getTimestamp() {
		return timestamp;
	}
	
	@Override
	public String toString() {
		return "\nTimestamp->"+this.timestamp+":StockId->"+this.stockId+":Quantity->"+this.quantity+":Indicator->"+this.type.name();
	}

}

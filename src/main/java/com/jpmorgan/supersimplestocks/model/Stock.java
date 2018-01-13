package com.jpmorgan.supersimplestocks.model;

/**
 * 
 * @author bhaskar.agarwal
 *
 */
public class Stock {

	private String symbol;

	private StockType type;

	private float lastDividend;

	private float fixedDividend;

	private float parValue;

	private long id;
	
	private double lastMarketPrice;

	public Stock(String symbol, StockType type, float lastDividend, int fixedDividend, float parValue, long id) {
		this.symbol = symbol;
		this.type = type;
		this.lastDividend = lastDividend;
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;
		this.id = id;
		this.lastMarketPrice = parValue;
	}

	public long getId() {
		return id;
	}

	public String getSymbol() {
		return symbol;
	}

	public StockType getType() {
		return type;
	}

	public float getParValue() {
		return parValue;
	}

	public float getFixedDividend() {
		return fixedDividend;
	}

	public float getLastDividend() {
		return lastDividend;
	}

	public double getLastMarketPrice() {
		return lastMarketPrice;
	}

	public void setLastMarketPrice(double lastMarketPrice) {
		this.lastMarketPrice = lastMarketPrice;
	}

	@Override
	public String toString() {
		return "Stock [symbol=" + symbol + ", type=" + type + ", lastDividend=" + lastDividend + ", fixedDividend="
				+ fixedDividend + ", parValue=" + parValue + "]";
	}

}

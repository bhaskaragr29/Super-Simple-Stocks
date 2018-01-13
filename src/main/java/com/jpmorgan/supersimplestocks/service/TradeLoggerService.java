package com.jpmorgan.supersimplestocks.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.jpmorgan.supersimplestocks.model.Trade;
import com.jpmorgan.supersimplestocks.model.TradeIndicator;

public class TradeLoggerService {
	
	public static List<Trade> tradeList = new ArrayList<Trade>();
	
	private static AtomicLong tradeId = new AtomicLong();
	
	/**
	 * Record Table
	 * @param quantity
	 * @param marketPrice
	 * @param type
	 * @param stockId
	 */
	public static void recordTrade(int quantity, double marketPrice, TradeIndicator type, long stockId) {
		tradeList.add(new Trade(stockId, quantity, marketPrice, type, tradeId.incrementAndGet(), Instant.now()));	
	}
	
	/**
	 * Static logger function 
	 * @return Trade List
	 */
	public static List<Trade> getList() {
		return tradeList;
	}
}

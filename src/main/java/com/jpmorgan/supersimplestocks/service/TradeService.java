package com.jpmorgan.supersimplestocks.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import com.jpmorgan.supersimplestocks.model.Trade;

public class TradeService {
	
	/**
	 * Get Trade By StockId and Time Difference
	 * @param trades
	 * @param stockId
	 * @param seconds
	 * @return
	 */
	public List<Trade> getTradeByStockITimeDifference(List<Trade> trades, long stockId, int seconds) {
		
		final Instant instant = Instant.now();
		
		return trades.stream()
				.filter(
				item -> 
				item.getStockId() == stockId && 
						instant.toEpochMilli() - item.getTimestamp().toEpochMilli() <= seconds*1000
			)
				.collect(Collectors.toList());
		
	}
	
	/**
	 * Get Trade by Stock Id Only
	 * @param trades
	 * @param stockId
	 * @return
	 */
	public List<Trade> getTradebyStockId(List<Trade> trades, long stockId) {
		return trades.stream()
				.filter(
				item -> 
				item.getStockId() == stockId)
				.collect(Collectors.toList());
	}
}

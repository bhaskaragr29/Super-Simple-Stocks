package com.jpmorgan.supersimplestocks.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.jpmorgan.supersimplestocks.model.Stock;
import com.jpmorgan.supersimplestocks.model.Trade;

/**
 * Abstraction Service Layer for Super Simple Stocks
 * 
 * @author bhaskar.agarwal
 *
 */
public class SuperSimpeStocksService {

	private StockFormula stockFormula;
	
	private TradeService tradeService;

	private Map<String, Stock> beverageStocks;
	
	public SuperSimpeStocksService(Map<String, Stock> beverageStocks, StockFormula stockFormula, TradeService tradeService) {
		this.beverageStocks = beverageStocks;
		this.stockFormula = stockFormula;
		this.tradeService = tradeService;
		
	}
	
	/**
	 * 
	 * @param symbol
	 * @return
	 */
	public boolean isValidSymbol(String symbol) {
		return this.beverageStocks.containsKey(symbol);
	}
	
	/**
	 * 
	 * @param symbol
	 * @return
	 */
	public Stock getStockBySymbol(String symbol) {
		return this.beverageStocks.get(symbol);
	}
	
	/**
	 * getDividendYeild
	 * 
	 * @param marketPrice
	 * @param symbol - Stock Symbol
	 * @return
	 */
	public double getDividendYeild(double marketPrice, String symbol) {
		
		Stock stock = this.getStockBySymbol(symbol);
		
		return marketPrice <=0D || stock == null ? 0D:
			stockFormula.getRawDividendYeild(
						stock.getType(), 
						marketPrice, 
						stock.getLastDividend(), 
						stock.getFixedDividend(), 
						stock.getParValue()
					);
	}

	/**
	 * getPERatio
	 * 
	 * @param marketPrice
	 * @param symbol - Stock Symbol
	 * @return
	 */
	public double getPERatio(double marketPrice, String symbol) {
		
		Stock stock = this.getStockBySymbol(symbol);
		
		return stock.getLastDividend() > 0 ? stockFormula.getRawPERatio(marketPrice, stock.getLastDividend()) : 0D;
	}

	/**
	 * 
	 * @param trades - 
	 * @param stockId
	 * @return
	 */
	public double getGeometricMean(List<Trade> trades, long stockId) {
		List<Double> marketPrices = tradeService.getTradebyStockId(trades, stockId)
				.stream()
				.map(Trade::getTradePrice)
				.collect(Collectors.toList());
		
		return stockFormula.getRawGeometricMean(
				marketPrices.parallelStream().filter(item -> item.doubleValue() > 0D).collect(Collectors.toList()));
	}

	/**
	 * getVolumeWeightedStockPrice for Stock id
	 * @param trades
	 * @param seconds
	 * @param stockId
	 * @return
	 */
	public double getVolumeWeightedStockPrice(List<Trade> trades, int seconds, long stockId) {
		
		
		return stockFormula
				.getRawVolumeWeightedStockPrice(tradeService.getTradeByStockITimeDifference(trades, stockId, seconds));
	}

}

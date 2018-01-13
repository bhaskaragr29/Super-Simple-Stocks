package com.jpmorgan.supersimplestocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.HashMap;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.jpmorgan.supersimplestocks.model.Stock;
import com.jpmorgan.supersimplestocks.model.StockType;
import com.jpmorgan.supersimplestocks.model.TradeIndicator;
import com.jpmorgan.supersimplestocks.service.StockFormulaService;
import com.jpmorgan.supersimplestocks.service.SuperSimpeStocksService;
import com.jpmorgan.supersimplestocks.service.TradeLoggerService;
import com.jpmorgan.supersimplestocks.service.TradeService;

/**
 * Hello world!
 *
 */
public class StocksApplication {
	
	public final static String[] STOCK_SYMBOLS = { "TEA", "POP", "ALE", "GIN", "JOE" };
	public final static String[] TRADE_INDICATOR = {TradeIndicator.BUY.name(), TradeIndicator.SELL.name()};
	
	public static AtomicLong stockid = new AtomicLong();
	private static final int RECORD_TABLE_SECONDS = 15 * 60;
	
	public static SuperSimpeStocksService service;

	public static void main(String[] args) throws IOException {
		Map<String, Stock> beverageStocks = new HashMap<String, Stock>();

		beverageStocks.put(STOCK_SYMBOLS[0],
				new Stock(STOCK_SYMBOLS[0], StockType.COMMON, 0, 0, 100, stockid.incrementAndGet()));
		beverageStocks.put(STOCK_SYMBOLS[1],
				new Stock(STOCK_SYMBOLS[1], StockType.COMMON, 8, 0, 100, stockid.incrementAndGet()));
		beverageStocks.put(STOCK_SYMBOLS[2],
				new Stock(STOCK_SYMBOLS[2], StockType.COMMON, 23, 0, 60, stockid.incrementAndGet()));
		beverageStocks.put(STOCK_SYMBOLS[3],
				new Stock(STOCK_SYMBOLS[3], StockType.PREFERRED, 8, 2, 100, stockid.incrementAndGet()));
		beverageStocks.put(STOCK_SYMBOLS[4],
				new Stock(STOCK_SYMBOLS[4], StockType.COMMON, 13, 0, 250, stockid.incrementAndGet()));

		SuperSimpeStocksService service = new SuperSimpeStocksService(beverageStocks, new StockFormulaService(), new TradeService());

		process(service);
	}
	
	

	public static void process(SuperSimpeStocksService service) throws IOException {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			System.out.print("We have 5 stocks Available \"TEA\", \"POP\", \"ALE\", \"GIN\", \"JOE\"");
			String symbol = readStockSymbol(in, service);
			double marketPrice = readTradeMarketPrice(in);
			int num = readTradeQuantity(in);
			String indicator = readTradeIndicator(in);
			TradeIndicator tradeIndicator = TradeIndicator.valueOf(indicator);			
			
			Stock stock = service.getStockBySymbol(symbol);
			stock.setLastMarketPrice(marketPrice);
			long stockId = stock.getId();
			
			TradeLoggerService.recordTrade(num, marketPrice, tradeIndicator, stockId);
			
			
			BigDecimal peRatio = new BigDecimal(service.getPERatio(marketPrice, symbol)).setScale(3, RoundingMode.HALF_UP);
			System.out.print("\n PE Ratio value:"+ peRatio);
			
			BigDecimal diviYield = new BigDecimal(service.getDividendYeild(marketPrice, symbol)).setScale(3, RoundingMode.HALF_UP);
			System.out.print("\n Dividend Yield Value value:"+ diviYield);
			
			
			BigDecimal VWSP = new BigDecimal(service.getVolumeWeightedStockPrice(TradeLoggerService.getList(), RECORD_TABLE_SECONDS, stockId)).setScale(3, RoundingMode.HALF_UP);
			System.out.print("\n Volume Weighted Price for last 15 min:"+VWSP);
		
			BigDecimal GM = new BigDecimal(service.getGeometricMean(TradeLoggerService.getList(), stockId)).setScale(3, RoundingMode.HALF_UP);
			System.out.print("\n Geometric Mean of "+symbol+":"+GM+"\n");
		}
	}
	
	public static String readStockSymbol(BufferedReader in, SuperSimpeStocksService service) throws IOException {
		System.out.print("\n Please type symbol above stocks:");
		
		String symbol = null;

		while (true) {
			symbol = in.readLine();
			if (service.isValidSymbol(symbol)) {
				break;
			} else {
				System.out.print("Wrong Symbol: Please enter valid Symbol\n");
			}
		}
		
		return symbol;
	}
	
	public static int readTradeQuantity(BufferedReader in) throws IOException {
		System.out.print("\n Enter Trade Quanity:");
		String quantity = null;
		String message = "Wrong Quanity: Please enter valid Quanity\n";
		int num = 0;
		while (true) {
			quantity = in.readLine();
			try {
				num = Integer.parseInt(quantity);
				if(num > 0) {
					break;
				} else {
					System.out.print(message);
				}
				// is an integer!
			} catch (NumberFormatException e) {
				// not an integer!
				
			}
		}
		return num;
	}
	
	public static double readTradeMarketPrice(BufferedReader in) throws IOException {
		System.out.print("\n Enter Market Price:");
		String message = "Wrong Quanity: Please enter valid Market Price\n";
		String mp = null;
		double marketPrice = 0;
		while (true) {
			mp = in.readLine();
			try {
				marketPrice = Double.parseDouble(mp);
				if(marketPrice > 0) {
					break;
				} else {
					System.out.print(message);
				}
			} catch (NumberFormatException e) {
				// not an integer!
				System.out.print(message);
			}
		}
		return marketPrice;
	}
	
	public static String readTradeIndicator(BufferedReader in) throws IOException {
		System.out.print("\n Please enter trade indicator(BUY, SELL):");
		
		String indicator = null;
		
		while (true) {
			indicator = in.readLine();
			if (Arrays.asList(TRADE_INDICATOR).contains(indicator)) {
				break;
			} else {
				System.out.print("Wrong Indicator: Please enter valid Indicator\n");
			}
		}
		
		return indicator;
	}

}

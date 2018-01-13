package com.jpmorgan.supersimplestocks.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.jpmorgan.supersimplestocks.model.Stock;
import com.jpmorgan.supersimplestocks.model.StockType;
import com.jpmorgan.supersimplestocks.model.Trade;
import com.jpmorgan.supersimplestocks.model.TradeIndicator;

public class AbstractData {

	public static AtomicLong stockid = new AtomicLong();

	public static AtomicLong tradeid = new AtomicLong();

	public final static String[] STOCK_SYMBOLS = { "TEA", "POP", "ALE", "GIN", "JOE" };

	public static List<Stock> stocks = new ArrayList<Stock>();

	public static List<Trade> trades = new ArrayList<Trade>();

	public static List<Stock> prepareStockData() {
		if (stocks.size() == 0) {
			stocks.add(new Stock(STOCK_SYMBOLS[0], StockType.COMMON, 12, 0, 100, stockid.incrementAndGet()));
			stocks.add(new Stock(STOCK_SYMBOLS[1], StockType.COMMON, 23, 0, 60, stockid.incrementAndGet()));
			stocks.add(new Stock(STOCK_SYMBOLS[2], StockType.PREFERRED, 8, 2, 101, stockid.incrementAndGet()));
			stocks.add(new Stock(STOCK_SYMBOLS[3], StockType.COMMON, 34, 1, 99, stockid.incrementAndGet()));
		}

		return stocks;
	}

	public static List<Trade> prepareTradeData() {
		prepareStockData();

		Instant instant = Instant.now();

		Instant instant2Delay = instant.minusMillis(2000);// 2 second delay
		Instant instant4Delay = instant.minusMillis(4000); // 4 second delay
		Instant instant15MinDelay = instant.minusMillis(15 * 60 * 1000);
		Instant instant30MinDelay = instant.minusMillis(30 * 60 * 1000);

		if (trades.size() == 0) {

			// 4 for TEA
			trades.add(new Trade(stocks.get(0).getId(), 10, 101.23, TradeIndicator.BUY, tradeid.incrementAndGet(),
					instant));
			trades.add(new Trade(stocks.get(0).getId(), 5, 102.23, TradeIndicator.BUY, tradeid.incrementAndGet(),
					instant2Delay));
			trades.add(new Trade(stocks.get(0).getId(), 11, 100.23, TradeIndicator.BUY, tradeid.incrementAndGet(),
					instant4Delay));
			trades.add(new Trade(stocks.get(0).getId(), 20, 130.23, TradeIndicator.SELL, tradeid.incrementAndGet(),
					instant15MinDelay));
			trades.add(new Trade(stocks.get(0).getId(), 1, 99.23, TradeIndicator.BUY, tradeid.incrementAndGet(),
					instant30MinDelay));

			// 4 for POP
			trades.add(new Trade(stocks.get(1).getId(), 10, 101.23, TradeIndicator.BUY, tradeid.incrementAndGet(),
					instant));
			trades.add(new Trade(stocks.get(1).getId(), 5, 102.23, TradeIndicator.BUY, tradeid.incrementAndGet(),
					instant2Delay));
			trades.add(new Trade(stocks.get(1).getId(), 11, 100.23, TradeIndicator.BUY, tradeid.incrementAndGet(),
					instant4Delay));
			trades.add(new Trade(stocks.get(1).getId(), 20, 130.23, TradeIndicator.SELL, tradeid.incrementAndGet(),
					instant15MinDelay));
			trades.add(new Trade(stocks.get(1).getId(), 1, 99.23, TradeIndicator.BUY, tradeid.incrementAndGet(),
					instant30MinDelay));

			// 3 for GIN
			trades.add(new Trade(stocks.get(3).getId(), 10, 101.23, TradeIndicator.BUY, tradeid.incrementAndGet(),
					instant));
			trades.add(new Trade(stocks.get(3).getId(), 5, 102.23, TradeIndicator.SELL, tradeid.incrementAndGet(),
					instant2Delay));
			trades.add(new Trade(stocks.get(3).getId(), 11, 100.23, TradeIndicator.BUY, tradeid.incrementAndGet(),
					instant4Delay));
			trades.add(new Trade(stocks.get(3).getId(), 20, 130.23, TradeIndicator.SELL, tradeid.incrementAndGet(),
					instant15MinDelay));
			trades.add(new Trade(stocks.get(3).getId(), 1, 99.23, TradeIndicator.BUY, tradeid.incrementAndGet(),
					instant30MinDelay));
		}

		return trades;
	}
}

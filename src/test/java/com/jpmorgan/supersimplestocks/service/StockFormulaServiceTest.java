package com.jpmorgan.supersimplestocks.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jpmorgan.supersimplestocks.model.Stock;
import com.jpmorgan.supersimplestocks.model.Trade;

public class StockFormulaServiceTest {

	public StockFormulaService service = new StockFormulaService();

	public static List<Stock> stocks = new ArrayList<Stock>();

	public static List<Trade> trades = new ArrayList<Trade>();

	public final double DELTA = 0.0001;

	@BeforeClass
	public static void prepareData() {
		stocks = AbstractData.prepareStockData();
		trades = AbstractData.prepareTradeData();
	}

	@Test
	public void getPERatioTest() {

		double actualResult = service.getRawPERatio(101.23, 23);

		assertEquals(101.23 / 23, actualResult, DELTA);

		double actualResult1 = service.getRawPERatio(1.23, 23);

		assertEquals(1.23 / 23, actualResult1, DELTA);

		double actualResultZeroDividend = service.getRawPERatio(101.23, 0);

		assertEquals(0, actualResultZeroDividend, DELTA);

	}

	@Test
	public void getRawDividendYeildCommonTest() {
		// For POP which last dividend is 23
		Stock stock = stocks.get(1);

		double marketPrice1 = 101.23;
		double actualResult = service.getRawDividendYeild(stock.getType(), 
				marketPrice1, 
				stock.getLastDividend(), 
				stock.getFixedDividend(), 
				stock.getParValue());
		assertEquals(stock.getLastDividend() / marketPrice1, actualResult, DELTA);

		double marketPrice2 = 0.0D;
		double actualResult2 = service.getRawDividendYeild(stock.getType(), 
				marketPrice2, 
				stock.getLastDividend(), 
				stock.getFixedDividend(), 
				stock.getParValue());
		assertEquals(0, actualResult2, DELTA);

	}

	@Test
	public void getRawDividendYeildPrefferedTest() {
		Stock stock = stocks.get(2);

		double marketPrice1 = 101.23;
		double actualResult = service.getRawDividendYeild(stock.getType(), 
				marketPrice1, 
				stock.getLastDividend(), 
				stock.getFixedDividend(), 
				stock.getParValue());
		double expectedResult = (stock.getParValue() * (stock.getFixedDividend() / 100.0)) / marketPrice1;
		assertEquals(expectedResult, actualResult, DELTA);

		double marketPrice2 = 0.0D;
		double actualResult2 = service.getRawDividendYeild(stock.getType(), 
				marketPrice2, 
				stock.getLastDividend(), 
				stock.getFixedDividend(), 
				stock.getParValue());
		assertEquals(0, actualResult2, DELTA);
	}

	@Test
	public void getRawGeometricMeanTest() {
		List<Double> marketPrices = Arrays.asList(101.23D, 23.23D, 1D, 10.23D);

		double gm = 1D;
		for (double mp : marketPrices) {
			gm *= mp;
		}
		double expectedGm = Math.pow(gm, 1D / (double) marketPrices.size());
		double actualResult = service.getRawGeometricMean(marketPrices);

		assertEquals(expectedGm, actualResult, DELTA);

		double actualResult1 = service.getRawGeometricMean((List<Double>) Collections.EMPTY_LIST);
		assertEquals(0D, actualResult1, DELTA);

	}

	@Test
	public void getRawVolumeWeightedStockPriceTest() {
		double actualResult = service.getRawVolumeWeightedStockPrice(trades);

		double tradePriceQuanity = 0D;
		int quantity = 0;
		Iterator<Trade> itr = trades.iterator();

		while (itr.hasNext()) {
			Trade trade = itr.next();
			tradePriceQuanity += (trade.getTradePrice() * trade.getQuantity());
			quantity += trade.getQuantity();
		}

		assertEquals(tradePriceQuanity / quantity, actualResult, DELTA);

		double actualResult1 = service.getRawVolumeWeightedStockPrice((List<Trade>) Collections.EMPTY_LIST);
		assertEquals(0D, actualResult1, DELTA);
	}

}

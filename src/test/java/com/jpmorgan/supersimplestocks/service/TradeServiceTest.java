package com.jpmorgan.supersimplestocks.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.jpmorgan.supersimplestocks.model.Stock;
import com.jpmorgan.supersimplestocks.model.Trade;

public class TradeServiceTest {

	public TradeService service = new TradeService();

	public static List<Trade> trades = new ArrayList<Trade>();

	public static List<Stock> stocks = new ArrayList<Stock>();

	@BeforeClass
	public static void prepareData() {
		stocks = AbstractData.prepareStockData();
		trades = AbstractData.prepareTradeData();
	}

	@Test
	public void getTradeByStock5MinTimeDifferenceTest() {

		List<Trade> filteredList = service.getTradeByStockITimeDifference(trades, stocks.get(0).getId(), 5 * 60);

		assertEquals(3, filteredList.size());

		assertEquals(filteredList.get(0), trades.get(0));
		assertEquals(filteredList.get(1), trades.get(1));
		assertEquals(filteredList.get(2), trades.get(2));
	}

	@Test
	public void getTradeByStock17MinTimeDifferenceTest() {
		List<Trade> filteredList = service.getTradeByStockITimeDifference(trades, stocks.get(0).getId(), 17 * 60);
		assertEquals(4, filteredList.size());
		assertEquals(filteredList.get(0), trades.get(0));
		assertEquals(filteredList.get(1), trades.get(1));
		assertEquals(filteredList.get(2), trades.get(2));
		assertEquals(filteredList.get(3), trades.get(3));
	}

	@Test
	public void getTradeByStock40MinTimeDifferenceTest() {
		List<Trade> filteredList = service.getTradeByStockITimeDifference(trades, stocks.get(0).getId(), 40 * 60);
		assertEquals(5, filteredList.size());
		assertEquals(filteredList.get(0), trades.get(0));
		assertEquals(filteredList.get(1), trades.get(1));
		assertEquals(filteredList.get(2), trades.get(2));
		assertEquals(filteredList.get(3), trades.get(3));
		assertEquals(filteredList.get(4), trades.get(4));
	}

}

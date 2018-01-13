package com.jpmorgan.supersimplestocks.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jpmorgan.supersimplestocks.model.Stock;
import com.jpmorgan.supersimplestocks.model.Trade;

public class SuperSimpleStockServiceTest {
	
	
	
	public static List<Trade> trades = new ArrayList<Trade>();

	public static List<Stock> stocks = new ArrayList<Stock>();
	
	public static Map<String, Stock> beverages= new HashMap<String, Stock>();
	
	SuperSimpeStocksService service;
	
	private TradeService tradeService;
	
	private StockFormula stockFormula;
	
	@BeforeClass
	public static void prepareData() {
		stocks = AbstractData.prepareStockData();
		trades = AbstractData.prepareTradeData();
		for(Stock s: stocks) {
			beverages.put(s.getSymbol(), s);
		}
	}
	
	@Before
	public void setUp() {
		tradeService = mock(TradeService.class);
		stockFormula = mock(StockFormula.class);
		service = new SuperSimpeStocksService(beverages, stockFormula, tradeService);
	}
	
	@After
    public void tearDown() {
		stockFormula = null;
		tradeService = null;
		service = null;       
    }
	
	@Test
	public void isValidSymbolTest() {
		String s = "TEA";
		
		assertTrue(service.isValidSymbol(s));
		
		assertTrue(!service.isValidSymbol("BACD"));
	}
	
	@Test
	public void getDividendYeildTest() {
		
		Stock stock = stocks.get(1);
		double marketPrice = 101.23;
		double expectedDY = 102.23;
		
		when(stockFormula.getRawDividendYeild(stock.getType(), 
				marketPrice, 
				stock.getLastDividend(), 
				stock.getFixedDividend(), 
				stock.getParValue())).thenReturn(expectedDY);
		
		double actualDY = service.getDividendYeild(marketPrice, stock.getSymbol());
		
		assertEquals(expectedDY, actualDY, 0.001);
	}
	
	@Test
	public void getPERatioTest() {
		
		Stock stock = stocks.get(1);
		double marketPrice = 101.23;
		double expectedDY = 102.23;
		
		when(stockFormula.getRawPERatio(marketPrice, stock.getLastDividend())).thenReturn(expectedDY);
		
		double actualDY = service.getPERatio(marketPrice, stock.getSymbol());
		
		assertEquals(expectedDY, actualDY, 0.001);
	}
	
	@Test
	public void getGeometricMeanTest() {
		
		Stock stock = stocks.get(1);//POP
		
		double expectedDY = 102.23;
		
		List<Double> marketPrices = new ArrayList<Double>();
		
		when(stockFormula.getRawGeometricMean(marketPrices)).thenReturn(expectedDY);
		
		double actualDY = service.getGeometricMean(trades, stock.getId());
		
		assertEquals(expectedDY, actualDY, 0.001);
	}
	
	
	@Test
	public void getVolumeWeightedStockPriceTest() {
		
		Stock stock = stocks.get(1);//POP
		
		double expectedDY = 102.23;

		when(tradeService.getTradeByStockITimeDifference(trades, stock.getId(), 1)).thenReturn(trades);
		when(stockFormula.getRawVolumeWeightedStockPrice(trades)).thenReturn(expectedDY);
		
		double actualDY = service.getVolumeWeightedStockPrice(trades, 1, stock.getId());
		
		assertEquals(expectedDY, actualDY, 0.001);
	}
	
	
	
	
	
}

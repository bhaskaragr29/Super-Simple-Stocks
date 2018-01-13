package com.jpmorgan.supersimplestocks.service;

import java.util.List;

import com.jpmorgan.supersimplestocks.model.StockType;
import com.jpmorgan.supersimplestocks.model.Trade;

public interface StockFormula{
	public double getRawDividendYeild(StockType type, double marketPrice, float lastDividend, float fixedDividend, float parValue);
	
	public double getRawPERatio(double marketPrice, float lastDividend);
	
	public double getRawGeometricMean(List<Double> marketPrices);
	
	public double getRawVolumeWeightedStockPrice(List<Trade> trades); 
}

package com.jpmorgan.supersimplestocks.service;

import java.util.Iterator;
import java.util.List;

import com.jpmorgan.supersimplestocks.model.StockType;
import com.jpmorgan.supersimplestocks.model.Trade;

/**
 * 
 * @author bhaskar.agarwal StockFormula Service
 */
public class StockFormulaService implements StockFormula {
	
	/**
	 * @param type - Stock Type
	 * @param marketPrice -  Market Price
	 * @param lastDividend - Last Dividend
	 * @param fixedDividend - Fixed Dividend
	 * @param parValue - Face Value
	 */
	@Override
	public double getRawDividendYeild(StockType type, double marketPrice, float lastDividend, float fixedDividend,
			float parValue) {
		double pe = 0D;

		if (marketPrice <= 0F)
			return 0;

		switch (type) {
		case PREFERRED:
			pe = (parValue * (fixedDividend / 100.0)) / marketPrice;
			break;
		default:

			pe = lastDividend / marketPrice;
			break;
		}

		return pe;
	}

	/**
	 * Formula to calculate PE Ratio
	 * 
	 * @param marketPrice
	 *            - Current Market Price
	 * @param lastDividend
	 *            - Last Dividend Value
	 * @return double RAWPE Ratio value
	 */
	@Override
	public double getRawPERatio(double marketPrice, float lastDividend) {
		if (lastDividend <= 0F || marketPrice <= 0F)
			return 0;

		return marketPrice / lastDividend;
	}

	/**
	 * Formula to calculate Geometric Mean
	 * 
	 * @param marketPrice
	 *            - list of all market Prices
	 * @return double Geometric Mean
	 */
	@Override
	public double getRawGeometricMean(List<Double> marketPrice) {

		if (marketPrice.size() <= 0 || marketPrice == null)
			return 0D;

		double gm = 1D;
		for (double mp : marketPrice) {
			gm *= mp;
		}
		return Math.pow(gm, 1D / marketPrice.size());
	}

	/**
	 * Formula to calculate VolumeWeightedStockPrice
	 * 
	 * @param trades
	 *            - list of all trade
	 * @return double VolumeWeightedStockPrice value
	 */
	@Override
	public double getRawVolumeWeightedStockPrice(List<Trade> trades) {

		if (trades.size() > 0) {
			double tradePriceQuanity = 0D;
			int quantity = 0;
			Iterator<Trade> itr = trades.iterator();

			while (itr.hasNext()) {
				Trade trade = itr.next();
				tradePriceQuanity += (trade.getTradePrice() * trade.getQuantity());
				quantity += trade.getQuantity();
			}

			return tradePriceQuanity / quantity;
		} else {
			return 0D;
		}

	}

}

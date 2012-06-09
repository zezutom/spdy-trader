package org.zezutom.spdytrader.impl;

import java.math.BigDecimal;
import java.util.Random;

import org.zezutom.spdytrader.MarketDAO;

public class RandomMarketDAO implements MarketDAO {

	public BigDecimal getPrice(String asset) {
		return getRandom(asset);
	}

	public String getMostTraded(String...portfolio) {
		
		if (portfolio == null) {
			return null;
		}
		
		return portfolio[new Random().nextInt(portfolio.length - 1)];
	}

	private BigDecimal getRandom(String asset) {
		
		// Asset name length determines the calculation
		final int length = asset.length();
		BigDecimal max = new BigDecimal(length + "." + new Random().nextInt(length));
		
		// A base: any number between 100 and 500
		double range = (Math.random() * 500) + 100;				
		
		// Transform the base into a BigDecimal 
		BigDecimal randFromRange = new BigDecimal(range);
		
		// Calculate the result
        BigDecimal actualRandom = randFromRange.divide(max,BigDecimal.ROUND_DOWN);
        
        return actualRandom;
	}
}

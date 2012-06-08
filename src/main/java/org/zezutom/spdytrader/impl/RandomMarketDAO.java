package org.zezutom.spdytrader.impl;

import java.math.BigDecimal;

import org.zezutom.spdytrader.MarketDAO;

public class RandomMarketDAO implements MarketDAO {

	public BigDecimal getPrice(String asset) {
		return BigDecimal.TEN;
	}

	public String getMostTraded() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getTradersOnline() {
		// TODO Auto-generated method stub
		return 0;
	}

}

package org.zezutom.spdytrader.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.apache.log4j.Logger;

/**
 * Any kind of a traded asset.
 * 
 * @author tom
 *
 */
public class Asset {
	
	private static final Logger logger = Logger.getLogger(Price.class);

	private String name;
	
	private Collection<Price> prices;
	
	public Asset(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void addPriceValue(BigDecimal value) {
		if (prices == null) {
			prices = new HashSet<Price>();
		}
		Price price = new Price(this, value);
		prices.add(price);
		logger.info(price);
	}
	
	public Collection<Price> getPrices() {
		return (prices == null) ? null : Collections.unmodifiableCollection(prices);
	}
}

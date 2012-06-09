package org.zezutom.spdytrader;

import java.math.BigDecimal;


/**
 * Provides valuable trading information.
 * 
 * @author tom
 *
 */
public interface MarketDAO {

	/**
	 * Returns the recent price of the given asset.
	 * 
	 * @param asset	
	 * @return	the current price
	 */
	BigDecimal getPrice(String asset);
	
	/**
	 * Finds out which asset is being traded the most at the moment.
	 * 
	 * @param	the monitored portfolio
	 * @return	asset identifier
	 */
	String getMostTraded(String...portfolio);	
}

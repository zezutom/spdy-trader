package org.zezutom.spdytrader;

import java.util.Collection;

import org.zezutom.spdytrader.model.Asset;

/**
 * Captures recent trading prices.
 * 
 * @author tom
 *
 */
public interface TradingClient {
	
	// Maximum amount of seconds to wait for the server response
	public static final int TIMEOUT = 5;
	
	void subscribe(String...assets);
	
	Collection<Asset> getHistory();
}

package org.zezutom.spdytrader.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * An asset price at a given point in time.
 * 
 * @author tom
 *
 */
public class Price {
	
	private BigDecimal value;
	
	private Date created;
	
	private Asset asset;
}

package org.zezutom.spdytrader.model;

import java.math.BigDecimal;
import java.util.Date;

import org.joda.time.DateTime;

/**
 * An asset price at a given point in time.
 * 
 * @author tom
 *
 */
public class Price implements Comparable<Price> {
	
	
	private BigDecimal value;
	
	private Date created;
	
	private Asset asset;

	public Price(Asset asset, BigDecimal value) {
		this.asset = asset;
		this.value = value;
		this.created = DateTime.now().toDate();
	}
	
	public BigDecimal getValue() {
		return value;
	}

	public Date getCreated() {
		return created;
	}

	public Asset getAsset() {
		return asset;
	}

	public int compareTo(Price price) {
		
		if (price == null) {
			return -1;
		}
		
		int result = 0;
		
		if (created.before(price.getCreated())) {
			result = -1;
		} else if (created.after(price.getCreated())) {
			result = 1;
		} else {
			result = value.compareTo(price.getValue());
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (value != null) {
			sb.append(String.format("$%5.2f", value));
		}
		
		if (asset != null) {
			sb.append(": ").append(asset.getName());
		}		

		return sb.toString();
	}
}

package org.zezutom.spdytrader.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zezutom.spdytrader.ServerProxy;
import org.zezutom.spdytrader.Trader;
import org.zezutom.spdytrader.impl.SPDYServerProxy;
import org.zezutom.spdytrader.impl.SpeedyTrader;
import org.zezutom.spdytrader.model.Price;

public class SpeedyTradingTest {

	public static final ServerProxy server = new SPDYServerProxy();
	
	private static final Trader trader = new SpeedyTrader();
	
	private static final String[] PORTFOLIO = {"Acme Ltd.", "Foo Consulting", "Bar Plc."};
	@BeforeClass
	public static void setUp() throws Exception {
		assertTrue(server.start());
		trader.subscribe(PORTFOLIO);		
		letTimePass(5);		
	}
	
	@Test
	public void theWholePortfolioShouldBeMonitored() {
		Collection<String> portfolio = trader.getPortfolio();
		assertNotNull(portfolio);		
		assertThat(PORTFOLIO.length, is(portfolio.size()));
		
		for (String asset : PORTFOLIO) {
			assertTrue(portfolio.contains(asset));
		}
	}
		
	@Test
	public void pricesShouldBeUpdatedEverySingleSecond() {
		
		for (String asset : PORTFOLIO) {
			Collection<Price> history = trader.getHistory(asset);
			assertNotNull(history);
			
			Date lastUpdate = null;
			
			for (Price price : history) {
				assertNotNull(price);
				assertNotNull(price.getValue());
				
				Date created = price.getCreated();
				assertNotNull(created);
				
				if (lastUpdate != null) {
					assertTrue(secondsBetween(created, lastUpdate) < 2);
				} else {
					lastUpdate = created;
				}
			}
			
		}
	}
	
	private int secondsBetween(Date dateOne, Date dateTwo) {
		DateTime dateTimeOne = new DateTime(dateOne);
		DateTime dateTimeTwo = new DateTime(dateTwo);
		
		Seconds seconds = Seconds.secondsBetween(dateTimeOne, dateTimeTwo);
		
		return seconds.getSeconds();		
	}
	
	private static void letTimePass(int seconds) {
		int i = 0;
		while (i < seconds) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				fail("Premature end of game!");
			}
			i++;
		}		
	}
	@AfterClass
	public static void tearDown() throws Exception {
		assertTrue(server.stop());
	}
}

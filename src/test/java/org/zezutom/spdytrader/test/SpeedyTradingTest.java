package org.zezutom.spdytrader.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zezutom.spdytrader.ServerProxy;
import org.zezutom.spdytrader.Trader;
import org.zezutom.spdytrader.impl.SPDYServerProxy;
import org.zezutom.spdytrader.impl.SpeedyTrader;

import static org.junit.Assert.*;

public class SpeedyTradingTest {

	public static final ServerProxy server = new SPDYServerProxy();
	
	@BeforeClass
	public static void setUp() throws Exception {
		assertTrue(server.start());
	}
	
	@Test
	public void someTest() throws Exception {
		System.out.println("some test");
		int i = 0;
		
		Trader trader = new SpeedyTrader();
		trader.subscribe("asset A", "asset B");
		
		while (i < 5) {
			Thread.sleep(1000);
			i++;
		}
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		assertTrue(server.stop());
	}
}

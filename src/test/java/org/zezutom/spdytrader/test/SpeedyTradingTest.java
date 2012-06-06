package org.zezutom.spdytrader.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zezutom.spdytrader.ServerProxy;
import org.zezutom.spdytrader.TradingClient;
import org.zezutom.spdytrader.impl.SPDYServerProxy;
import org.zezutom.spdytrader.impl.SPDYTradingClient;

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
		
		TradingClient client = new SPDYTradingClient();
		
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

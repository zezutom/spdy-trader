package org.zezutom.spdytrader.impl;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.spdy.SPDYClient;
import org.eclipse.jetty.spdy.api.DataInfo;
import org.eclipse.jetty.spdy.api.Headers;
import org.eclipse.jetty.spdy.api.ReplyInfo;
import org.eclipse.jetty.spdy.api.SPDY;
import org.eclipse.jetty.spdy.api.Session;
import org.eclipse.jetty.spdy.api.Stream;
import org.eclipse.jetty.spdy.api.StreamFrameListener;
import org.eclipse.jetty.spdy.api.StringDataInfo;
import org.eclipse.jetty.spdy.api.SynInfo;
import org.zezutom.spdytrader.ServerProxy;
import org.zezutom.spdytrader.TradingClient;
import org.zezutom.spdytrader.model.Asset;

public class SPDYTradingClient implements TradingClient {

	public SPDYTradingClient() throws Exception {
		init();
	}
	private void init() throws Exception {
		// A client factory shared among all client instances
		SPDYClient.Factory clientFactory = new SPDYClient.Factory();
		clientFactory.start();
		
		// Create a client instance
		SPDYClient client = clientFactory.newSPDYClient(SPDY.V2); 
		
		// Obtain a session instance
		Session session = client.connect(
				new InetSocketAddress(
						ServerProxy.HOST,
						ServerProxy.PORT), null)
						.get(TIMEOUT, TimeUnit.SECONDS);
		
		// Sends SYN_STREAM to the server, adding headers
		Headers headers = new Headers();
		headers.put("url", "/prices");
		
		final Stream stream = session.syn(new SynInfo(headers, false), getStreamListener()).get(TIMEOUT, TimeUnit.SECONDS);
				
		// Schedule periodic data update
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		
		Runnable periodicTask = new Runnable() {
			
			public void run() {
				// contact the server
				stream.data(new StringDataInfo("test", false));
			}
		};
		
		executor.scheduleAtFixedRate(periodicTask, 0, 1, TimeUnit.SECONDS);
		
	}
	
	private StreamFrameListener getStreamListener() {
		return new StreamFrameListener.Adapter() {
			
			@Override
			public void onReply(Stream stream, ReplyInfo replyInfo) {
				// Reply received from server, send DATA to the server
				System.out.println("reply received from server");
				
			}
			
			@Override
			public void onData(Stream stream, DataInfo dataInfo) {
				// Data received from server
				System.out.println("data received from server");
				
			}
		};
	}	
	
	public void subscribe(String... assets) {
		// TODO Auto-generated method stub
		
	}

	public Collection<Asset> getHistory() {
		// TODO Auto-generated method stub
		return null;
	}
}

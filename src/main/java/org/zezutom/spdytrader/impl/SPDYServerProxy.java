package org.zezutom.spdytrader.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.spdy.SPDYServerConnector;
import org.eclipse.jetty.spdy.api.BytesDataInfo;
import org.eclipse.jetty.spdy.api.DataInfo;
import org.eclipse.jetty.spdy.api.Headers;
import org.eclipse.jetty.spdy.api.ReplyInfo;
import org.eclipse.jetty.spdy.api.Stream;
import org.eclipse.jetty.spdy.api.StreamFrameListener;
import org.eclipse.jetty.spdy.api.StringDataInfo;
import org.eclipse.jetty.spdy.api.SynInfo;
import org.eclipse.jetty.spdy.api.server.ServerSessionFrameListener;
import org.zezutom.spdytrader.DataTransformer;
import org.zezutom.spdytrader.MarketDAO;
import org.zezutom.spdytrader.ServerProxy;
import org.zezutom.spdytrader.impl.server.StartOperation;
import org.zezutom.spdytrader.impl.server.StopOperation;

public class SPDYServerProxy implements ServerProxy {

	private Server server;

	private DataTransformer transformer = new JacksonDataTransformer();

	private MarketDAO dao = new RandomMarketDAO();
	
	private void init() {
		server = new Server();
		
		Connector connector = new SPDYServerConnector(getSessionListener());
		connector.setPort(PORT);
		server.addConnector(connector);		
	}

	private ServerSessionFrameListener getSessionListener() {
		return new ServerSessionFrameListener.Adapter() {
			
			@Override
			public StreamFrameListener onSyn(Stream stream, SynInfo synInfo) {
				// Reply upon receiving a SYN_STREAM
				stream.reply(new ReplyInfo(false));
				
				// Inspect the headers
				Headers headers = synInfo.getHeaders();

				final String url = headers.get("url").value();
								
				return new StreamFrameListener.Adapter() {
										
					@Override
					public void onData(Stream stream, DataInfo dataInfo) {
						// Client data received: TODO inspect 'dataInfo' to get the data
						System.out.println("Received request for " + url);
												
						if ("/prices".equals(url)) {
							System.out.println("received price request for: ");							
							String[] assets = transformer.getPortfolio(dataInfo.asBytes(true));
														
							Map<String, BigDecimal> priceMap = new HashMap<String, BigDecimal>();
							
							for (String asset : assets) {
								priceMap.put(asset, dao.getPrice(asset));
							}
							stream.data(new BytesDataInfo(transformer.toBytes(priceMap), false));
						} else {
							stream.data(new StringDataInfo("replying back", false));
						}												
					}
				};
			}
		};
	}
	
	public boolean start() throws Exception {
		if (server == null) {
			init();
		}

		if (server.isStarted()) {
			return true;
		}

		return new StartOperation().trigger(this);
	}

	public boolean stop() throws Exception {
		if (server == null || server.isStopped()) {
			return true;
		}
		
		return new StopOperation().trigger(this);		
	}

	public Server getServer() {
		return server;
	}

}

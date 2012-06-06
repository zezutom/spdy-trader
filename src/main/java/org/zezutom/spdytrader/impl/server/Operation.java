package org.zezutom.spdytrader.impl.server;

import org.eclipse.jetty.server.Server;
import org.joda.time.DateTime;
import org.zezutom.spdytrader.ServerProxy;

/**
 * A server operation resulting into a new server state.
 * 
 * @author tom
 *
 */
public abstract class Operation {

	private ServerProxy proxy;
	
	public abstract boolean inProgress();
	
	public abstract boolean success();
	
	public abstract void execute() throws Exception;
	
	public Server getServer() {
		return proxy.getServer();
	}
	
	public boolean trigger(ServerProxy proxy) {
		this.proxy = proxy;

		try {
			execute();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		final DateTime max = DateTime.now().plusSeconds(ServerProxy.TIMEOUT);
		
		// wait until the operation is completed or times out
		while(inProgress()) {
			if (DateTime.now().isAfter(max)) {
				break;
			}
		}
		
		return success();
	}	
	
}

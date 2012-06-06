package org.zezutom.spdytrader.impl.server;

/**
 * Starts the server.
 * 
 * @author tom
 *
 */
public class StartOperation extends Operation {

	@Override
	public boolean inProgress() {
		return getServer().isStarting();
	}

	@Override
	public boolean success() {
		// TODO Auto-generated method stub
		return getServer().isStarted();
	}

	@Override
	public void execute() throws Exception {
		getServer().start();		
	}

}

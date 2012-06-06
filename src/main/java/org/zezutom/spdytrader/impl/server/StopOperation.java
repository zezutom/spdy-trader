package org.zezutom.spdytrader.impl.server;

public class StopOperation extends Operation {

	@Override
	public boolean inProgress() {
		return getServer().isStopping();
	}

	@Override
	public boolean success() {
		return getServer().isStopped();
	}

	@Override
	public void execute() throws Exception {
		getServer().stop();		
	}

}

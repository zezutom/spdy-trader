package org.zezutom.spdytrader;

import org.eclipse.jetty.server.Server;

/**
 * Manages server state.
 * 
 * @author tom
 *
 */
public interface ServerProxy {

	// The maximum amount of seconds given for a server stop / start to complete
	public static final int TIMEOUT = 30;
	
	// The server host
	public static final String HOST = "localhost";
	
	// The port the server is listening on
	public static final int PORT = 8181;
		
	/**
	 * Starts the server.
	 * @return true if successfully started, false otherwise
	 */
	boolean start() throws Exception;
	
	/**
	 * Stops the server.
	 * @return true if successfully stopped, false otherwise
	 */
	boolean stop() throws Exception;	

	Server getServer();
}

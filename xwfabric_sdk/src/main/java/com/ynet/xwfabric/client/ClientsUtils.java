package com.ynet.xwfabric.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientsUtils {

	private static Logger logger = LoggerFactory.getLogger(ClientsUtils.class);

	private static final ThreadLocal<ClientCons> cl = new ThreadLocal<ClientCons>();

	public static ClientCons getClients(final String mspid, final String chainCodeName,
			final String version) throws Exception {

		ClientCons clientCons = cl.get();

		logger.debug("thread name: " + Thread.currentThread().getName() + ", chainCode name: " + chainCodeName);

		if (null == clientCons) {
			FabricClient client = null;

			logger.debug("create a new client...");
			client = new FabricClient().timeoutMillis(60 * 1000).ccId(chainCodeName, version).initClient(mspid)
					.createChannel(mspid, "network-config.yaml");
			clientCons = new ClientCons(client);

			cl.set(clientCons);
		}

		return clientCons;
	}

	/**
	 * 销毁ClientCons以及FabricClient
	 */
	public static void destroy() {
		ClientCons clientCons = cl.get();
		
		if(clientCons != null) {
			FabricClient client = clientCons.getClient();
			try {
				client.shutdown();
				logger.debug("destroy the client...");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			cl.set(null);
		}
	}
}

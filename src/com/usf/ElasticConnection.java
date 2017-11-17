package com.usf;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.usf.Constants;

/**
 * ElasticConnection class is constructed to get client object for the
 * connection using TransportClient with the elasticsearch.
 * 
 * @author Chandrakala Jaiswar
 * @version 1.0
 * @since
 */
public class ElasticConnection {

	/**
	 * @return a client for the elasticsearch connection.
	 */
	public static Client getConnection() {
		Settings settings = Settings.settingsBuilder() // In ES 2.0, the
														// ImmutableSettings
														// class was indeed
														// removed
				.put(Constants.CLUSTER_NAME, Constants.ELASTIC_SEARCH).build();

		Client client = null;
		try {
			client = TransportClient.builder().settings(settings).build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getLocalHost(), 9300))
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getLocalHost(), 9300));

		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("Client not found");
		}

		return client;

	}

	/**
	 * @return a Map after constructing the JSON Document object with product ID
	 *         and product description.
	 */
	public static Map<String, Object> putJsonDocument(String productId, String productDescription) {
		Map<String, Object> jsonDocument = new HashMap<String, Object>();
		jsonDocument.put(Constants.PRODUCT_ID, productId);
		jsonDocument.put(Constants.PRODUCT_DESC, productDescription);
		return jsonDocument;
	}

}

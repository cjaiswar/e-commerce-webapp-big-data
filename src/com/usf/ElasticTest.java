package com.usf;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.MultiSearchResponse.Item;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.SearchHit;

import com.usf.Constants;

/**
 * The ESTest class is constructed in order to provide test functionality for
 * searchDocument, getDocument, updateDocument & deleteDocumentcreate.
 ** 
 * @author Chandrakala Jaiswar
 * @version 1
 * @since 11/24/2016
 */
public class ElasticTest {

	public static void main(String args[]) throws IOException {

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

		client.prepareIndex("bigecom", "product", "1")
				.setSource(putJsonDocument("APIP732GB",
						"The latest iPhone with advanced camera, better battery life, immersive speakers and water resistance!"))
				.execute().actionGet();
		client.prepareIndex("bigecom", "product", "2")
				.setSource(putJsonDocument("SMGAS732GB",
						"Fast battery charging: 60% in 30 min (Quick Charge 2.0); Wireless charging (Qi/PMA) ; market dependent; ANT+ support; S; Voice natural language commands and dictation; OneDrive (115 GB cloud storage); Active noise cancellation with dedicated mic; MP4/DivX/XviD/WMV/H.264 player; MP3/WAV/WMA/eAAC+/FLAC player; Photo/video editor; Document editor"))
				.execute().actionGet();
		client.prepareIndex("bigecom", "product", "3").setSource(putJsonDocument("SLIM1234", "Slim shirt for men"))
				.execute().actionGet();
		client.prepareIndex("bigecom", "product", "4").setSource(putJsonDocument("SLIM1233", "Slim fit for men"))
				.execute().actionGet();
		client.prepareIndex("bigecom", "product", "5").setSource(putJsonDocument("SLIM1235", "Regular fit for men"))
				.execute().actionGet();
		client.prepareIndex("bigecom", "product", "6").setSource(putJsonDocument("TSHIRT40", "Long Sleeve Tshirt"))
				.execute().actionGet();
		client.prepareIndex("bigecom", "product", "7")
				.setSource(putJsonDocument("GIRL1234",
						"Women's Round Neck Striped Stretch Basic T Shirt Tops Long Sleeve Blouse"))
				.execute().actionGet();
		client.prepareIndex("bigecom", "product", "8")
				.setSource(putJsonDocument("GIRL1235", "Women's Benton Springs Full-Zip Fleece Jacket")).execute()
				.actionGet();
		client.prepareIndex("bigecom", "product", "9")
				.setSource(putJsonDocument("GIRL1236", "Women's One Piece Striped Slim")).execute().actionGet();
		client.prepareIndex("bigecom", "product", "10")
				.setSource(putJsonDocument("GIRL1237", "Women's Innisfree Short-Sleeve Polo Shirt")).execute()
				.actionGet();
		client.prepareIndex("bigecom", "product", "11")
				.setSource(putJsonDocument("MENSHIRT234", "Workwear Scrubs Unisex Stretch V-neck Top")).execute()
				.actionGet();
		client.prepareIndex("bigecom", "product", "12")
				.setSource(putJsonDocument("HPENX360",
						"Intel Core i7-6500u CPU, 16GB RAM, 1TB HDD, Backlit Keyboard, Windows 10"))
				.execute().actionGet();
		client.prepareIndex("bigecom", "product", "13")
				.setSource(
						putJsonDocument("ACASPE1553VG", "Intel Core i5, NVIDIA 940MX, 8GB DDR4, 256GB SSD, Windows 10"))
				.execute().actionGet();
		client.prepareIndex("bigecom", "product", "14")
				.setSource(putJsonDocument("VIONB32C", "Viotek 32 inch LED Curved Model NB32C")).execute().actionGet();
		client.prepareIndex("bigecom", "product", "15")
				.setSource(putJsonDocument("NIKD3300",
						"Capture every special moment in the lifelike quality it deserves--and have a great time doing it! With its included zoom lens, the new ultra-compact AF-S DX NIKKOR 18-55mm f/3.5-5.6G VR II, the D3300 is a small, easy to use HD-SLR. Capture beautiful 24.2-MP photos and 1080p Full HD videos with vibrant colors and softly blurred backgrounds, then share them instantly with your compatible smartphone and the optional WU-1a Wireless Adapter. Whether you are-creating high-resolution panoramas, adding fun special effects or recording dazzling HD video with sound, the D3300 will bring you endless joy, excitement and memories--just like the special moments of your life"))
				.execute().actionGet();
		client.prepareIndex("bigecom", "product", "16")
				.setSource(putJsonDocument("CANONEORT5",
						"In The Box EOS-T5 Body, Eyecup Ef, Camera Cover R-F-3, Wide Strap EW-300D, Battery Charger LC-E10, Battery Pack LP-E10, Battery Cover, Interface Cable IFC-130U, EOS DIGITAL Solution Disk, EF-S 18-55mm 1:3.5-5.6 IS II, Lens Cap E-58II, Lens Dust Cap E, Camera Instructional Manual Basic (E), Camera Instructional Manual Basic (S), Camera Instructional Manual CD (5ML), Software Instructional Manual CD (5ML), Macro Booklet (E), Flash Booklet (E) 18.0 Megapixel CMOS (APS-C) image sensor and high-performance DIGIC 4 Image Processor for excellent speed and quality. ISO 100-6400 (expandable to H: 12800) for shooting from bright to dim light. EOS Full HD Movie mode helps you capture brilliant results. Scene Intelligent Auto mode helps deliver expertly optimized photos and offers improved scene detection for amazing results when shooting at night. 9-point AF system (including one center cross-type AF point) and AI Servo AF help provide necessary options for impressive autofocus performance and accurate results"))
				.execute().actionGet();
		client.prepareIndex("bigecom", "product", "17")
				.setSource(putJsonDocument("LG65UH7700",
						"High Dynamic Range Done Right. Not all UHD TVs are the same. LG SUPER UHD TVs offer a superior 4K experience by incorporating advanced technologies that deliver over a billion rich colors, smoother motion and elevated brightness. HDR Super with Dolby Vision simply provides a better way to watch"))
				.execute().actionGet();
		client.prepareIndex("bigecom", "product", "18")
				.setSource(putJsonDocument("NIKD3300",
						"Capture every special moment in the lifelike quality it deserves--and have a great time doing it! With its included zoom lens, the new ultra-compact AF-S DX NIKKOR 18-55mm f/3.5-5.6G VR II, the D3300 is a small, easy to use HD-SLR. Capture beautiful 24.2-MP photos and 1080p Full HD videos with vibrant colors and softly blurred backgrounds, then share them instantly with your compatible smartphone and the optional WU-1a Wireless Adapter. Whether you are recreating high-resolution panoramas, adding fun special effects or recording dazzling HD video with sound, the D3300 will bring you endless joy, excitement and memories--just like the special moments of your life"))
				.execute().actionGet();
		client.prepareIndex("bigecom", "product", "19")
				.setSource(putJsonDocument("BOQUCO35WL",
						"Quiet Comfort 35 wireless headphones are engineered with world-class noise cancellation that makes quiet sound quieter and music sound better. Free yourself from wires and connect easily to your devices with Bluetooth and NFC pairing. Volume-optimized EQ gives you balanced audio performance at any volume, while a noise-rejecting dual microphone provides clear calls, even in windy or noisy environments. Voice prompts and intuitive controls make communicating and controlling your music hassle-free. A lithium-ion battery gives you up to 20 hours of wireless play time per charge. And if you anticipate a situation where charging may not be possible, just plug in the included audio cable. Wired mode gives you up to 40 hours of play time per charge. Premium materials make these headphones lightweight and comfortable for all-day listening. Use the Bose Connect app for a more personalized experience. Included: Quiet Comfort 35 wireless headphones; USB charging cable; backup audio cable; airline adapter; carry case"))
				.execute().actionGet();

		System.out
				.println("Final PIDS" + searchDocument(client, "bigecom", "product", "product_description", "iPhone"));

	}

	public static Map<String, Object> putJsonDocument(String product_id, String product_description) {

		Map<String, Object> jsonDocument = new HashMap<String, Object>();

		jsonDocument.put(Constants.PRODUCT_ID, product_id);
		jsonDocument.put(Constants.PRODUCT_DESC, product_description);
		System.out.println("JSON value" + jsonDocument);
		return jsonDocument;
	}

	/**
	 * Returns a list of all the relevant product id's found with particular
	 * search.
	 * 
	 * @param client:
	 *            client for the elasticsearch connection.
	 * @param index
	 *            is the name of index in elasticsearch.
	 * @param type
	 *            is the document type.
	 * @return an List<String> object containing all the relevant product id's
	 *         found with particular search.
	 */
	public static List<String> searchDocument(Client client, String index, String type, String field, String value) {
		String[] arr = value.split(" ");
		ArrayList<String> productIds = new ArrayList<>();
		QueryBuilder query = null;
		query = QueryBuilders.boolQuery().minimumShouldMatch("75%")
				.should(QueryBuilders.fuzzyQuery(field, value).maxExpansions(1)
						.prefixLength(value.length() - value.length() / 2))
				.should(QueryBuilders.wildcardQuery(field, "*" + value + "*"));

		QueryBuilder query1 = QueryBuilders.matchQuery(field, value);
		// System.out.println("Query value"+query1);
		SearchRequestBuilder response = client.prepareSearch(index).setTypes(type).setQuery(query);

		// System.out.println("response value"+response);
		SearchRequestBuilder response1 = client.prepareSearch(index).setTypes(type).setQuery(query1);

		// System.out.println("response1 value"+response1);
		MultiSearchResponse res = client.prepareMultiSearch().add(response).add(response1).execute().actionGet();
		// System.out.println("res value"+res);

		for (Item sr : res.getResponses()) {
			SearchHit[] results = sr.getResponse().getHits().getHits();
			// System.out.println("results value is"+results);

			for (SearchHit hit : results) {
				System.out.println("------------------------------");
				Map<String, Object> result = hit.getSource();
				productIds.add((String) result.get(Constants.PRODUCT_ID));
				// System.out.println("product Ids are"+productIds);
			}
		}
		ArrayList<String> pids = new ArrayList<>();
		Set<String> hs = new HashSet<>();
		hs.addAll(productIds);
		pids.addAll(hs);

		return pids;
	}

	/**
	 * This method is to retrieve documents in elasticsearch.
	 * 
	 * @param client:
	 *            client for the elasticsearch connection.
	 * @param index
	 *            is the name of index in elasticsearch.
	 * @param type
	 *            is the document type.
	 * @param id:
	 *            id of the document.
	 */
	public static Map<String, Object> getDocument(Client client, String index, String type, String id) {

		GetResponse getResponse = client.prepareGet(index, type, id).execute().actionGet();
		Map<String, Object> source = getResponse.getSource();
		return source;

	}

	/**
	 * This method is to update a document in elasticsearch.
	 * 
	 * @param client:
	 *            client for the elasticsearch connection.
	 * @param index
	 *            is the name of index in elasticsearch.
	 * @param type
	 *            is the document type.
	 * @param id
	 *            of the document.
	 * @param field
	 *            in the document.
	 * @param newValue
	 *            for the field.
	 */
	public static void updateDocument(Client client, String index, String type, String id, String field,
			String newValue) {

		Map<String, Object> updateObject = new HashMap<String, Object>();
		updateObject.put(field, newValue);
		client.prepareUpdate(index, type, id).setScript(new Script("ctx._source." + field + "=" + field)).get();
		// .setScriptParams(updateObject).execute().actionGet();;
	}

	/**
	 * This method is to delete a document in elasticsearch.
	 * 
	 * @param client:
	 *            client for the elasticsearch connection.
	 * @param index
	 *            is the name of index in elasticsearch.
	 * @param type
	 *            is the document type.
	 * @param id
	 *            of the document.
	 */
	public static void deleteDocument(Client client, String index, String type, String id) {

		DeleteResponse response = client.prepareDelete(index, type, id).execute().actionGet();

	}
}

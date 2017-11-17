package com.usf;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bson.Document;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.BasicBSONObject;
import org.bson.types.ObjectId;
import org.elasticsearch.client.Client;
import redis.clients.jedis.Jedis;
import com.usf.ElasticConnection;
import com.usf.SearchElastic;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.usf.Constants;

/**
 * Servlet implementation class FindProducts for the products search based on
 * the product description.
 * 
 * @author Sadhashiv Sharma
 * @version 1.0
 * @since
 */
public class FindProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Client client = ElasticConnection.getConnection();
	Jedis jedis;
	DBCollection coll;
	MongoClient mongoClient = null;
	DB db;

	/**
	 * init method to initialize objects.
	 */
	@Override
	public void init() throws ServletException {

		try {
			mongoClient = new MongoClient("localhost", 27017);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db = mongoClient.getDB("test");
		System.out.println("Connection to database successfully");

		System.out.println("Collection products selected successfully");
		this.jedis = new Jedis(Constants.HOST);

	}

	/**
	 * @param request:
	 *            HttpServletRequest.
	 * @param response:
	 *            HttpServletResponse.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String searchString = request.getParameter(Constants.PRODUCT_ID).trim();
		if (searchString != null && !searchString.isEmpty()) {
			List<String> listPids = SearchElastic.searchDocument(client, Constants.INDEX, Constants.DOCUMENT_TYPE,
					Constants.PRODUCT_DESC, searchString);
			ArrayList<ArrayList<String>> pid_list = new ArrayList<ArrayList<String>>();
			Map<String, String> updateMap = new HashMap<String, String>();
			for (String product_id : listPids) {
				if (jedis.exists(product_id.toString())) {
					ArrayList<String> redisCacheDta = getDataFromCache(product_id, jedis);
					System.out.println("Redis Cache value" + redisCacheDta);
					pid_list.add(redisCacheDta);
				} else {

					DBCollection coll = db.getCollection(Constants.COLLECTION);

					List<DBObject> dbobj = null;
					BasicDBObject query = new BasicDBObject();
					// System.out.println("before query " + query);
					System.out.println("value of pid " + product_id);

					query.put("product_id", product_id);
					System.out.println("query is" + query);
					DBCursor cursor = coll.find(query);
					Iterator<DBObject> itrc = cursor.iterator();

					dbobj = cursor.toArray();

					pid_list.addAll((Collection<? extends ArrayList<String>>) dbobj);

					System.out.println("pid_List is" + pid_list);

					while (itrc.hasNext()) {
						DBObject obj = (DBObject) itrc.next();

						String mpid = (String) obj.get("product_id");

						updateMap.put(Constants.PRODUCT_ID, mpid);
						String mpname = (String) obj.get("product_name");
						updateMap.put(Constants.PRODUCT_NAME, mpname);

						String mpbrand = (String) obj.get("brand");
						updateMap.put(Constants.PRODUCT_BRAND, mpbrand);
						String mpdesc = (String) obj.get("product_description");
						updateMap.put(Constants.PRODUCT_DESC, mpdesc);

						String mpcost = (String) obj.get("price");

						updateMap.put(Constants.PRODUCT_COST, mpcost);

						String mpimage = (String) obj.get("image");
						updateMap.put(Constants.IMAGE_URL, mpimage);
						String mitem_cat = (String) obj.get("category");
						updateMap.put(Constants.ITEM_CATEGORY, mitem_cat);
						System.out.println("Hashmap value" + updateMap);
					}

					jedis.hmset(product_id, updateMap);
					System.out.println("Value of Jedis" + jedis);

				}
			}

			try {
				request.setAttribute("pidList", pid_list);
				RequestDispatcher view = request.getRequestDispatcher("/searchresult.jsp");
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			view.forward(request, response);
		}
	}

	/**
	 * Returns a list of information related to the product ID from MongoDB
	 * database in case not found in Cache.
	 * 
	 * @param product_id
	 *            (product ID)of the product.
	 * @param collection
	 *            is a collection i.e. table name in MongoDB.
	 * @return an ArrayList<String> containing information related to the
	 *         product ID from Mongo database.
	 */

	public ArrayList<String> getDataMongo(String product_id, String collection) {
		String pdid = null, pdname = null, pdbrand = null, pddesc = null;
		String pdprice = null, pdimage = null, item_cat = null;

		DBCollection coll = db.getCollection(collection);

		BasicDBObject query = new BasicDBObject();
		query.put("product_id", new ObjectId(product_id));
		DBCursor cursor = coll.find(query);
		ArrayList<String> productInfo = null;
		for (DBObject c : cursor) {
			productInfo = new ArrayList<String>();
			pdid = (String) c.get(Constants.PRODUCT_ID);
			pdname = ((BasicDBObject) c).getString(Constants.PRODUCT_NAME);
			pdbrand = ((BasicDBObject) c).getString(Constants.PRODUCT_BRAND);
			pddesc = ((BasicDBObject) c).getString(Constants.PRODUCT_DESC);
			pdprice = ((BasicDBObject) c).getString(Constants.PRODUCT_COST);
			pdimage = (String) c.get(Constants.IMAGE_URL);
			item_cat = (String) c.get(Constants.ITEM_CATEGORY);

			productInfo.add(pdid);
			productInfo.add(pdname);
			productInfo.add(pdbrand);
			productInfo.add(pddesc);
			productInfo.add(pdprice);
			productInfo.add(pdimage);
			productInfo.add(item_cat);
			System.out.println("product info is" + productInfo);
		}
		return productInfo;

	}

	/**
	 * Returns a list of information related to the product ID in cache.
	 * 
	 * @param product_id
	 *            (product ID)of the product.
	 * @param jedis
	 *            is an instance of Jedis.
	 * @return an ArrayList<String> containing information related to the
	 *         product ID in cache.
	 */
	public ArrayList<String> getDataFromCache(String product_id, Jedis jedis) {
		Map<String, String> pdtRetrieveHM = jedis.hgetAll(product_id.toString());
		ArrayList<String> rmapvalues = new ArrayList<String>();
		rmapvalues.add(0, pdtRetrieveHM.get(Constants.PRODUCT_ID));
		rmapvalues.add(0, pdtRetrieveHM.get(Constants.PRODUCT_NAME));
		rmapvalues.add(0, pdtRetrieveHM.get(Constants.PRODUCT_BRAND));
		rmapvalues.add(1, pdtRetrieveHM.get(Constants.PRODUCT_DESC));
		rmapvalues.add(2, pdtRetrieveHM.get(Constants.PRODUCT_COST));
		rmapvalues.add(3, pdtRetrieveHM.get(Constants.IMAGE_URL));
		rmapvalues.add(4, pdtRetrieveHM.get(Constants.ITEM_CATEGORY));
		return rmapvalues;
	}

	/**
	 * Closing all the open connections.
	 */
	@Override
	public void destroy() {
		jedis.close();
		mongoClient.close();

	}
}
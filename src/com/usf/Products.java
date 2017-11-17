package com.usf;

import com.mongodb.BasicDBObject;

public class Products extends BasicDBObject {

	public String product_id;
	public String product_name;
	public String brand;
	public String product_description;
	public String price;
	public String image;
	public String category;

	public Products(String spid, String spname, String sbrand, String sdesc, String sprice, String simage,
			String sitem_cat) {
		product_id = spid;
		product_name = spname;
		brand = sbrand;
		product_description = sdesc;
		price = sprice;
		image = simage;
		category = sitem_cat;

	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getProduct_description() {
		return product_description;
	}

	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}

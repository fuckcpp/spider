package com.ljx.spider.bean;


public class LinkTypeData
{
	private int id;
	/**
	 * 链接的地址
	 */
	private String bookname;
	
	
	private double point;
	
	private int people;
	
	private String bookauthor;
	
	private String publisher;
	
	private String date;
	
	private String price;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBookname() {
		return bookname;
	}

	public void setBookname(String bookname) {
		this.bookname = bookname;
	}

	public String getBookauthor() {
		return bookauthor;
	}

	public void setBookauthor(String bookauthor) {
		this.bookauthor = bookauthor;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getpeople() {
		return people;
	}

	public void setpeople(int people) {
		this.people = people;
	}

	public double getPoint() {
		return point;
	}

	public void setPoint(double point) {
		this.point = point;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price2) {
		this.price = price2;
	}

	
	

}

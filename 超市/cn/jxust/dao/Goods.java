package cn.jxust.dao;

import cn.jxust.util.OutOfDateException;

public class Goods {
	private String name;
	private double price;
	private int count;
    private String desc;
	//count代表的是商品库存数量
	public Goods (String name,double price,int count) {
		this.name=name;
		this.price=price;
		this.count=count;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDesc() {
		return desc;
	}
	public int getCount() {
		return count;
	}
	
	/**
	 * 设置商品库存数量
	 * @param count 库存数量
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
	public String getName() {
		return name;
	}
	public double getPrice() {
		return price;
	}
	//amount代表的用户购买的数量，函数返回的是该商品的总金额
	public double getShopPrice(int amount) {
		return amount*price;
	}
		@Override
		public String toString() {
			
			return this.name+"\t"+price+"\t"+count;
		}
	public void detail() throws OutOfDateException{
		System.out.println("产品名称:"+name);
		System.out.println("产品单价:"+price);
		System.out.println("产品数量:"+count);
		System.out.println("产品描述:"+desc);
	}

}
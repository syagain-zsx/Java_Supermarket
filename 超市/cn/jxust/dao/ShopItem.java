package cn.jxust.dao;
public class ShopItem {
	private Goods goods;
	private int amount;
	
	public Goods getGoods() {
		return goods;
	}

	public int getAmount() {
		return amount;
	}

	public ShopItem(Goods goods,int amount) {
		this.goods=goods;
		this.amount=amount;
	}
	
	public String itemDesc() {
		return goods.getName()+"("+goods.getPrice()+"元/件)"+"\t:"+this.amount;
	}

	public void show() {
		System.out.println(itemDesc());
		System.out.println("=================");
	}
	
	public double payPrice() {
		return goods.getShopPrice(amount);
	}
}

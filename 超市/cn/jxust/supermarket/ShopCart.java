package cn.jxust.supermarket;
import java.util.ArrayList;
import cn.jxust.dao.ShopItem;
import cn.jxust.dao.Goods;

public class ShopCart {
	//private ShopItem[] goodsItems=new ShopItem[10];
	private ArrayList<ShopItem> goodItems = new ArrayList<>();
	//private int currCount=0;
	
	public boolean addGoods(ShopItem item) {
//		if (currCount==10)
//			return false;
		goodItems.add(item);
		return true;
	}
	void show() {
		if (goodItems.isEmpty()) {
			System.out.println("购物车是空的，请添加商品");
			return;
		}
		for(int i=0;i<goodItems.size();i++) {
			goodItems.get(i).show();
		}
	}
	double payPrice() {
		double totalPrice=0;
		for(int i=0;i<goodItems.size();i++) {
			totalPrice+=goodItems.get(i).payPrice();
		}
		return totalPrice;
	}
	/**
	 * 打印购物车中所有商品的详细信息和总价
	 * 遍历购物车中的每个商品，输出商品描述和应付价格，同时累加计算总金额
	 * 最后输出购物车商品的总计金额
	 */
	void printCartDesc() {
		double totalPrice = 0; // 初始化总价为0
		// 遍历购物车中的所有商品
		for (int i = 0; i < goodItems.size(); i++) {
			System.out.print(goodItems.get(i).itemDesc()); // 输出商品描述
			System.out.println("  " + goodItems.get(i).payPrice()); // 输出商品应付价格
			totalPrice += goodItems.get(i).payPrice(); // 累加商品价格到总价
		}
		System.out.println("总计:" + totalPrice + "元"); // 输出总计金额
	}
	/**
	 * 结算后更新库存并清空购物车
	 * 在清空购物车前，遍历所有商品并减少对应库存数量
	 */
	void clearAfterPay() {
		// 遍历购物车中的每个商品，更新库存
		for (ShopItem item : goodItems) {
			Goods goods = item.getGoods(); // 获取商品信息
			int currentCount = goods.getCount(); // 获取当前库存
			int buyAmount = item.getAmount(); // 获取购买数量
			goods.setCount(currentCount - buyAmount); // 更新库存
		}
		// 清空购物车
		goodItems.clear();
	}

	/**
	 * 获取购物车商品列表
	 * @return 购物车中的商品列表
	 */
	public ArrayList<ShopItem> getGoodItems() {
		return goodItems;
	}

	/**
	 * 清空购物车中库存不足的商品
	 * 遍历购物车，移除库存数量小于购买数量的商品
	 */
	void clearInsufficientStock() {
		for (int i = goodItems.size() - 1; i >= 0; i--) {
			Goods goods = goodItems.get(i).getGoods();
			if (goods.getCount() < goodItems.get(i).getAmount()) {
				goodItems.remove(i);
			}
		}
	}

	/**
	 * 检查购物车中所有商品的库存是否足够
	 * 遍历购物车中的每个商品，对比商品库存数量和购买数量
	 * 如果存在任意商品库存不足，则返回false；否则返回true
	 * @return 库存是否足够支付
	 */
	public boolean canPay() {
		boolean res = true; // 默认库存足够
		// 遍历购物车中的所有商品
		for (int i = 0; i < goodItems.size(); i++) {
			Goods goods = goodItems.get(i).getGoods(); // 获取商品信息
			// 检查库存数量是否小于购买数量
			if (goods.getCount() < goodItems.get(i).getAmount()) {
				res = false; // 库存不足
				break; // 立即退出循环
			}
		}
		return res;
	}
}
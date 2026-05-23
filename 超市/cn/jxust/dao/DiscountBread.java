package cn.jxust.dao;

import java.util.Date;

import cn.jxust.util.OutOfDateException;

public class DiscountBread extends Bread implements DiscountInerface {
    double discount;
    double totalPayment,discountPayment;
    Date expDate;
    int vaildDay;
	public DiscountBread(String name, double price, int count, Date produceDate,double discount) {
		super(name, price, count, produceDate);
		this.discount=discount;
	}
	public double getShopPrice(int amount) {
		discountPayment= getDiscountPrice(amount,discount);
		return discountPayment;
	}

	public double getDiscountPrice(int amount, double discount) {
		totalPayment=super.getShopPrice(amount);
		Date expDate = super.getValidPeroid(); 
        Date now = new Date();
        if (expDate == null) {
            System.err.println("警告：商品有效期为空，无法计算折扣，返回原价！");
            return totalPayment;
        }
        long diffTime = expDate.getTime() - now.getTime();
        if (diffTime < 0) {
            return totalPayment;
        }
        vaildDay = (int) (diffTime / (24 * 60 * 60 * 1000));
        if (vaildDay <= 1) {
            return totalPayment * discount;
        }
        return totalPayment;
    }

	public void detail() throws OutOfDateException{
		super.detail();
		System.out.println("总金额:"+totalPayment);
		System.out.println("折扣金额:"+discountPayment);
	}
	

}

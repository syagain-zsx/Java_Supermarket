package cn.jxust.dao;

import java.text.DecimalFormat;

import cn.jxust.util.OutOfDateException;

public class DiscountMobile extends Mobile implements DiscountInerface {
    double discount;
    double totalPayment,discountPayment;
	public DiscountMobile(String name, double price, int count, String brand,String cpu,String os,int memorySize,double discount) {
		super(name, price, count,  brand, cpu, os,memorySize);
		this.discount=discount;
	}
	public double getShopPrice(int amount) {
		discountPayment= getDiscountPrice(amount,discount);
		return discountPayment;
	}
	public double getDiscountPrice(int amount, double discount) {
		totalPayment=super.getShopPrice(amount);
		if (amount>=2) {
			return totalPayment*discount;
		}
		return totalPayment;
	}
	public void detail() throws OutOfDateException{
		super.detail();
		DecimalFormat df=new DecimalFormat("#.00");
		System.out.println("总金额:"+df.format(totalPayment));
		System.out.println("折扣金额:"+df.format(discountPayment));
	}
	

}

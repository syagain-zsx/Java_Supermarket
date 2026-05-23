package cn.jxust.dao;
import cn.jxust.util.OutOfDateException;
public class Mobile extends Goods {
    private String brand;
    private String cpu;
    private String os;
    private int memorySize;
	public Mobile(String name, double price, int count,String brand,String cpu,String os,int memorySize) {
		super(name, price, count);
		this.brand=brand;
		this.cpu=cpu;
		this.memorySize=memorySize;
		this.os=os;
	}
	public void detail() throws OutOfDateException{
		System.out.println("品牌:"+this.brand);
		System.out.println("操作系统:"+this.os);
		System.out.println("CPU:"+this.cpu);
		System.out.println("内存大小:"+this.memorySize+"G");
		super.detail();
	}

}

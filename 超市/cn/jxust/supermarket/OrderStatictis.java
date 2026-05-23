package cn.jxust.supermarket;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TreeMap;
import java.util.Calendar;

import java.util.Date;


public class OrderStatictis 
{
	private static Map<Date,Integer> orderNumByTime = new TreeMap<Date,Integer>();

	public static void add(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.SECOND,0);
		c.set(Calendar.MILLISECOND,0);
		Date key = c.getTime();
		
		int value = orderNumByTime.getOrDefault(key, 0) + 1;
		orderNumByTime.put(key, value);
	}
	
	public static void print() 
	{
		String format = "yyyy-MM-dd HH:mm";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		System.out.println("支付时间\t\t支付订单数");
		for(Date key : orderNumByTime.keySet())
		{
			System.out.println(sdf.format(key) + "\t" + orderNumByTime.get(key));
		}
	}
}


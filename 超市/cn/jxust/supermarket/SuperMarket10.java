package cn.jxust.supermarket;
import java.util.Calendar;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

import cn.jxust.dao.Bread;
import cn.jxust.dao.Customer;
import cn.jxust.dao.CustomerMember;
import cn.jxust.dao.DiscountBread;
import cn.jxust.dao.DiscountMobile;
import cn.jxust.dao.Goods;
import cn.jxust.dao.Mobile;
import cn.jxust.dao.ShopItem;
import cn.jxust.util.OutOfDateException;
import cn.jxust.util.Setting;
import cn.jxust.util.FileUtil;
public class SuperMarket10 {
	
//	static Goods[] goodsList=new Goods[50];
//	static int acc; 	
	//用链表的形式计录
	static HashMap<String, ShopCart> users = new HashMap<String, ShopCart>();
	static ArrayList<Goods> goodsList = new ArrayList<>();
	static Set<String> userNameSet = new HashSet<String>();
	static CustomerManager manager=new CustomerManager();
	static Customer currentCustomer;
	
	private static void initial() {
		// 从文件加载已注册的会员信息
		manager.loadMembersFromFile();
		// 同步更新 userNameSet，确保注册时能检测到已存在的用户
		userNameSet.addAll(manager.getAllUserNames());
		
		Goods noteBook=new Goods("笔记本", 12, 20);
		noteBook.setDesc("学生用的笔记本，16开，50页");
		goodsList.add(noteBook);
		
		Mobile xiaomi8=new Mobile("小米手机8",2199,10,"小米","845","android",8);
		xiaomi8.setDesc("搭载晓龙845处理器，拥有红外人脸识别、双频G	PS等技术。具备AI双摄、光学变焦和光学防抖等功能");
		goodsList.add(xiaomi8);
		
		DiscountMobile xiaomi7=new DiscountMobile("小米手机7",1999,10,"小米","845","android",6,0.85);
		xiaomi7.setDesc("小米7将采用刘海屏设计，搭载Synaptics和Goodis的屏下指纹方案，有蓝色和黑色等颜色");
		xiaomi7.getShopPrice(2);
		goodsList.add(xiaomi7);
		
		Mobile hornor=new Mobile("华为荣耀",2699,5,"华为","麒麟950","android",6);	
		hornor.setDesc("搭载了华为海思自主研发的麒麟920芯片。该芯片基于28nm工艺制造，采用8核big.little GTS等架构");
		goodsList.add(hornor);
		
		Date validPeriod=new Date();
		Calendar c=Calendar.getInstance();
		c.setTime(validPeriod);
		c.add(Calendar.DAY_OF_MONTH,1);
		validPeriod = c.getTime();
		
		Bread bread=new Bread("菲尔雪", 10, 15,validPeriod );
		bread.setDesc("菲尔雪面包挺好吃的，比较贵，种类多");
		goodsList.add(bread);
		
		
		DiscountBread disBread=new DiscountBread("甜甜圈", 5, 30, validPeriod, 0.8);
		disBread.setDesc("甜甜圈很好吃，就是太甜了");
		disBread.getShopPrice(10);
		goodsList.add(disBread);
	}
	public static void print(String content) {
		System.out.println(content);
	}
	public static void register(Scanner input) {
		
		print("请输入用户名：");
		String userName=input.next();
		while(userNameSet.contains(userName))
		{
			print("该用户已存在，请重新输入");
			userName = input.next();
		}
		userNameSet.add(userName);
		print("请输入密码：");
		String pwd=input.next();
		print("请再次输入密码：");
		String cfmPwd=input.next();
		while (!cfmPwd.equals(pwd)) {
			print("两次密码输入不同，请重新输入：");
			print("请输入密码：");
			pwd=input.next();
			print("请再次输入密码：");
			cfmPwd=input.next();
		}
		int checkedNumber=(int)(Math.random()*9000+1000);
		print("请输入验证码："+checkedNumber+"\t");
		int checkedNum=input.nextInt();
		while (checkedNumber!=checkedNum) {
			print("验证码错误，请重新输入验证码");
			checkedNumber=(int)(Math.random()*9000+1000);
			print("请输入验证码："+checkedNumber+"\t");
			checkedNum=input.nextInt();
		}

		Customer customer;
		//为了便于测试，随机生成会员积分，实际开发中应该在登录后根据用户信息查询数据库获得会员积分
		Random random = new Random();
		int rdate = random.nextInt(2); // 生成0-1之间的随机整数
		
		if(Setting.DEBUG) 
		{
			rdate = 1; // 调试模式下强制生成会员用户
		}
		if(rdate==1)
		{
			// 生成会员用户（调试模式下 rdate=1，强制生成会员）
			customer=new CustomerMember(userName, pwd, 1000); // 新会员初始赠送1000积分
		}	
		else 
		{
			// 生成普通用户
			customer=new Customer(userName, pwd);
			print("普通用户注册成功！");
		}
		
		boolean isOk=manager.addCustomer(customer);
		if (isOk) {
			print("注册成功！");
			// 将会员信息保存到文件
			FileUtil.saveMember(customer);
		} else {
			print("注册失败！");
		}
	}
	public static void main(String[] args) {
	//功能菜单的显示 注册 登录
	Scanner input=new Scanner(System.in);
	boolean isLogin=false;
	initial();
	//ShopCart cart=new ShopCart();
	HashMap<String, ShopCart> cartData = new HashMap<String, ShopCart>();
	ShopCart cart;
	while(true) {
		print("============================欢迎来到理工超市系统==========================");
			print("1.用户注册 2.用户登录 3.浏览商品 4.查看商品详情 5.加入购物车 6.查看购物车 7.结算 8.查看会员积分 9.退出");
			print("====================================================================");
		int chioce=input.nextInt();
		switch(chioce) {
		case 1:
			print("用户注册");
			register(input);
			continue;
		case 2:
			print("用户登录");
			isLogin=login(input);
			continue;
		case 3:
			print("浏览商品");
			goodsList(input);
			continue;
		case 4:
			print("查看商品详情");
			print("请输入要查看的商品编号:");
			int goodsNum=input.nextInt();
			while (goodsNum<1||goodsNum>goodsList.size()) {
				print("输入错误，请重新输入1-"+goodsList.size());
				goodsNum=input.nextInt();
			}
			try {
				goodsList.get(goodsNum-1).detail();
			} catch (OutOfDateException e) {
				System.out.println(Setting.OUTOFDATEMSG);
			}
			continue;
		case 5:
			print("加入购物车");
			if (!isLogin) {
				print("请先登陆再购买商品");
				continue;
			}
		   
			if (!cartData.containsKey(currentCustomer.getUserName())) {
				cart = new ShopCart();
				cartData.put(currentCustomer.getUserName(), cart);
			}
			ShopItem item = addGoodsToCast(input);
			cartData.get(currentCustomer.getUserName()).addGoods(item);
			
			continue;
		case 6:
			print("查看购物车");
			if (!isLogin) {
				print("请先登陆再查看购物车");
				continue;
			}
			cart = cartData.get(currentCustomer.getUserName());
			if (cart == null ) {
				print("购物车为空");
			} else {
				cart.show();
			}
			continue;
		case 7:
			print("结算");
			if (!isLogin) {
				print("请先登陆再查看购物车");
				continue;
			}
			pay(cartData,input);
			continue;
		case 8:
				print("查看会员积分");
				if (!isLogin) {
					print("请先登录");
					continue;
				}
				showMemberPoints();
				continue;
			case 9:
				print("查看经营情况");
				OrderStatictis.print();
				System.exit(0);
				continue;
			
		case 10:
				default:
				print("输入错误，请重新输入");
		
		}
	}
}
	/**
	 * 处理用户结算支付的静态方法
	 * 包含库存检查、商品清单打印、支付确认、积分抵扣、金额验证和找零计算等流程
	 * @param cartData 存储用户购物车数据的 HashMap
	 * @param input 用于接收用户输入的 Scanner 对象
	 */
	private static void pay(HashMap<String, ShopCart> cartData, Scanner input) {
		// 获取当前用户的购物车
		ShopCart cart = cartData.get(currentCustomer.getUserName());
		
		// 检查购物车库存是否充足
		if (!checkStock(cart)) {
			return;
		}
		
		// 打印购物车商品详细信息
		print("购物车商品清单:");
		cart.printCartDesc();
		
		// 显示会员积分信息
		showMemberPoints();
		
		// 确认是否支付
		if (!confirmPayment(input)) {
			return;
		}
		
		// 获取商品总价
		double totalPrice = cart.payPrice();
		
		// 会员积分抵扣
		totalPrice = processPointDiscount(totalPrice, input);
		
		// 输入支付金额并验证
		double userPay = inputPayment(totalPrice, input);
		
		// 计算找零并输出支付成功信息
		print("支付成功，找零" + String.format("%.2f", (userPay - totalPrice)));
		
		// 会员消费后累计积分（1元=1积分，基于实际支付金额）
		accumulatePoints(totalPrice);
		
		// 清空购物车
		cart.clearAfterPay();	
		
		// 记录订单统计信息
		OrderStatictis.add(new Date());
	}

	/**
	 * 检查购物车库存是否充足
	 * @param cart 当前用户的购物车
	 * @return 库存是否充足
	 */
	private static boolean checkStock(ShopCart cart) {
		if (!cart.canPay()) {
			print("购物车部分商品库存不足，现为您清空库存不足的商品");
			cart.clearInsufficientStock();
			if (cart.getGoodItems().isEmpty()) {
				print("购物车已清空");
				return false;
			}
		}
		return true;
	}

	/**
	 * 显示会员积分信息
	 */
	private static void showMemberPoints() {
		if (currentCustomer instanceof CustomerMember) {
			CustomerMember member = (CustomerMember) currentCustomer;
			print("当前会员积分：" + member.getMemberPoints() + "分");
		}
	}

	/**
	 * 确认是否支付
	 * @param input 用户输入
	 * @return 是否确认支付
	 */
	private static boolean confirmPayment(Scanner input) {
		print("现在是否支付,Y/N ?");
		String conform = input.next();
		while (!conform.equalsIgnoreCase("Y") && !conform.equalsIgnoreCase("N")) {
			print("输入错误，重新输入Y/N ");
			conform = input.next();
		}
		return conform.equalsIgnoreCase("Y");
	}

	/**
	 * 处理会员积分抵扣
	 * 每1000积分可抵扣10元（即每100积分抵扣1元）
	 * @param totalPrice 原始总价
	 * @param input 用户输入
	 * @return 抵扣后的金额
	 */
	private static double processPointDiscount(double totalPrice, Scanner input) {
		if (!(currentCustomer instanceof CustomerMember)) {
			return totalPrice;
		}
		
		CustomerMember member = (CustomerMember) currentCustomer;
		int memberPoints = member.getMemberPoints();
		
		// 积分不足1000，不允许抵扣
		if (memberPoints < 1000) {
			return totalPrice;
		}
		
		print("你是会员，现有：" + memberPoints + "积分，每1000个积分可抵扣10元，是否使用？(Y/N)");
		String confirm = input.next();
		while (!confirm.equalsIgnoreCase("Y") && !confirm.equalsIgnoreCase("N")) {
			print("输入错误，请确认是否使用积分，Y表示使用，N表示不使用");
			confirm = input.next();
		}
		
		if (confirm.equalsIgnoreCase("N")) {
			return totalPrice;
		}
		
		// 计算最大可抵扣积分
		int maxDeductPoints = (int) (totalPrice * 100); // 每100积分抵扣1元
		maxDeductPoints = Math.min(maxDeductPoints, memberPoints);
		maxDeductPoints = (maxDeductPoints / 1000) * 1000; // 确保是1000的整数倍
		
		print("请输入要使用的积分数，应为1000的整数倍，且不能超过" + maxDeductPoints + "：");
		int usePoints = input.nextInt();
		
		// 验证积分输入
		while (usePoints < 1 || (usePoints % 1000 != 0) || usePoints > maxDeductPoints) {
			print("积分输入错误，请重新输入（1000的整数倍，不超过" + maxDeductPoints + "）：");
			usePoints = input.nextInt();
		}
		
		// 计算抵扣金额并扣除积分
		int deductAmount = usePoints / 100;
		totalPrice -= deductAmount;
		member.setMemberPoints(-usePoints);
		
		print("已使用" + usePoints + "积分，抵扣" + deductAmount + "元，抵扣后金额：" + String.format("%.2f", totalPrice) + "元");
		
		return totalPrice;
	}

	/**
	 * 输入支付金额并验证
	 * @param totalPrice 应付金额
	 * @param input 用户输入
	 * @return 用户支付的金额
	 */
	private static double inputPayment(double totalPrice, Scanner input) {
		print("请输入金额：");
		double userPay = input.nextDouble();
		while (userPay < totalPrice) {
			print("金额不足，请重新输入：");
			userPay = input.nextDouble();
		}
		return userPay;
	}

	/**
	 * 会员消费后累计积分（1元=1积分）
	 * @param actualPayAmount 实际支付金额
	 */
	private static void accumulatePoints(double actualPayAmount) {
		if (!(currentCustomer instanceof CustomerMember)) {
			return;
		}
		
		CustomerMember member = (CustomerMember) currentCustomer;
		int addPoints = (int) actualPayAmount;
		member.setMemberPoints(addPoints);
		print("本次消费获得" + addPoints + "积分，当前积分：" + member.getMemberPoints());
	}
	private static ShopItem addGoodsToCast(Scanner input) {
		print("请输入商品编号:");
		int goodsNum=input.nextInt();
		while (goodsNum<1||goodsNum>goodsList.size()) {
			print("输入错误，请重新输入1-"+goodsList.size());
			goodsNum=input.nextInt();
		}
		Goods goods=goodsList.get(goodsNum-1);
		try {
			goods.detail();
		} catch (OutOfDateException e) {
			e.printStackTrace();
		}
		print("请输入购买数量:");
		int amount=input.nextInt();
		while (amount<1||amount>goods.getCount()) {
			print("数量输入错误，请重新输入1-");
			amount=input.nextInt();
		}
		ShopItem item = new ShopItem(goods, amount);
		return item;
	}
	private static void goodsList(Scanner input) {
		print("商品列表");
		print("============================");
		print("编号\t 商品名称\t 单价\t 数量\t");
		for (int i = 0; i <goodsList.size(); i++) {
			String content=(i+1)+"\t"+goodsList.get(i).toString();
			print(content);
		}
		print("============================");
	}
	private static boolean login(Scanner input) {
		boolean isLogin=false;
		for (int i=0;i<3;i++) {
			print("请输入登录的用户名:");
			String uName=input.next();
			print("请输入登录的密码");
			String uPwd=input.next();
			if (manager.loginVaidate(uName, uPwd)) {


				//我草泥马，漏了这一行，导致登录后无法获取用户信息，无法进行购物车等后续操作，测试了好久才发现
				//sb学校的代码质量也太差了，不会把代码上传gitee吗，想对比都找不到地方
				currentCustomer = manager.getUserByName(uName);

				print("欢迎"+uName+"登录！");
				isLogin=true;
				break;
			}else {
				if (i<2) {
					print("用户名或者密码错误，您还有"+(2-i)+"次机会，请重新输入");
				}else {
					print("三次均输入用户名或密码");
				}
			}
		}

		return isLogin;
	}

}
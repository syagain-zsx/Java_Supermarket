package cn.jxust.supermarket;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import cn.jxust.dao.Customer;
import cn.jxust.util.FileUtil;

public class CustomerManager {
//	private Customer[] customers=new Customer[50];
//	private int currCount;
	
	private HashMap<String, Customer> user = new HashMap<String, Customer>();
	
	public boolean addCustomer(Customer customer) {
		user.put(customer.getUserName(),customer);
		return true;
		}
	
	public boolean loginVaidate(String UserName,String pwd) {
		boolean isLogin=false;
		// 先判断用户是否存在，避免空指针
        Customer customer = user.get(UserName);
        if (customer != null) {
            isLogin = customer.customerVaildate(UserName, pwd);
        }
        return isLogin;
    }

	public Customer getUserByName(String userName) {
		Customer customer = null;
		if (this.user.containsKey(userName)) {
			customer = this.user.get(userName);
		}
		return customer;
	}
	
	/**
	 * 从文件加载会员信息
	 */
	public void loadMembersFromFile() {
		Map<String, Customer> members = FileUtil.loadMembers();
		user.putAll(members);
	}
	
	/**
	 * 获取所有用户名
	 * @return 用户名集合
	 */
	public Set<String> getAllUserNames() {
		return user.keySet();
	}
}
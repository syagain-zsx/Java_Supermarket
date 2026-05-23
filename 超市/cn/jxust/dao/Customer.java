package cn.jxust.dao;

import cn.jxust.supermarket.SecurePwd;

public class Customer {
	private String userName;
	private String pwd;
	private String salt; // 添加盐值字段
	
	public Customer(String userName,String pwd) {
		this.userName=userName;
		this.salt=SecurePwd.generateSalt(); // 先生成盐值
		this.pwd=SecurePwd.MD5WithSalt(pwd, this.salt); // 使用带盐加密
	}
	
	 public String getUserName() {
		return userName;
	}

	 public String getPwd() {
		return pwd;
	}
	public String getSalt() {
		return salt;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public void setEncryptedPwd(String pwd) {
		this.pwd = pwd;
	}
	public boolean customerVaildate(String user,String pwd) {
		boolean isRight=false;
		// 使用存储的盐值对输入密码进行加密后比对
		String securePwd=SecurePwd.MD5WithSalt(pwd, this.salt);
		if(this.userName.equals(user)&&this.pwd.equals(securePwd))
			isRight=true;
		return isRight;
	}

	

}
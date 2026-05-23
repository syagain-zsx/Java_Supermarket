package cn.jxust.dao;

public class CustomerMember extends Customer{
    private int memberPoints; // 去掉static，每个会员独立积分
    public CustomerMember(String userName,String pwd,int memberPoints) {
        super(userName, pwd);
		this.memberPoints=memberPoints; // 初始化积分为传入的值
    }
    public int getMemberPoints() {
        return memberPoints;
    }
    // 重命名参数，避免与成员变量重名
	public void setMemberPoints(int addPoints) {
		this.memberPoints += addPoints; // 累加积分
	}
	
	/**
	 * 设置盐值（用于从文件加载）
	 */
	public void setSalt(String salt) {
        super.setSalt(salt); // 调用父类方法设置盐值
	}
	
	/**
	 * 设置加密后的密码（用于从文件加载）
	 */
	public void setEncryptedPwd(String pwd) {
        super.setEncryptedPwd(pwd); // 调用父类方法设置加密后的密码
    }
}
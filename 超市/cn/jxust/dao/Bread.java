package cn.jxust.dao;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.jxust.util.OutOfDateException;
public class Bread extends Goods {
    
	private Date validPeroid;

    public Date getValidPeroid() {
        return validPeroid;
    }

    public void setValidPeroid(Date validPeroid) {
        this.validPeroid = validPeroid;
    }

    public Bread(String name, double price, int count, Date validPeroid) {
        super(name, price, count); 
        this.validPeroid = validPeroid;
    }

	@Override
    public void detail() throws OutOfDateException {
    	if(this.validPeroid.before(new Date())) {
    		throw new OutOfDateException();
    	}
        super.detail(); 
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("商品有效期: " + df.format(this.validPeroid));
    }

	public Date getExpireDate() {
		return null;
	}
}
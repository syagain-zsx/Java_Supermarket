package cn.jxust.util;
import cn.jxust.dao.Customer;
import cn.jxust.dao.CustomerMember;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件操作工具类，用于会员信息的持久化存储
 */
public class FileUtil {

    /**
     * 保存会员信息到文件
     * @param customer 会员对象
     */
    public static void saveMember(Customer customer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\code_vs\\JAVA_Work\\超市\\members.txt", true))) {
            if (customer instanceof CustomerMember) {
                CustomerMember member = (CustomerMember) customer;
                // 格式: MEMBER,用户名,加密密码,积分,盐值
                writer.write("MEMBER," + customer.getUserName() + "," + customer.getPwd() + "," 
                        + member.getMemberPoints() + "," + customer.getSalt());
            } else {
                // 格式: NORMAL,用户名,加密密码,盐值
                writer.write("NORMAL," + customer.getUserName() + "," + customer.getPwd() + "," 
                        + customer.getSalt());
            }
            writer.newLine();
        } catch (IOException e) {
            if (Setting.DEBUG) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从文件加载所有会员信息
     * 文件格式：
     * - 普通用户：NORMAL,用户名,加密密码,盐值
     * - 会员用户：MEMBER,用户名,加密密码,积分,盐值
     * @return 会员信息映射表，key为用户名，value为Customer对象
     */
    public static Map<String, Customer> loadMembers() {
        Map<String, Customer> members = new HashMap<>();
        
        // 创建文件对象
        File file = new File(Setting.MEMBERFILE);
        // 如果文件不存在，返回空映射
        if (!file.exists()) {
            return members;
        }
        
        // 使用 try-with-resources 自动关闭流
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // 逐行读取文件内容
            while ((line = reader.readLine()) != null) {
                // 按逗号分隔每行数据
                String[] parts = line.split(",");
                // 验证数据格式是否正确（至少需要4个字段）
                if (parts.length >= 4) {
                    String type = parts[0];       // 用户类型：NORMAL 或 MEMBER
                    String userName = parts[1];   // 用户名
                    String pwd = parts[2];        // 加密后的密码
                    String salt = parts[parts.length - 1]; // 盐值（最后一个字段）
                    
                    // 判断是否为会员用户
                    if ("MEMBER".equals(type) && parts.length >= 5) {
                        int points = Integer.parseInt(parts[3]); // 会员积分
                        // 创建会员对象（密码参数传空字符串，后续用setEncryptedPwd设置）
                        CustomerMember member = new CustomerMember(userName, "", points);
                        member.setSalt(salt);           // 设置盐值
                        member.setEncryptedPwd(pwd);    // 设置加密后的密码
                        members.put(userName, member);  // 添加到映射表
                    } else {
                        // 创建普通用户对象
                        Customer customer = new Customer(userName, "");
                        customer.setSalt(salt);           // 设置盐值
                        customer.setEncryptedPwd(pwd);    // 设置加密后的密码
                        members.put(userName, customer);  // 添加到映射表
                    }
                }
            }
        } catch (IOException e) {
            // 调试模式下打印异常信息
            if (Setting.DEBUG) {
                e.printStackTrace();
            }
        }
        return members;
    }
}
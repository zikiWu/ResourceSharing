package com.zk.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.zk.bean.User;
import com.zk.dao.ConnectMysql;
import com.zk.po.vo.FileUserVO;

public class UserService {
	public static ArrayList<User> wealthRank()
    {
        ArrayList<User> al = new ArrayList<User>();
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from user where state=? order by wealth desc limit ?");
            p.setInt(1, 1);
            p.setInt(2, 10);
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
            	User user = new User();
            	user.setUsername(rs.getString(1));
            	user.setWealth(rs.getInt(5));
            	al.add(user);
            }
            ConnectMysql.close(rs, p, conn);

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("数据库连接出错");
        }
        return al;
    }
	public static ArrayList<FileUserVO> uploadRank()
    {
        ArrayList<FileUserVO> al = new ArrayList<>();
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select user_id ,user_icon,author,count(user_id) as file_num from files  f GROUP BY f.user_id ,user_icon,author  having count(f.user_id) >0  order by file_num desc limit 10");
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
                FileUserVO user = new FileUserVO();
            	user.setUserid(rs.getInt(1));
                user.setUserIcon(rs.getString(2));
                user.setUserName(rs.getString(3));
            	user.setFileNum(rs.getInt(4));
            	al.add(user);
            }
            ConnectMysql.close(rs, p, conn);

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("数据库连接出错");
        }
        return al;
    }
	
	public static boolean download(Integer userid,int money)
    {
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from fzqblog_user where userid=?");
            p.setInt(1, userid);
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
            	int mark = rs.getInt(10);
            	if(mark<money){
            		return false;
            	}
            	p = conn.prepareStatement("update fzqblog_user set mark=? where userid=?");
            	p.setInt(1, (mark-money));
            	p.setInt(2, userid);
            	int i = p.executeUpdate();
            	if(i>0){
            		return true;
            	}else{
            		return false;
            	}
            }
            ConnectMysql.close(rs, p, conn);

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("数据库连接出错");
        }
        return false;
    }
	public static User showMessage(String name)
    {
		User user = new User();
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from user where username=?");
            p.setString(1, name);
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
            	user.setUsername(rs.getString(1));
            	user.setMail(rs.getString(3));
            	user.setUpload(rs.getInt(4));
            	user.setWealth(rs.getInt(5));
            	user.setSex(rs.getString(6));
            	user.setState(rs.getInt(7));
            	
            }
            ConnectMysql.close(rs, p, conn);

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("数据库连接出错");
        }
        return user;
    }
	public static boolean update(String name, String psw, String mail, String sex) {
		try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("update user set mail=?,sex=? where username=? and password=?");
            p.setString(1, mail);
            p.setString(2, sex);
            p.setString(3, name);
            p.setString(4, psw);
            int i = p.executeUpdate();
            	if(i>0){
            		return true;
            	}else{
            		return false;
            	}

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("数据库连接出错");
        }
        return false;
	}
}

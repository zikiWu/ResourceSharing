package com.zk.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zk.dao.ConnectMysql;

public class LoginService {
	public static int judge(String n,String pass)
    {
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from user where username=? and password=?");
            p.setString(1, n);
            p.setString(2, pass);
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
            	int state = rs.getInt(7);
                ConnectMysql.close(rs, p, conn);
                return state;
            }
            ConnectMysql.close(rs, p, conn);

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("数据库连接出错");
        }
        return 0;
    }
	
	public static boolean insert(String n,String pass,String m)
    {
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("insert into user values(?,?,?,?,?,?,?)");
            p.setString(1, n);
            p.setString(2, pass);
            p.setString(3, m);
            p.setInt(4, 0);
            p.setInt(5, 10);
            p.setString(6, "男");
            p.setInt(7, 1);
            int flag = p.executeUpdate();
            p.close();
            conn.close();
            if(flag>0)
            	return true;
            else
            	return false;

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("数据库连接出错");
        }
        return false;
    }
	public static boolean update(String name,String psw1,String psw2)
    {
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("update user set password=? where username=? and password=?");
            p.setString(1, psw2);
            p.setString(2, name);
            p.setString(3, psw1);
            int flag = p.executeUpdate();
            p.close();
            conn.close();
            if(flag>0)
            	return true;
            else
            	return false;

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("数据库连接出错");
        }
        return false;
    }
}

package com.zk.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.zk.bean.Attentions;
import com.zk.bean.Grades;
import com.zk.dao.ConnectMysql;

public class AttentionsService {
	public static boolean select(Attentions a)
    {
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from attentions where username=? and fileid=?");
            p.setString(1, a.getUsername());
            p.setInt(2, a.getFileid());
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
                ConnectMysql.close(rs, p, conn);
                return true;
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
	public static boolean insert(Attentions a)
    {
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("insert into attentions (username,fileid,time) values (?,?,?)");
            p.setString(1, a.getUsername());
            p.setInt(2, a.getFileid());
            p.setString(3, a.getTime());
            int flag = p.executeUpdate();
            if(flag>0){
            	p = conn.prepareStatement("update files set attention=attention+1 where id=?");
            	p.setInt(1, a.getFileid());
            	int u = p.executeUpdate();
            	if(u>0)
            		return true;
            	else
            		return false;
            }
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
	public static boolean delect(Attentions a)
    {
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("delete from attentions where username=? and fileid=?");
            p.setString(1, a.getUsername());
            p.setInt(2, a.getFileid());
            int flag = p.executeUpdate();
            if(flag>0){
            	p = conn.prepareStatement("update files set attention=attention-1 where id=?");
            	p.setInt(1, a.getFileid());
            	int u = p.executeUpdate();
            	if(u>0)
            		return true;
            	else
            		return false;
            }
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
	
	public static ArrayList<Attentions> showAllName(String name)
    {
		ArrayList<Attentions> al = new ArrayList<Attentions>();
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from attentions where username=?");
            p.setString(1, name);
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
                Attentions a = new Attentions();
                a.setId(rs.getInt(1));
                a.setUsername(rs.getString(2));
                a.setFileid(rs.getInt(3));
                a.setTime(rs.getString(4));
                al.add(a);
            }
//            p = conn.prepareStatement("select * from files where id=?");
//            for(Attentions obj : al){
//            	p.setInt(1, obj.getFileid());
//            	rs = p.executeQuery();
//            	while(rs.next()){
//            		obj.setUsername(rs.getString(2));
//            	}
//            }
            
            ConnectMysql.close(rs, p, conn);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("数据库连接出错");
        }
        return al;
    }
}

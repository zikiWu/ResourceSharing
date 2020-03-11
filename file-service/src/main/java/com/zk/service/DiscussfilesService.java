package com.zk.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;
import com.zk.bean.Attentions;
import com.zk.bean.Discussfiles;
import com.zk.dao.ConnectMysql;

public class DiscussfilesService {
	public static ArrayList<Discussfiles> showAll(int fileid)
    {
        ArrayList<Discussfiles> al = new ArrayList<Discussfiles>();
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from discussfiles where fileid=? order by time");
            p.setInt(1, fileid);
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
            	Discussfiles file = new Discussfiles();
            	file.setId(rs.getInt(1));
            	file.setUsername(rs.getString(2));
            	file.setFileid(rs.getInt(3));
            	file.setTime(rs.getString(4));
            	file.setDiscusscontent(rs.getString(5));
            	al.add(file);
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
	public static ArrayList<Discussfiles> showAllName(String name)
    {
        ArrayList<Discussfiles> al = new ArrayList<Discussfiles>();
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from discussfiles where username=? order by time desc");
            p.setString(1, name);
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
            	Discussfiles file = new Discussfiles();
            	file.setId(rs.getInt(1));
            	file.setUsername(rs.getString(2));
            	file.setFileid(rs.getInt(3));
            	file.setTime(rs.getString(4));
            	file.setDiscusscontent(rs.getString(5));
            	al.add(file);
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
	public static ArrayList<Discussfiles> showAllName1()
    {
        ArrayList<Discussfiles> al = new ArrayList<Discussfiles>();
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from discussfiles order by time desc");
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
            	Discussfiles file = new Discussfiles();
            	file.setId(rs.getInt(1));
            	file.setUsername(rs.getString(2));
            	file.setFileid(rs.getInt(3));
            	file.setTime(rs.getString(4));
            	file.setDiscusscontent(rs.getString(5));
            	al.add(file);
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
	public static int total(int fileid)
    {
        int count = 0;
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select count(*) num from discussfiles where fileid=?");
            p.setInt(1, fileid);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
            	count = rs.getInt("num");
            }
            ConnectMysql.close(rs, p, conn);

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("数据库连接出错");
        }
        return count;
    }
	public static boolean delete(String username,int id)
    {
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("delete from discussfiles where username=? and id=?");
            p.setString(1, username);
            p.setInt(2, id);
            int flag = p.executeUpdate();
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
	public static boolean delete1(int id)
    {
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("delete from discussfiles where id=?");
            p.setInt(1, id);
            int flag = p.executeUpdate();
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
	public static int insert(Discussfiles d)
    {
		int increaseid = -1;
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("insert into discussfiles (username,fileid,time,discusscontent) values (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            p.setString(1, d.getUsername());
            p.setInt(2, d.getFileid());
            p.setString(3, d.getTime());
            p.setString(4, d.getDiscusscontent());
            int flag = p.executeUpdate();
            ResultSet rs = p.getGeneratedKeys();
            if(rs.next()){
            	increaseid = rs.getInt(1);
            }
            return increaseid;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("数据库连接出错");
        }
        return increaseid;
    }
}

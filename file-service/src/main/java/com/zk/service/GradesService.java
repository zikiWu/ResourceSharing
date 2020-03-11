package com.zk.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.zk.bean.Downfiles;
import com.zk.bean.Files;
import com.zk.bean.Grades;
import com.zk.dao.ConnectMysql;

public class GradesService {
	public static boolean select(Grades g)
    {
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from grades where username=? and fileid=?");
            p.setString(1, g.getUsername());
            p.setInt(2, g.getFileid());
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
	public static boolean insert(Grades g)
    {
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("insert into grades (username,fileid,time,score) values (?,?,?,?)");
            p.setString(1, g.getUsername());
            p.setInt(2, g.getFileid());
            p.setString(3, g.getTime());
            p.setInt(4, g.getScore());
            int flag = p.executeUpdate();
            if(flag>0){
            	p = conn.prepareStatement("select * from files where id=?");
            	p.setInt(1, g.getFileid());
            	ResultSet rs = p.executeQuery();
            	while(rs.next()){
            		int gradenum = rs.getInt(6);
            		double score = rs.getDouble(7);
            		double scoreResult = ((gradenum*score+g.getScore())*1.0)/(gradenum+1);
            		p = conn.prepareStatement("update files set evaluate=evaluate+1,score=? where id=?");
            		p.setDouble(1, scoreResult);
            		p.setInt(2, g.getFileid());
            		int f = p.executeUpdate();
            		if(flag>0)
            			return true;
            		else
            			return false;
            	}
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
	public static ArrayList<Grades> showAllName(String name) {
		ArrayList<Grades> al = new ArrayList<Grades>();
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from grades where username=?");
            p.setString(1, name);
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
                Grades a = new Grades();
                a.setId(rs.getInt(1));
                a.setUsername(rs.getString(2));
                a.setFileid(rs.getInt(3));
                a.setTime(rs.getString(4));
                a.setScore(rs.getInt(5));
                al.add(a);
            }
            p = conn.prepareStatement("select * from files where id=?");
            for(Grades obj : al){
            	p.setInt(1, obj.getFileid());
            	rs = p.executeQuery();
            	while(rs.next()){
            		obj.setUsername(rs.getString(2));
            	}
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
}

package com.zk.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.zk.bean.Attentions;
import com.zk.bean.Downfiles;
import com.zk.bean.Files;
import com.zk.dao.ConnectMysql;

public class DownfilesService {
	public static boolean insertfile(Downfiles obj)
    {
        //处理业务逻辑
        try
        {
            Connection conn = ConnectMysql.getConnection();
            String insertSQL = "insert into downfiles (username,fileid,time,user_id) values(?,?,?,?)";
            PreparedStatement p = conn.prepareStatement(insertSQL);
            p.setString(1, obj.getUsername());
            p.setInt(2, obj.getFileid());
            p.setString(3, obj.getTime());
            p.setInt(4, obj.getUserId());
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

	public static ArrayList<Downfiles> showAllName(String name) {
		ArrayList<Downfiles> al = new ArrayList<Downfiles>();
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from downfiles where username=?");
            p.setString(1, name);
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
                Downfiles a = new Downfiles();
                a.setId(rs.getInt(1));
                a.setUsername(rs.getString(2));
                a.setFileid(rs.getInt(3));
                a.setTime(rs.getString(4));
                al.add(a);
            }
            p = conn.prepareStatement("select * from files where id=?");
            for(Downfiles obj : al){
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

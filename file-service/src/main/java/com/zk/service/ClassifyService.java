package com.zk.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.zk.bean.Bigclassify;
import com.zk.bean.Smallclassify;
import com.zk.dao.ConnectMysql;
import com.zk.mapper.BigclassifyMapper;
import com.zk.po.enums.MainFileCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassifyService {

    @Autowired
    private BigclassifyMapper bigclassifyMapper;

    public static ArrayList<ArrayList<ArrayList<Smallclassify>>> all()
    {
    	ArrayList<ArrayList<ArrayList<Smallclassify>>> al3 = new ArrayList<ArrayList<ArrayList<Smallclassify>>>();
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p1 = conn.prepareStatement("select * from bigclassify where state=?");
            PreparedStatement p2 = conn.prepareStatement("select * from smallclassify where belong=?");
            for(int s=1;s<=6;s++){
            	ArrayList<ArrayList<Smallclassify>> al2 = new ArrayList<ArrayList<Smallclassify>>();
            	p1.setInt(1, s);
            	ResultSet rs1 = p1.executeQuery();
            	while(rs1.next()){
            		int id = rs1.getInt(1);
            		String name = rs1.getString(2);
            		ArrayList<Smallclassify> al1 = new ArrayList<Smallclassify>();
            		Smallclassify sc1 = new Smallclassify();
            		sc1.setId(id);
            		sc1.setName(name);
            		al1.add(sc1);
            		p2.setInt(1, id);
            		ResultSet rs2 = p2.executeQuery();
            		while(rs2.next()){
            			Smallclassify sc2 = new Smallclassify();
            			sc2.setId(rs2.getInt(1));
            			sc2.setName(rs2.getString(2));
            			sc2.setBelong(rs2.getInt(3));
            			al1.add(sc2);
            		}
            		al2.add(al1);
            	}
            	al3.add(al2);
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("数据库连接出错");
        }
        return al3;
    }
    
    public static ArrayList<String[]> selectBig(int i)
    {
    	ArrayList<String[]> al = new ArrayList<String[]>();
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from bigclassify where state=?");
            p.setInt(1, i);
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
            	String[] sarr = new String[2];
            	sarr[0] = rs.getInt(1)+"";
            	sarr[1] = rs.getString(2);
//                sarr[3] = rs.getString(3);
            	al.add(sarr);
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
    
    public static ArrayList<String[]> selectSmall(int i)
    {
    	ArrayList<String[]> al = new ArrayList<String[]>();
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from smallclassify where belong=?");
            p.setInt(1, i);
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
            	String[] sarr = new String[2];
            	sarr[0] = rs.getInt(1)+"";
            	sarr[1] = rs.getString(2);
            	al.add(sarr);
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

package com.zk.service;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.zk.bean.Files;
import com.zk.bean.User;
import com.zk.dao.ConnectMysql;

public class FilesService {
	public static ArrayList<Files> showAll(String timeflag,String content,int pagenumber)
    {
		int pagenum = (pagenumber-1)*6;
        ArrayList<Files> al = new ArrayList<Files>();
        try
        {
            Connection conn = ConnectMysql.getConnection();
            String sql = "";
            if(timeflag.equals("1")){
            	sql = "select * from (select * from files where title like \"%"+content+"%\" order by time desc) a limit ?,?";
            }else if(timeflag.equals("2")){
            	sql = "select * from (select * from files where title like \"%"+content+"%\" order by attention desc) a limit ?,?";
            }
            PreparedStatement p = conn.prepareStatement(sql);
            p.setInt(1, pagenum);
            p.setInt(2, 10);
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
            	Files file = new Files();
            	file.setId(rs.getInt(1));
            	file.setTitle(rs.getString(2));
            	file.setSize(rs.getDouble(3));
            	file.setAuthor(rs.getString(4));
            	file.setAttention(rs.getInt(5));
            	file.setEvaluate(rs.getInt(6));
            	file.setScore(rs.getDouble(7));
            	file.setType(rs.getString(8));
            	file.setBelong(rs.getInt(9));
            	file.setTime(rs.getString(10));
            	file.setDownload(rs.getInt(11));
            	file.setMoney(rs.getInt(12));
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
	
	public static ArrayList<Files> downloadRank()
    {
        ArrayList<Files> al = new ArrayList<Files>();
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from (select * from files order by download desc) a limit 10");
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
            	Files file = new Files();
            	file.setId(rs.getInt(1));
            	file.setTitle(rs.getString(2));
            	file.setType(rs.getString(8));
            	file.setBelong(rs.getInt(9));
            	file.setDownload(rs.getInt(11));
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
	
	public static Files showfile(int id)
    {
		Files file = new Files();
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from files where id=?");
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
            	file.setId(rs.getInt(1));
            	file.setTitle(rs.getString(2));
            	file.setSize(rs.getDouble(3));
            	file.setAuthor(rs.getString(4));
            	file.setAttention(rs.getInt(5));
            	file.setEvaluate(rs.getInt(6));
            	file.setScore(rs.getDouble(7));
            	file.setType(rs.getString(8));
            	file.setBelong(rs.getInt(9));
            	file.setTime(rs.getString(10));
            	file.setDownload(rs.getInt(11));
            	file.setMoney(rs.getInt(12));
                file.setUserId(rs.getInt(13));
                file.setUserIcon(rs.getString(14));
                file.setShuoMing(rs.getString(15));
            }
            ConnectMysql.close(rs, p, conn);

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("数据库连接出错");
        }
        return file;
    }
	
	public static ArrayList<Files> sameAuthor(String author)
    {
        ArrayList<Files> al = new ArrayList<Files>();
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from (select * from files where author=? order by time desc) o limit 5");
            p.setString(1, author);
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
            	Files file = new Files();
            	file.setId(rs.getInt(1));
            	file.setTitle(rs.getString(2));
            	file.setType(rs.getString(8));
            	file.setBelong(rs.getInt(9));
            	file.setDownload(rs.getInt(11));
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
	
	public static ArrayList<Files> showAllName(String author)
    {
        ArrayList<Files> al = new ArrayList<Files>();
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from files where author=? order by time desc");
            p.setString(1, author);
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
            	Files file = new Files();
            	file.setId(rs.getInt(1));
            	file.setTitle(rs.getString(2));
            	file.setType(rs.getString(8));
            	file.setBelong(rs.getInt(9));
            	file.setTime(rs.getString(10));
            	file.setDownload(rs.getInt(11));
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
	public static ArrayList<Files> showAllName1()
    {
        ArrayList<Files> al = new ArrayList<Files>();
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from files order by time desc");
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
            	Files file = new Files();
            	file.setId(rs.getInt(1));
            	file.setTitle(rs.getString(2));
            	file.setType(rs.getString(8));
            	file.setBelong(rs.getInt(9));
            	file.setTime(rs.getString(10));
            	file.setDownload(rs.getInt(11));
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
	
	public static ArrayList<Files> fleshfile(int belong)
    {
        ArrayList<Files> al = new ArrayList<Files>();
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from (select * from files where belong=? order by time desc) o limit 5");
            p.setInt(1, belong);
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
            	Files file = new Files();
            	file.setId(rs.getInt(1));
            	file.setTitle(rs.getString(2));
            	file.setType(rs.getString(8));
            	file.setBelong(rs.getInt(9));
            	file.setDownload(rs.getInt(11));
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
	
	public static ArrayList<Files> find1(int id,String timeflag,int pagenumber)
    {
        ArrayList<Files> al = new ArrayList<Files>();
        int pagenum = (pagenumber-1)*6;
        try
        {
            Connection conn = ConnectMysql.getConnection();
            String sql = "";
            if(id==0){
            	if(timeflag.equals("1"))
            		sql = "select * from (select * from files order by time desc) a limit "+pagenum+",6";
            	else
            		sql = "select * from (select * from files order by attention desc) a limit "+pagenum+",6";
            }else{
            	if(timeflag.equals("1"))
            		sql = "select * from (select * from files where belong in (select id from smallclassify where belong in (select id from bigclassify where state="+id+")) order by time desc) a limit "+pagenum+",6";
            	else
            		sql = "select * from (select * from files where belong in (select id from smallclassify where belong in (select id from bigclassify where state="+id+")) order by time desc) a limit "+pagenum+",6";
            }
            PreparedStatement p = conn.prepareStatement(sql);
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
            	Files file = new Files();
            	file.setId(rs.getInt(1));
            	file.setTitle(rs.getString(2));
            	file.setSize(rs.getDouble(3));
            	file.setAuthor(rs.getString(4));
            	file.setAttention(rs.getInt(5));
            	file.setEvaluate(rs.getInt(6));
            	file.setScore(rs.getDouble(7));
            	file.setType(rs.getString(8));
            	file.setBelong(rs.getInt(9));
            	file.setTime(rs.getString(10));
            	file.setDownload(rs.getInt(11));
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
	public static ArrayList<Files> find2(int id,String timeflag,int pagenumber)
    {
        ArrayList<Files> al = new ArrayList<Files>();
        int pagenum = (pagenumber-1)*6;
        try
        {
            Connection conn = ConnectMysql.getConnection();
            String sql = "";
            	if(timeflag.equals("1"))
            		sql = "select * from (select * from files where belong in (select id from smallclassify where belong=?)  order by time desc) a limit "+pagenum+",6";
            	else
            		sql = "select * from (select * from files where belong in (select id from smallclassify where belong=?) order by time desc) a limt "+pagenum+",6";
            PreparedStatement p = conn.prepareStatement(sql);
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
            	Files file = new Files();
            	file.setId(rs.getInt(1));
            	file.setTitle(rs.getString(2));
            	file.setSize(rs.getDouble(3));
            	file.setAuthor(rs.getString(4));
            	file.setAttention(rs.getInt(5));
            	file.setEvaluate(rs.getInt(6));
            	file.setScore(rs.getDouble(7));
            	file.setType(rs.getString(8));
            	file.setBelong(rs.getInt(9));
            	file.setTime(rs.getString(10));
            	file.setDownload(rs.getInt(11));
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
	public static ArrayList<Files> find3(int id,String timeflag,int pagenumber)
    {
        ArrayList<Files> al = new ArrayList<Files>();
        int pagenum = (pagenumber-1)*6;
        try
        {
            Connection conn = ConnectMysql.getConnection();
            String sql = "";
            	if(timeflag.equals("1"))
            		sql = "select * from (select * from files where belong=?  order by time desc) a limit "+pagenum+",6";
            	else
            		sql = "select * from (select * from files where belong=? order by time desc) a limit "+pagenum+",6";
            PreparedStatement p = conn.prepareStatement(sql);
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
            	Files file = new Files();
            	file.setId(rs.getInt(1));
            	file.setTitle(rs.getString(2));
            	file.setSize(rs.getDouble(3));
            	file.setAuthor(rs.getString(4));
            	file.setAttention(rs.getInt(5));
            	file.setEvaluate(rs.getInt(6));
            	file.setScore(rs.getDouble(7));
            	file.setType(rs.getString(8));
            	file.setBelong(rs.getInt(9));
            	file.setTime(rs.getString(10));
            	file.setDownload(rs.getInt(11));
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
	
	public static boolean insertfile(Files obj)
    {
        //处理业务逻辑
        try
        {
            Connection conn = ConnectMysql.getConnection();
            String insertSQL = "insert into files (title,size,author,attention,evaluate,score,type,belong,time,download,money,user_id,user_icon,shuo_ming) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement p = conn.prepareStatement(insertSQL);
            p.setString(1, obj.getTitle());
            p.setDouble(2, obj.getSize());
            p.setString(3, obj.getAuthor());
            p.setInt(4, obj.getAttention());
            p.setInt(5, obj.getEvaluate());
            p.setDouble(6, obj.getScore());
            p.setString(7, obj.getType());
            p.setInt(8, obj.getBelong());
            p.setString(9, obj.getTime());
            p.setInt(10, obj.getDownload());
            p.setInt(11, obj.getMoney());
            p.setInt(12, obj.getUserId());
            p.setString(13, obj.getUserIcon());
            p.setString(14, obj.getShuoMing());
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
	public static boolean updateWealth(String name)
    {
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("update user set wealth=wealth+2 where username=?");
            p.setString(1, name);
            int f = p.executeUpdate();
            if(f>0){
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
	
	public static Map<String,String> title(int id)
    {
		Map<String,String> m = new HashMap<String,String>();
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from files where id=?");
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            while(rs.next())
            {
            	String t = rs.getString(2);
            	String money = rs.getInt(12)+"";
            	m.put("title", t);
            	m.put("money", money);
            }
            ConnectMysql.close(rs, p, conn);

        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("数据库连接出错");
        }
        return m;
    }
	public static boolean updateDownload(int id)
    {
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("update files set download=download+1 where id=?");
            p.setInt(1, id);
            int f = p.executeUpdate();
            if(f>0){
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
	public static String selectFileName(int id)
    {
		String name = "";
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("select * from files where id=?");
            p.setInt(1, id);
            ResultSet rs = p.executeQuery();
            while(rs.next()){
            	name = rs.getString(2);
            }
            ConnectMysql.close(rs, p, conn);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            System.out.println("数据库连接出错");
        }
        return name;
    }
	public static boolean deleteFile(String name){
		//File f = new File("C:\\tomcat\\webapps\\informationSharing\\resources\\files\\"+name);
		File f = new File("../informationSharing/WebContent/files/"+name);
		boolean b = f.delete();
		return b;
	}
	public static boolean delete(String username,int id)
    {
        try
        {
            Connection conn = ConnectMysql.getConnection();
            PreparedStatement p = conn.prepareStatement("delete from files where author=? and id=?");
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
            PreparedStatement p = conn.prepareStatement("delete from files where id=?");
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
}

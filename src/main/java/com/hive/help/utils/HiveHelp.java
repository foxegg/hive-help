package com.hive.help.utils;

import java.lang.reflect.Field;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * JDBC 操作 Hive
 *
 */
@Slf4j
public class HiveHelp {

	private static String driverName = "org.apache.hive.jdbc.HiveDriver";
	private static String url = "jdbc:hive2://192.168.3.3:10000/hive_data";// hive_data 是数据库名称
	private static String user = "root";// hadoop中可以訪問hdfs的用戶
	private static String password = "root";// 該用戶的密碼

	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet rs = null;

	public static void main(String[] args){
		HiveHelp hiveHelp = new HiveHelp();
		try {
			hiveHelp.init();
			hiveHelp.deopTables();
			hiveHelp.getCreateTableSql();
			hiveHelp.showTables();
			//hiveHelp.deleteOneDay();
			//hiveHelp.selectData();
			hiveHelp.destory();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void deleteOneDay() throws SQLException, ParseException {
		for(Class hiveClass: allHiveClass){
			StringBuilder sb = new StringBuilder();
			String className = hiveClass.getSimpleName();

			java.util.Date startData = new SimpleDateFormat("yyyyMMdd").parse(FtpUtilsDownload.START_DATE);
			long deleteTime = startData.getTime()/1000;
			String addString = "";
			if(className.startsWith("FKN")){
				addString = "000";
			}
			sb.append("delete from "+className +" where crawlerTime>"+deleteTime + addString);
			System.out.println("删除语句:"+sb);
			stmt.execute(sb.toString());
		}
	}

	public void init() throws Exception {
		Class.forName(driverName);
		conn = DriverManager.getConnection(url, user, password);
		stmt = conn.createStatement();
	}

	// 创建数据库

	public void createDatabase() throws Exception {
		String sql = "create database test";
		log.info("sql:{}", sql);
		stmt.execute(sql);
	}

	// 查询所有数据库
	public void showDatabases() throws Exception {
		String sql = "show databases";
		log.info("sql:{}", sql);
		rs = stmt.executeQuery(sql);
		while (rs.next()) {
			System.out.println(rs.getString(1));
		}
	}

	public void loadDataFromPath(String path,String table) throws Exception {
		String sql = "load data local inpath '"+path+"' into table "+table;
		log.info("sql:{}", sql);
		stmt.execute(sql);
	}

	// 删除数据库表
	public void deopTables() throws Exception {
		for(Class hiveClass: allHiveClass) {
			String className = hiveClass.getSimpleName();
			String sql = "drop table if exists "+className;
			log.info("sql:{}", sql);
			stmt.execute(sql);
		}
	}

	//所有需要入库hive类
	public static Class[] allHiveClass = {
			com.hive.help.bean.bigdata.hivebean.Device.class,
			com.hive.help.bean.bigdata.hivebean.Location.class,
			com.hive.help.bean.bigdata.hivebean.App.class,
			com.hive.help.bean.bigdata.hivebean.Contact.class,
			com.hive.help.bean.bigdata.hivebean.Sms.class,

			com.hive.help.bean.bigdata.hivebean.Idcardpics.class,
			com.hive.help.bean.bigdata.hivebean.CreditContacts.class,
			com.hive.help.bean.bigdata.hivebean.BaseInfo.class,
			com.hive.help.bean.bigdata.hivebean.ExtraInfo.class,

			com.hive.help.bean.bigdata.hivebean.FKN001.class,
			com.hive.help.bean.bigdata.hivebean.FKN002.class,
			com.hive.help.bean.bigdata.hivebean.FKN003.class,
	};
	public void getCreateTableSql(){
		for(Class hiveClass: allHiveClass){
			StringBuilder sb = new StringBuilder();
			String className = hiveClass.getSimpleName();
			sb.append("create table "+className +"(");
			Field[] fields = hiveClass.getDeclaredFields();
			for(Field field:fields){
				String type = field.getType().getSimpleName().toLowerCase();
				if(type.equals("long")){
					type = "bigint";
				}
				sb.append(field.getName() + " " + type+",");
			}
			sb.delete(sb.lastIndexOf(","),sb.length());
			sb.append(")");
			sb.append(
					"row format delimited fields terminated by ',' " +
					"lines terminated by '\\n' " +
					"stored as textfile");
			try {
				String sql = sb.toString();
				System.out.println(sql);
				createTable(sql);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// 创建表
	public void createTable(String sql) throws Exception {
		//String sql = "create table test (id int ,name string) row format delimited fields terminated by  '\\t' ";
		log.info("sql:{}", sql);
		stmt.execute(sql);
	}

	public void createTable1() throws Exception {
		String sql = "create table test1 (id int ,name string) row format delimited fields terminated by  '\\t' ";
		log.info("sql:{}", sql);
		stmt.execute(sql);
	}

	// 查询所有表
	public void showTables() throws Exception {
		String sql = "show tables";
		log.info("sql:{}", sql);
		rs = stmt.executeQuery(sql);
		while (rs.next()) {
			System.out.println(rs.getString(1));
		}
	}

	// 查看表结构
	public void descTable() throws Exception {
		String sql = "desc emp";
		log.info("sql:{}", sql);
		rs = stmt.executeQuery(sql);
		while (rs.next()) {
			System.out.println(rs.getString(1) + "\t" + rs.getString(2));
		}
	}

	// 加载数据
	public void loadData() throws Exception {
		String filePath = "/home/hadoop/data/emp.txt";
		String sql = "load data local inpath '" + filePath + "' overwrite into table test";
		log.info("sql:{}", sql);
		stmt.execute(sql);
	}

	// 查询数据
	public void selectData() throws Exception {
		for(Class hiveClass: allHiveClass){
			StringBuilder sb = new StringBuilder();
			String className = hiveClass.getSimpleName();

			String sql = "select * from "+className+ " order by crawlerTime desc limit 1";
			log.info("sql:{}", sql);
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				System.out.println(className+"当前最大时间为:"+rs.getString("crawlerTime"));
			}
		}

	}

	// 统计查询（会运行mapreduce作业）
	public void countData() throws Exception {
		String sql = "select count(1) from test";
		log.info("sql:{}", sql);
		rs = stmt.executeQuery(sql);
		while (rs.next()) {
			System.out.println(rs.getInt(1));
		}
	}

	// 删除数据库
	public void dropDatabase() throws Exception {
		String sql = "drop database if exists test";
		log.info("sql:{}", sql);
		stmt.execute(sql);
	}

	// 删除数据库表
	public void deopTable() throws Exception {
		String sql = "drop table if exists test";
		log.info("sql:{}", sql);
		stmt.execute(sql);
	}
	public static void main1(String[] args){
		String a = "1234\"56'789";
		System.out.println(a.replaceAll("\"",""));
	}
	public StringBuilder insertData(List objs) throws Exception {
		StringBuilder sb = new StringBuilder();
		Class classData = objs.get(0).getClass();
		String className = classData.getSimpleName().toLowerCase();
		System.out.println("insert:"+className+",size:"+objs.size());
		for(Object obj:objs){
			Field[] fields = classData.getDeclaredFields();
			for(Field field:fields){
				if(field.get(obj)!=null){
					sb.append(field.get(obj).toString().replaceAll("\\r?\\n", " ") .replaceAll(",","")+",");
				}else{
					sb.append(",");
				}
			}
			sb.append("\n");
		}
		//System.out.println(sb);
		//stmt.execute(sb.toString());
		return sb;
	}

	// 释放资源
	public void destory() throws Exception {
		if (rs != null) {
			rs.close();
		}
		if (stmt != null) {
			stmt.close();
		}
		if (conn != null) {
			conn.close();
		}
	}

}

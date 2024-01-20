package com.hive.help.utils;

import com.hive.help.utils.mysql.HiveHelpMysql;
import com.jcraft.jsch.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

/**
 * @author laoYou
 */
public class FtpUtilsHive {

  /**
   * 超时时间
   */
  private static final String TIME_OUT = "6000";
  /**
   * sftp对象
   */
  private static ChannelSftp channelSftp = null;
  /**
   * 会话
   */
  private static Session session = null;
  public  static void  close(){
    if(channelSftp!=null){
      channelSftp.disconnect();
    }

    if(session!=null){
      session.disconnect();
    }
  }
  /**
   * 判断ftp是否已连接
   *
   * @return
   */
  public static boolean isOpen() {
    try {
      channelSftp = new ChannelSftp();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * ftp链接
   */
  public static void connectionFtp(String user, String ip, Integer port) {
    try {
      JSch.setLogger(new SftpLogger()); //设置打印类
      boolean open = isOpen();
      System.out.println("open:"+open);
      if (open) {
        // 创建JSch对象
        JSch jsch = new JSch();
        String priKeyBasePath = "D:\\logs\\id_rsa";
        jsch.addIdentity(priKeyBasePath);

        // 通过 用户名，主机地址，端口 获取一个Session对象
        session = jsch.getSession(user, ip, port);
        session.setPassword("qwer1234");
        // 为Session对象设置properties
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        // 设置超时时间
        session.setTimeout(Integer.parseInt(TIME_OUT));
        // 建立链接
        session.connect();
        // 打开SFTP通道
        // 通道
        Channel channel = session.openChannel("sftp");
        // 建立SFTP通道的连接
        channel.connect();
        channelSftp = (ChannelSftp) channel;
        System.out.println("执行完成");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static String path = "/var/lib/mysql-files/";
  public static String client = "client/";
  public static String credit = "credit/";
  public static String service = "service/";

  private static String FILE_NAME = "scorpio.8050.1.DATE.log";

  public static String format_YYYY_MM_DD(Date date) {
    return new SimpleDateFormat("yyyyMMdd").format(date);
  }

  /**
   * 根据起始日期获取所有文件名称
   * @return
   */
  public static ArrayList<String> getAllFileNames(Class[] hiveClasses){
    ArrayList<String> names = new ArrayList<>();
    Date nowDate = new Date();
    Date startData = null;
    try {
      startData = new SimpleDateFormat("yyyyMMdd").parse(FtpUtilsDownload.START_DATE);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    while(startData.getTime()<nowDate.getTime()){
      String startDataStr = format_YYYY_MM_DD(startData);
      String fileName = FILE_NAME.replace("DATE",startDataStr);
      //非今日数据处理
      if(!format_YYYY_MM_DD(startData).equals(format_YYYY_MM_DD(nowDate))){
        for(Class hiveClass: hiveClasses) {
          String className = hiveClass.getSimpleName().toLowerCase();
          names.add(className+"_"+fileName);
        }
      }
      startData = new Date(startData.getTime()+1000*60*60*24);
    }
    return names;
  }


  public static void main(String[] args) throws ParseException {
    //连接服务器
    connectionFtp("root","192.168.3.3",22);
    //连接hive
    try {
      hiveHelp.init();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    download(FtpUtilsHive.getAllFileNames(HiveHelpMysql.allClientHiveClass),path+client);
    //download(FtpUtilsHive.getAllFileNames(HiveHelpMysql.allCreditHiveClass),path+credit);
    //download(FtpUtilsHive.getAllFileNames(HiveHelpMysql.allScoprioHiveClass),path+service);

    close();
  }

  private static void download(ArrayList<String> fileNames,String path){
    for(String fileName:fileNames){
      //下载文件
      if(fileName.startsWith("app")){
        downloadFile(path,fileName);
      }
    }
  }

  /**
   * 下载单个文件
   * @param remotePath：远程下载目录
   * @param localFileName：保存文件名
   * @return
   */
  public static void downloadFile(String remotePath, String localFileName) {
    try {
      System.out.println("检查文件是否存在:"+remotePath+localFileName);
      long remoteFileLength = getFileSize(remotePath + localFileName);
      System.out.println("remoteFile.length:"+remoteFileLength);
      //远程文件不存在
      if(remoteFileLength<=0){
        //return;
      }
      hiveHelp.loadDataFromPath(remotePath+localFileName,localFileName.split("_")[0]);
      System.out.println(remotePath + localFileName + "插入数据库完成");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  static HiveHelpMysql hiveHelp = new HiveHelpMysql();

  /**
   * 获取远程文件大小
   * @param srcSftpFilePath
   * @return
   */
  public static long getFileSize(String srcSftpFilePath) {
    long fileSize;//文件大于等于0则存在
    try {
      SftpATTRS sftpATTRS = channelSftp.lstat(srcSftpFilePath);
      fileSize = sftpATTRS.getSize();
    } catch (Exception e) {
      fileSize = -1;//获取文件大小异常
      if (e.getMessage().toLowerCase().equals("no such file")) {
        fileSize = -2;//文件不存在
      }
    }
    return fileSize;
  }
}
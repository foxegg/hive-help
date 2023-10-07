package com.hive.help.utils;

import com.alibaba.fastjson.JSONObject;
import com.hive.help.bean.bigdata.databean.*;
import com.hive.help.bean.bigdata.hivebean.*;
import com.hive.help.common.enums.DateType;
import org.springframework.beans.BeanUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadFileTextHelp {
    /**
     * 读取整个文件
     * @param fileName
     */
    public static void readFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                initText(line);
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
    private static String nowPath = null;
    private static String nowName = null;
    static HiveHelp hiveHelp = new HiveHelp();
    public static void main(String[] args){
        try {
            hiveHelp.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        DateType.cleanDate();
        ArrayList<String> fileNames = FtpUtilsDownload.getAllFileNames();
        for(String fileName:fileNames){
            System.out.println("读取文件开始:"+fileName);
            nowName = fileName;

            nowPath = FtpUtilsDownload.destFilePath+ FtpUtilsDownload.client;
            readFile(FtpUtilsDownload.destFilePath+ FtpUtilsDownload.client+fileName);
            //文件读完遍历插入数据
            for (DateType value : DateType.values()) {
                if(value.getData().size()>0){
                    endAndInsert(value.getData());
                }
            }
            nowPath = FtpUtilsDownload.destFilePath+ FtpUtilsDownload.credit;
            readFile(FtpUtilsDownload.destFilePath+ FtpUtilsDownload.credit+fileName);
            //文件读完遍历插入数据
            for (DateType value : DateType.values()) {
                if(value.getData().size()>0){
                    endAndInsert(value.getData());
                }
            }

            nowPath = FtpUtilsDownload.destFilePath+ FtpUtilsDownload.service;
            readFile(FtpUtilsDownload.destFilePath+ FtpUtilsDownload.service+fileName);
            //文件读完遍历插入数据
            for (DateType value : DateType.values()) {
                if(value.getData().size()>0){
                    endAndInsert(value.getData());
                }
            }
            System.out.println("读取文件结束:"+fileName);
        }
        typeCount.forEach((k,v)->{
            System.out.println(k+":"+v);
        });

        try {
            hiveHelp.destory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 一旦超过500条立即插入
     * @param dataList
     */
    private static void endAndInsert(List dataList){
        try {
            outFile(hiveHelp.insertData(dataList),dataList);
            dataList.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 一旦超过500条立即插入
     * @param dataList
     */
    private static void checkAndInsert(List dataList){
        if(dataList.size()>=20000){
            try {
                outFile(hiveHelp.insertData(dataList),dataList);
                dataList.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void outFile(StringBuilder sb,List dataList){
        Class classData = dataList.get(0).getClass();
        String className = classData.getSimpleName().toLowerCase();
        String outPath = nowPath.replace("/logs/","/logs_out/");
        File outDir = new File(outPath);
        if(!outDir.exists()){
            outDir.mkdirs();
        }
        File outFile = new File(outPath+className+"_"+nowName);
        FileWriter writer = null;
        try {
            // 二、检查目标文件是否存在，不存在则创建
            if (!outFile.exists()) {
                outFile.createNewFile();// 创建目标文件
            }
            // 三、向目标文件中写入内容
            // FileWriter(File file, boolean append)，append为true时为追加模式，false或缺省则为覆盖模式
            writer = new FileWriter(outFile, true);
            writer.append(sb.toString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static HashMap<String,Integer> typeCount = new HashMap<>();

    /**
     * 大批量数据分开录入
     */
    static boolean ADD_APP = true;
    /**
     * 解析单行数据
     * @param text
     */
    public static void initText(String text){
        JSONObject json = (JSONObject)JSONObject.parse(text);
        String dataId = json.getString("dataId");
        //设备上报
        if (dataId.equals(DateType.LOCATION.getCode())) {//已保存
            LocationLog locationLog = JSONObject.parseObject(text, LocationLog.class);
            Location location = new Location();
            BeanUtils.copyProperties(locationLog,location);
            BeanUtils.copyProperties(locationLog.getReport(),location);
            if(ADD_APP){
                DateType.LOCATION.getData().add(location);
            }

            checkAndInsert(DateType.LOCATION.getData());
        }else if (dataId.equals(DateType.DEVICE.getCode())) {//已保存
            DeviceLog deviceLog = JSONObject.parseObject(text, DeviceLog.class);
            Device device = new Device();
            BeanUtils.copyProperties(deviceLog,device);
            BeanUtils.copyProperties(deviceLog.getReport(),device);
            if(ADD_APP){
                DateType.DEVICE.getData().add(device);
            }
            checkAndInsert(DateType.DEVICE.getData());
        }else if (dataId.equals(DateType.SMS.getCode())) {
            SmsLog smsLog = JSONObject.parseObject(text, SmsLog.class);
            if(smsLog.getReport()!=null){
                for(SmsReport smsReport:smsLog.getReport()){
                    Sms sms = new Sms();
                    BeanUtils.copyProperties(smsLog,sms);
                    BeanUtils.copyProperties(smsReport,sms);
                    sms.setCreateTime(smsReport.getTime());
                    if(ADD_APP){
                        DateType.SMS.getData().add(sms);
                    }
                    checkAndInsert(DateType.SMS.getData());
                }
            }
        }else if (dataId.equals(DateType.CONTACTS.getCode())) {
            ContactLog contactLog = JSONObject.parseObject(text, ContactLog.class);
            if(contactLog.getReport()!=null){
                for(ContactReport contactReport:contactLog.getReport()){
                    Contact contact = new Contact();
                    BeanUtils.copyProperties(contactLog,contact);
                    BeanUtils.copyProperties(contactReport,contact);
                    if(ADD_APP){
                        DateType.CONTACTS.getData().add(contact);
                    }
                    checkAndInsert(DateType.CONTACTS.getData());
                }
            }
        }else if (dataId.equals(DateType.APP.getCode())) {
            AppLog appLog = JSONObject.parseObject(text, AppLog.class);
            if(appLog.getReport()!=null){
                for(AppReport appReport:appLog.getReport()){
                    App app = new App();
                    BeanUtils.copyProperties(appLog,app);
                    BeanUtils.copyProperties(appReport,app);
                    if(ADD_APP){
                        DateType.APP.getData().add(app);
                    }
                    checkAndInsert(DateType.APP.getData());
                }
            }
        }
        //授信提交信息
        else if (dataId.equals(DateType.idcardpics.getCode())) {//已保存
            IdcardpicsLog idcardpicsLog = JSONObject.parseObject(text, IdcardpicsLog.class);
            Idcardpics idcardpics = new Idcardpics();
            BeanUtils.copyProperties(idcardpicsLog,idcardpics);
            BeanUtils.copyProperties(idcardpicsLog.getReport(),idcardpics);
            if(ADD_APP){
                DateType.idcardpics.getData().add(idcardpics);
            }
            checkAndInsert(DateType.idcardpics.getData());
        }else if (dataId.equals(DateType.credit_contacts.getCode())) {//已保存
            CreditContactsLog creditContactsLog = JSONObject.parseObject(text, CreditContactsLog.class);
            CreditContacts creditContacts = new CreditContacts();
            BeanUtils.copyProperties(creditContactsLog,creditContacts);
            BeanUtils.copyProperties(creditContactsLog.getReport(),creditContacts);
            if(ADD_APP){
                DateType.credit_contacts.getData().add(creditContacts);
            }
            checkAndInsert(DateType.credit_contacts.getData());
        }else if (dataId.equals(DateType.extra_info.getCode())) {//已保存
            ExtraInfoLog extraInfoLog = JSONObject.parseObject(text, ExtraInfoLog.class);
            ExtraInfo extraInfo = new ExtraInfo();
            BeanUtils.copyProperties(extraInfoLog,extraInfo);
            BeanUtils.copyProperties(extraInfoLog.getReport(),extraInfo);
            if(ADD_APP){
                DateType.extra_info.getData().add(extraInfo);
            }
            checkAndInsert(DateType.extra_info.getData());
        }else if (dataId.equals(DateType.base_info.getCode())) {//已保存
            BaseInfoLog baseInfoLog = JSONObject.parseObject(text, BaseInfoLog.class);
            BaseInfo baseInfo = new BaseInfo();
            BeanUtils.copyProperties(baseInfoLog,baseInfo);
            BeanUtils.copyProperties(baseInfoLog.getReport(),baseInfo);
            if(ADD_APP){
                DateType.base_info.getData().add(baseInfo);
            }
            checkAndInsert(DateType.base_info.getData());
        }
        //决策结果
        else if (dataId.equals(DateType.FKN001.getCode())) {
            FKN001Log fnk001Log = JSONObject.parseObject(text, FKN001Log.class);
            if(fnk001Log.getReport()!=null){
                for(FKN001Report fkn001Report:fnk001Log.getReport()){
                    FKN001 fkn001 = new FKN001();
                    BeanUtils.copyProperties(fnk001Log,fkn001);
                    BeanUtils.copyProperties(fkn001Report,fkn001);
                    if(ADD_APP){
                        DateType.FKN001.getData().add(fkn001);
                    }
                    checkAndInsert(DateType.FKN001.getData());
                }
            }
        }else if (dataId.equals(DateType.FKN002.getCode())) {
            FKN002Log fnk002Log = JSONObject.parseObject(text, FKN002Log.class);
            if(fnk002Log.getReport()!=null){
                for(FKN002Report fkn002Report:fnk002Log.getReport()){
                    FKN002 fkn002 = new FKN002();
                    BeanUtils.copyProperties(fnk002Log,fkn002);
                    BeanUtils.copyProperties(fkn002Report,fkn002);
                    if(ADD_APP){
                        DateType.FKN002.getData().add(fkn002);
                    }
                    checkAndInsert(DateType.FKN002.getData());
                }
            }
        }else if (dataId.equals(DateType.FKN003.getCode())) {
            FKN003Log fnk003Log = JSONObject.parseObject(text, FKN003Log.class);
            if(fnk003Log.getReport()!=null){
                for(FKN003Report fkn003Report:fnk003Log.getReport()){
                    FKN003 fkn003 = new FKN003();
                    BeanUtils.copyProperties(fnk003Log,fkn003);
                    BeanUtils.copyProperties(fkn003Report,fkn003);
                    if(ADD_APP){
                        DateType.FKN003.getData().add(fkn003);
                    }
                    checkAndInsert(DateType.FKN003.getData());
                }
            }
        }

        typeCount.put(dataId,typeCount.get(dataId)==null?1:typeCount.get(dataId)+1);
    }



}
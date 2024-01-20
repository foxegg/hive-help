/**
  * Copyright 2023 json.cn 
  */
package com.hive.help.bean.bigdata.hivebean;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2023-09-20 14:34:23
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class Sms {
    public String deriveNo;
    public String district;
    public String dataId;
    public String dataType;
    public String dataVersion;
    public String channel;
    public String channelSub;
    public String tokenId;
    public String userGid;
    public String remoteAddr;
    public long crawlerTime;
    public int totalNum;
    public String phone;
    public String msg;
    public int type;
    //这个字段已替换,原名time,hive不允许有time命名字段
    public long createTime;
    public String name;
}
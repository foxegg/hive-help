/**
  * Copyright 2023 json.cn 
  */
package com.hive.help.bean.bigdata.databean;

import lombok.Data;

/**
 * Auto-generated: 2023-09-20 14:49:14
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class BaseInfoLog {

    private String email;
    private String phone;
    private String dataId;
    private String dataType;
    private String dataVersion;
    private String channel;
    private String channelSub;
    private String appId;
    private String tokenId;
    private String userGid;
    private String remoteAddr;
    private long crawlerTime;
    private int totalNum;
    private BaseInfoReport report;
}
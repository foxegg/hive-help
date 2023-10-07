/**
  * Copyright 2023 json.cn 
  */
package com.hive.help.bean.bigdata.databean;

import lombok.Data;

/**
 * Auto-generated: 2023-09-20 14:46:36
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class ExtraInfoLog {

    private int opType;
    private String dataId;
    private String dataType;
    private String dataVersion;
    private String channel;
    private String channelSub;
    private String appId;
    private String userGid;
    private String remoteAddr;
    private long crawlerTime;
    private int totalNum;
    private ExtraInfoReport report;
}
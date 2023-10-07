/**
  * Copyright 2023 json.cn 
  */
package com.hive.help.bean.bigdata.databean;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2023-09-20 14:38:56
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class AppLog {

    private String deriveNo;
    private String district;
    private String dataId;
    private String dataType;
    private String dataVersion;
    private String channel;
    private String channelSub;
    private String tokenId;
    private String userGid;
    private String remoteAddr;
    private long crawlerTime;
    private int totalNum;
    private List<AppReport> report;
}
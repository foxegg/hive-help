/**
  * Copyright 2023 json.cn 
  */
package com.hive.help.bean.bigdata.databean;
import lombok.Data;

import java.util.List;

/**
 * Auto-generated: 2023-09-20 14:53:26
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class FKN001Log {

    private String policyResult;
    private String policyGid;
    private String scorePolicyGid;
    private String policyVersion;
    private int policyStage;
    private String refuseCode;
    private String modelName;
    private String tokenCon;
    private String tokenApp;
    private String tokenDev;
    private String tokenLoc;
    private String dataId;
    private String dataType;
    private String dataVersion;
    private String channel;
    private String channelSub;
    private String tokenId;
    private String userGid;
    private long crawlerTime;
    private List<FKN001Report> report;
}
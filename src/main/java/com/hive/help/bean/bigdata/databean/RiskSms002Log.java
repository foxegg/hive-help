package com.hive.help.bean.bigdata.databean;

import lombok.Data;

@Data
public class RiskSms002Log {
    public String dataId;
    public String dataType;
    public String dataVersion;
    public String tokenId;
    public String userGid;
    public long crawlerTime;
    public RiskSms002LogReport report;
}

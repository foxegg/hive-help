package com.hive.help.bean.bigdata.hivebean;

import com.hive.help.bean.bigdata.databean.RiskSms002LogReport;
import lombok.Data;

@Data
public class RiskSms002 {
    public String dataId;
    public String dataType;
    public String dataVersion;
    public String tokenId;
    public String userGid;
    public long crawlerTime;
    public Integer smsOvdD3Cnt;
    public Integer smsRepayD14Cnt;
    public Integer smsRmdD7Cnt;
    public Integer smsRmdD3Cnt;
    public Integer smsOvdD7Cnt;
    public Integer smsTotD30Cnt;
    public Integer smsRmdD14OVRd30Rt;
    public Double smsWalletD30AvgBalG;
    public Integer smsWalletD14AvgCrGamt;
    public Integer smsOvdPhoneD14Cnt;
    public Integer smsRepayD7OVRd14Rt;
    public Double smsWalletD14MedBalG;
    public Integer smsOvdD14Cnt;
    public String smsToken;
}

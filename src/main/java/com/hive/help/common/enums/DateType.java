package com.hive.help.common.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public enum DateType {
    //数据上报
    LOCATION("LOC01", new ArrayList<String>()),
    DEVICE("DEV01", new ArrayList<String>()),
    SMS("SMS01", new ArrayList<String>()),
    CONTACTS("CON01", new ArrayList<String>()),
    APP("APP01", new ArrayList<String>()),

    //授信提交信息
    idcardpics("indonesia.idcardpics", new ArrayList<String>()),
    credit_contacts("indonesia.contacts", new ArrayList<String>()),
    extra_info("indonesia.extra.info", new ArrayList<String>()),
    base_info("indonesia.baseinfo", new ArrayList<String>()),

    //决策结果
    FKN001("FKN-001", new ArrayList<String>()),
    FKN002("FKN-002", new ArrayList<String>()),
    FKN003("FKN-003", new ArrayList<String>()),
    RISKSMS002("RISKSMS-002", new ArrayList<String>()),
    ;


    DateType(String typeCode, List data) {
        this.code = typeCode;
        this.data = data;
    }

    private String code; // code

    private List<String> data; // 描述


    public String getCode() {
        return code;
    }

    public List getData() {
        return data;
    }

    /**
     * 通过方法code获取枚举信息
     *
     * @param code code
     * @return
     * @date: 2021-03-10 10:11
     */
    public static DateType get(String code) {
        return Arrays.stream(DateType.values()).filter(e -> e.getCode().equals(code)).findFirst().orElse(null);
    }

    public static void cleanDate() {
        for (DateType value : DateType.values()) {
            value.data.clear();
        }
    }
}

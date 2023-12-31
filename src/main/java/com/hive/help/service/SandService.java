package com.hive.help.service;

import com.alibaba.fastjson.JSONObject;
import com.hive.help.common.enums.SandMethodEnum;

/**
 * @desc 示例
 * @date 2021/03/12
 * @author: sandpay
 * */
public interface SandService {

    /**
     * 接口调用
     */
    JSONObject invoke(JSONObject param, SandMethodEnum sandMethodEnum);

    JSONObject walletIsOpen(Integer userId);

    JSONObject getPayInfo(String oriCustomerOrderNo);
}

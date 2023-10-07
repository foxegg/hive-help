/**
  * Copyright 2023 json.cn 
  */
package com.hive.help.bean.bigdata.databean;
import lombok.Data;

import java.util.Date;

/**
 * Auto-generated: 2023-09-20 14:38:56
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class AppReport {

    private String appName;
    private String appId;
    private boolean systemApp;
    private String versionName;
    private long versionCode;
    private String firstInstallTime;
    private String lastUpdateTime;
}
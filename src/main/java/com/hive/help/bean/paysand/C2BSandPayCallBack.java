package com.hive.help.bean.paysand;

import lombok.Data;

@Data
public class C2BSandPayCallBack
{
    private C2BSandPayHead head;

    private C2BLogSandPayCallBack body;
}
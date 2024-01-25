package com.hive.help.utils.mysql;

public class Test {
    public static void main(String[] args) {
        StringBuilder sbTmp = new StringBuilder();
        sbTmp.append("You don\u0027t have enough balance to complete your payment of P299.00 to Google. Use GCredit or Cash-In to increase your balance and try again.,"
                .replaceAll("\\\\", "")
                .replaceAll("[\r|\n]", " ")
                .replaceAll(",", " "));
        if (sbTmp.length() > 512) {
            sbTmp.delete(512, sbTmp.length());
        }
        System.out.println(changeMsg(sbTmp.toString()));
    }

    private static String changeMsg(String msg) {
        msg = msg.replaceAll("\\u000a", "\n");
        msg = msg.replaceAll("\\u0009", "\t");
        msg = msg.replaceAll("\\u0008", "\b");
        msg = msg.replaceAll("\\u000d", "\r");
        msg = msg.replaceAll("\\u000c", "\f");
        msg = msg.replaceAll("\\u0027", "'");
        msg = msg.replaceAll("\\u0022", "\"");
        msg = msg.replaceAll("\\u005c", "\\");
        return msg;
    }
}

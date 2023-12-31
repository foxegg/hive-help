package com.hive.help.utils;

import java.util.Hashtable;

public class SftpLogger implements com.jcraft.jsch.Logger {
    static Hashtable<Integer, String> name = new Hashtable<Integer, String>();

    static {
        name.put(new Integer(DEBUG), "DEBUG: ");
        name.put(new Integer(INFO), "INFO: ");
        name.put(new Integer(WARN), "WARN: ");
        name.put(new Integer(ERROR), "ERROR: ");
        name.put(new Integer(FATAL), "FATAL: ");
    }

    @Override
    public boolean isEnabled(int level) {
        return true;
    }

    @Override
    public void log(int level, String message) {
        //这里我们就用控制台红色字体输出
        System.err.print(name.get(new Integer(level)));
        System.err.println(message);
    }

    public void login(String username, String password, String host, int port) {

    }
}
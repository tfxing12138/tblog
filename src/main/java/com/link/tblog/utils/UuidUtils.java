package com.link.tblog.utils;

import java.util.UUID;

public class UuidUtils {

    public static String generateId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "").substring(0,16); // 去除 "-" 符号
    }

}

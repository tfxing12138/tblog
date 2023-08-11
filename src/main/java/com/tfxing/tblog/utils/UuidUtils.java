package com.tfxing.tblog.utils;

import java.util.UUID;

public class UuidUtils {

    public static String generateId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", ""); // 去除 "-" 符号
    }

}

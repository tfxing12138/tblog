package com.tfxing.tblog.utils;

public class ValidUtils {

    public static <T> void validParam(T t, String message) {
        if(null == t) {
            throw new RuntimeException(message);
        }
    }
}

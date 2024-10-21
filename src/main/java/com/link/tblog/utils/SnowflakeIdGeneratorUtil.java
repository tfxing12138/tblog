package com.tfxing.tblog.utils;

public class SnowflakeIdGeneratorUtil {
    // 使用单例模式，确保只创建一个 SnowflakeIdGenerator 实例
    private static final SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1, 1);

    // 静态方法获取 ID
    public static long getId() {
        return generator.nextId();
    }
}

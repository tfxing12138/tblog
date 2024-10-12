package com.tfxing.tblog.utils;

public class SnowflakeIdGenerator {
    // 数据中心 ID 0-31
    private final long datacenterId;
    // 工作节点 ID 0-31
    private final long workerId;
    // 时间戳起始时间
    private final long epoch = 1288834974657L;
    // 序列号 0-4095
    private long sequence = 0L;
    // 上次生成 ID 的时间戳
    private long lastTimestamp = -1L;

    // 数据中心和工作节点 ID 不能超过 31
    public SnowflakeIdGenerator(long workerId, long datacenterId) {
        if (workerId > 31 || workerId < 0) {
            throw new IllegalArgumentException("Worker ID can't be greater than 31 or less than 0.");
        }
        if (datacenterId > 31 || datacenterId < 0) {
            throw new IllegalArgumentException("Datacenter ID can't be greater than 31 or less than 0.");
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        // 如果系统时钟回拨，抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id");
        }

        // 如果当前时间戳和上次生成 ID 的时间戳相同，则序列加一
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & 4095; // 4095 = 2^12 - 1
        } else {
            sequence = 0; // 不同时间戳重置序列
        }

        lastTimestamp = timestamp;

        // 生成的 ID 计算方式，确保它是正整数
        long id = ((timestamp - epoch) << 22) | (datacenterId << 17) | (workerId << 12) | sequence;

        // 检查生成的 ID 是否为负数，如果是，则抛出异常
        if (id < 0) {
            throw new RuntimeException("Generated ID is negative, which is not allowed.");
        }

        return id;
    }

}

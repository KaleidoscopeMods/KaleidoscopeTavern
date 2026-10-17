package com.github.ysbbbbbb.kaleidoscopetavern.util;

public class ShakerTime {
    public static final int MAX_TIME = 5500;
    public static final int SYNC_INTERVAL = 100;
    public static final int MAX_TOLERANCE = 200;

    public static long elapsedMillis(long start, long now) {
        return (now - start) / 1_000_000L;
    }

    public static int getTolerance(int latency, int jitter) {
        return (int) Math.min(MAX_TOLERANCE, 50L + Math.max(0, latency) / 2L + Math.max(0, jitter));
    }

    public static long selectTime(long serverTime, int clientTime, int tolerance) {
        // 客户端只能在服务端允许的范围内修正用时
        if (clientTime >= 0 && clientTime <= MAX_TIME
                && Math.abs(serverTime - clientTime) <= Math.min(MAX_TOLERANCE, tolerance)) {
            return clientTime;
        }
        return serverTime;
    }

    public static double getProgress(double previous, double synced, long elapsed) {
        // 收到同步后平滑校正，未收到同步时最多外推 200 毫秒
        double time = Math.max(0, elapsed);
        double progress = previous + (synced - previous) * Math.min(1, time / SYNC_INTERVAL)
                + Math.min(time, MAX_TOLERANCE);
        return Math.max(0, Math.min(MAX_TIME, Math.min(synced + MAX_TOLERANCE, progress)));
    }
}

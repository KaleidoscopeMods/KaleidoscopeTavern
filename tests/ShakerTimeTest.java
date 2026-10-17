package com.github.ysbbbbbb.kaleidoscopetavern.util;

public class ShakerTimeTest {
    // 编译模组后运行：java -ea -cp build/classes/java/main tests/ShakerTimeTest.java
    public static void main(String[] args) {
        // 真实时间不受服务器 tick 数影响，也能处理 nanoTime 数值回绕
        long start = Long.MAX_VALUE - 1_000_000L;
        assert ShakerTime.elapsedMillis(start, start + 4_700_000_000L) == 4700;

        assert ShakerTime.getTolerance(0, 0) == 50;
        assert ShakerTime.getTolerance(100, 30) == 130;
        assert ShakerTime.getTolerance(1000, 1000) == 200;
        assert ShakerTime.getTolerance(Integer.MAX_VALUE, Integer.MAX_VALUE) == 200;

        // 边界内接受客户端用时，超出边界或伪造的用时回退到服务端
        assert ShakerTime.selectTime(5020, 4900, 120) == 4900;
        assert ShakerTime.selectTime(5021, 4900, 120) == 5021;
        assert ShakerTime.selectTime(5100, 4900, 1000) == 4900;
        assert ShakerTime.selectTime(5101, 4900, 1000) == 5101;
        assert ShakerTime.selectTime(4700, -1, 200) == 4700;
        assert ShakerTime.selectTime(5500, 5700, 200) == 5500;
        assert ShakerTime.selectTime(0, Integer.MAX_VALUE, 200) == 0;

        // 同步间隔内平滑推进，断档后不再无限外推
        assert ShakerTime.getProgress(4600, 4700, 0) == 4600;
        assert ShakerTime.getProgress(4600, 4700, 50) == 4700;
        assert ShakerTime.getProgress(4600, 4700, 100) == 4800;
        assert ShakerTime.getProgress(4600, 4700, 200) == 4900;
        assert ShakerTime.getProgress(4600, 4700, 2000) == 4900;
        assert ShakerTime.getProgress(5400, 5500, 200) == 5500;

        // 收包后的主线程排队时间不会改变用于结算的真实用时
        long receivedStart = 1_000_000_000L;
        long receivedStop = receivedStart + 4_700_000_000L;
        long handledStop = receivedStop + 2_000_000_000L;
        assert ShakerTime.elapsedMillis(receivedStart, handledStop) == 6700;
        assert ShakerTime.selectTime(ShakerTime.elapsedMillis(receivedStart, receivedStop), 4700, 50) == 4700;
        System.out.println("ShakerTimeTest passed");
    }
}

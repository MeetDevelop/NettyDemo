package cn.meetdevelop.netty.test;

import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * description: Test
 * date: 2020/5/14 16:08
 * author: zgy
 * version: 1.0
 */
public class Test {
    public static void main(String[] args) {

        int max = Math.max(1, SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        System.out.println(max);

    }
}

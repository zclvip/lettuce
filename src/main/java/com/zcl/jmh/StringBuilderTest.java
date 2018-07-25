package com.zcl.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Created by zhaocl1 on 2018/4/25.
 */
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 3)
@Measurement(iterations = 10,time = 5,timeUnit = TimeUnit.SECONDS)
@Threads(16)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class StringBuilderTest {

    @Benchmark
    public void testStringAdd(){
        String a = "";
        for (int i = 0; i < 10; i++) {
            a += i;
        }
//        System.out.println(a);
    }

    @Benchmark
    public void testStringBuilderAdd(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(i);
        }
//        System.out.println(sb.toString());
    }

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(StringBuilderTest.class.getSimpleName())
                .forks(2)
                .build();

        new Runner(options).run();
    }
}

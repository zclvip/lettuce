package com.zcl.jmh;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Created by zhaocl1 on 2018/4/25.
 */
public class JmlTest {

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(StringBuilderTest.class.getSimpleName())
                .output("D:/benchmark.log")
                .forks(2)
                .build();

        new Runner(options).run();
    }
}

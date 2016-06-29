package com.fourlastor.ilovebenchmarks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.runner.RunWith;

import dk.ilios.spanner.Benchmark;
import dk.ilios.spanner.BenchmarkConfiguration;
import dk.ilios.spanner.SpannerConfig;
import dk.ilios.spanner.config.RuntimeInstrumentConfig;
import dk.ilios.spanner.junit.SpannerRunner;

@RunWith(SpannerRunner.class)
public class BenchmarkTest {

    private static final List<Integer> LIST = new ArrayList<>(Collections.nCopies(1000, 1));
    private static final ArrayList<Integer> ARRAY_LIST = new ArrayList<>(Collections.nCopies(1000, 1));

    @BenchmarkConfiguration
    public SpannerConfig configuration = new SpannerConfig.Builder()
            .trialsPrExperiment(2)
            .addResultProcessor(new UploadToStuffProcessor("http://url/goes.here"))
            .addInstrument(RuntimeInstrumentConfig.unittestConfig())
            .build();

    @Benchmark
    public void forWithSizeExtractedOnList() {
        int accumulator = 0;

        int size = LIST.size();
        for (int i = 0; i < size; i++) {
            accumulator += LIST.get(i);
        }
    }

    @Benchmark
    public void forWithSizeNotExtractedOnList() {
        int accumulator = 0;

        for (int i = 0; i < LIST.size(); i++) {
            accumulator += LIST.get(i);
        }
    }

    @Benchmark
    public void forEnhancedOnList() {
        int accumulator = 0;

        for (Integer integer : LIST) {
            accumulator += integer;
        }
    }

    @Benchmark
    public void forWithSizeExtractedOnArrayList() {
        int accumulator = 0;

        int size = ARRAY_LIST.size();
        for (int i = 0; i < size; i++) {
            accumulator += ARRAY_LIST.get(i);
        }
    }

    @Benchmark
    public void forWithSizeNotExtractedOnArrayList() {
        int accumulator = 0;

        for (int i = 0; i < ARRAY_LIST.size(); i++) {
            accumulator += ARRAY_LIST.get(i);
        }
    }

    @Benchmark
    public void forEnhancedOnArrayList() {
        int accumulator = 0;

        for (Integer integer : ARRAY_LIST) {
            accumulator += integer;
        }
    }
}

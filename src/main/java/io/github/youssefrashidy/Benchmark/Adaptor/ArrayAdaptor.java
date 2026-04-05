package io.github.youssefrashidy.Benchmark.Adaptor;


import io.github.youssefrashidy.ArrayGeneration.ArrayType;

import java.util.ArrayList;

public interface ArrayAdaptor  {
    BenchMarkArray getRandomArray(int size ) ;
    BenchMarkArray getNearlySorted_10(int size) ;
    BenchMarkArray getNearlySorted_5(int size) ;
    BenchMarkArray getNearlySorted_1(int size) ;
}

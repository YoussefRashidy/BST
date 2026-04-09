package io.github.youssefrashidy.Benchmark.Adaptor;



import io.github.youssefrashidy.ArrayGeneration.ArrayGenerator;
import io.github.youssefrashidy.ArrayGeneration.ArrayType;

import java.util.ArrayList;

public class BenchMarkArrayGenerator implements ArrayAdaptor {
    private final int seed = 1234 ;
    ArrayGenerator generator = new ArrayGenerator(seed) ;

    @Override
    public BenchMarkArray getRandomArray(int size) {
        return new BenchMarkArray(ArrayType.RANDOM,generator.randomArray(size , size*10)) ;
    }

    @Override
    public BenchMarkArray getNearlySorted_10(int size) {
        return new BenchMarkArray(ArrayType.NEARLY_SORTED_10,generator.nearlySortedArray(size,10 * size / 100 , size*10)) ;
    }

    @Override
    public BenchMarkArray getNearlySorted_5(int size) {
        return new BenchMarkArray(ArrayType.NEARLY_SORTED_5,generator.nearlySortedArray(size,5 * size / 100,size*10)) ;
    }

    @Override
    public BenchMarkArray getNearlySorted_1(int size) {
        return new BenchMarkArray(ArrayType.NEARLY_SORTED_1,generator.nearlySortedArray(size, size / 100,size*10)) ;
    }
}

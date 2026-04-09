package io.github.youssefrashidy.Benchmark;

public record PairSummary(StatSummary bst , StatSummary rb) {
    @Override
    public String toString(){
        return bst.toString() + "\n" + rb.toString() ;
    }
}

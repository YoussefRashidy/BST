package io.github.youssefrashidy.ArrayGeneration;

public enum ArrayType {
    SORTED, INVERSELY_SORTED, RANDOM,
    NEARLY_SORTED_10 {
        public final int percentage = 10;

        public int getPercentage() {
            return percentage;
        }
    },
    NEARLY_SORTED_1 {
        public final int percentage = 1;

        public int getPercentage() {
            return percentage;
        }
    },
    NEARLY_SORTED_5 {
        public final int percentage = 5;

        public int getPercentage() {
            return percentage;
        }
    };

}

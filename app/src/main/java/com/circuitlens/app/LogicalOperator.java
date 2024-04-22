package com.circuitlens.app;

import java.util.function.BiFunction;

public enum LogicalOperator {
    AND () {
        @Override
        public BiFunction<Boolean, Boolean, Boolean> getFunction() {
            return (Boolean a, Boolean b) -> a && b;
        }

        @Override
        public String toString() {
            return "&";
        }
    },
    OR () {
        @Override
        public BiFunction<Boolean, Boolean, Boolean> getFunction() {
            return (Boolean a, Boolean b) -> a || b;
        }

        @Override
        public String toString() {
            return "|";
        }
    },
    NOT () {
        @Override
        public BiFunction<Boolean, Boolean, Boolean> getFunction() {
            return (Boolean a, Boolean b) -> !a;
        }

        @Override
        public String toString() {
            return "!";
        }
    };

    public abstract BiFunction<Boolean, Boolean, Boolean> getFunction();

    public abstract String toString();
}

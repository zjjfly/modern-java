package com.github.zjjfly.sequences;

import akka.serialization.JSerializer;
import clojure.lang.IFn;
import jaskell.util.CR;

import java.util.Arrays;

/**
 * @author zjjfly
 */
public class ChesireSerializer extends JSerializer {

    private static IFn generate;
    private static IFn parse;

    static {
        CR.require("cheshire.core");
        generate = CR.var("cheshire.core", "generate-string");
        parse = CR.var("cheshire.core", "parse-string");
    }

    @Override
    public Object fromBinaryJava(byte[] bytes, Class<?> manifest) {
        return parse.invoke(Arrays.toString(bytes));
    }

    @Override
    public int identifier() {
        return 1279807;
    }

    @Override
    public byte[] toBinary(Object o) {
        return ((String)generate.invoke(o)).getBytes();
    }

    @Override
    public boolean includeManifest() {
        return false;
    }
}

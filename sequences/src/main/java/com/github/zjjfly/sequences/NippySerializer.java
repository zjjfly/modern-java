package com.github.zjjfly.sequences;

import akka.serialization.JSerializer;
import clojure.lang.IFn;
import jaskell.util.CR;

/**
 * @author zjjfly
 */
public class NippySerializer extends JSerializer {

    private static IFn freeze;

    private static IFn thaw;

    private static IFn get;

    static {
        CR.require("taoensso.nippy");
        freeze = CR.var("taoensso.nippy", "freeze");
        thaw = CR.var("taoensso.nippy", "thaw");
        get = CR.var("clojure.core", "get");
    }

    @Override
    public Object fromBinaryJava(byte[] bytes, Class<?> manifest) {
        return thaw.invoke(bytes);
    }

    @Override
    public int identifier() {
        return 1279807;
    }

    @Override
    public byte[] toBinary(Object o) {
        return (byte[]) freeze.invoke(o);
    }

    @Override
    public boolean includeManifest() {
        return false;
    }
}

package com.github.zjjfly.sequences;

import com.esotericsoftware.kryo.Kryo;
import com.github.zjjfly.messages.CreateSequence;
import com.github.zjjfly.messages.DropSequence;
import com.github.zjjfly.messages.ListSequences;
import com.github.zjjfly.messages.NextValue;

/**
 * @author zjjfly
 */
public class KryoInit {
    public void customize(Kryo kryo) {
        kryo.register(CreateSequence.class);
        kryo.register(DropSequence.class);
        kryo.register(ListSequences.class);
        kryo.register(NextValue.class);
    }
}

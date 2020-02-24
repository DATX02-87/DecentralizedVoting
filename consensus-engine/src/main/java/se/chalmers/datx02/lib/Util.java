package se.chalmers.datx02.lib;

import org.zeromq.ZMQ;

import java.nio.ByteBuffer;
import java.util.Random;
import java.util.UUID;

public class Util {
    private static Random rand = new Random();

    public static byte[] generateId() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        return bb.array();
    }

}

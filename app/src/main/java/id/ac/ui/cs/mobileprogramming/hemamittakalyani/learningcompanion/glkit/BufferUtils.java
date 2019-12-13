package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.glkit;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class BufferUtils {

    public static FloatBuffer newFloatBuffer (int numFloats) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(numFloats * 4);
        buffer.order(ByteOrder.nativeOrder());
        return buffer.asFloatBuffer();
    }

    public static ShortBuffer newShortBuffer (int numShorts) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(numShorts * 2);
        buffer.order(ByteOrder.nativeOrder());
        return buffer.asShortBuffer();
    }

}

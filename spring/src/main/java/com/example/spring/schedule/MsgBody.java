package com.example.spring.schedule;

import org.apache.mina.core.buffer.IoBuffer;

import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * @author wanjun
 * @create 2022-09-15 8:14
 */
public class MsgBody {
    private static final int NUM_TYPE_BITS=3;
    private static final int NUM_LEN_BITS=5;
    private static final int MAX_LEN=(1<<NUM_LEN_BITS)-1;
    private static final int TYPE_MASK=(1<<NUM_TYPE_BITS)-1;
    private static final int LEN_MASK=MAX_LEN<<NUM_TYPE_BITS;

    private int numParams;
    private boolean modifiable;
    private IoBuffer buf;
    private StringBuilder detail;
    private ArrayList<Integer> intValues;
    private ArrayList<Float> floatValues;
    private ArrayList<String> stringValues;
    private ArrayList<byte[]> bytesValues;

    public static Charset uft8Charset=Charset.forName("utf-8");
    
}

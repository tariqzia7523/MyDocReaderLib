
package com.xls.bbbbb.office.fc.codec;


public interface BinaryEncoder extends Encoder {
    

    byte[] encode(byte[] source) throws EncoderException;
}  


package org.hac.common.crypto;

public interface SignatureInterface {
    boolean validateComponents();

    byte[] toByteArray();
}
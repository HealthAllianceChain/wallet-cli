package org.hac.keystore;


import org.hac.common.crypto.SignInterface;

public interface Credentials {
  SignInterface getPair();

  String getAddress();
}

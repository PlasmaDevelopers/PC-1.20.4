package com.mojang.realmsclient;

import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.exception.RealmsServiceException;

interface RealmsCall<T> {
  T request(RealmsClient paramRealmsClient) throws RealmsServiceException;
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\RealmsMainScreen$RealmsCall.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
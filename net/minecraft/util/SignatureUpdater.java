package net.minecraft.util;

import java.security.SignatureException;

@FunctionalInterface
public interface SignatureUpdater {
  void update(Output paramOutput) throws SignatureException;
  
  @FunctionalInterface
  public static interface Output {
    void update(byte[] param1ArrayOfbyte) throws SignatureException;
  }
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\SignatureUpdater.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
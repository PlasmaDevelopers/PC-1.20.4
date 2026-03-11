package net.minecraft.util;

import java.security.SignatureException;

@FunctionalInterface
public interface Output {
  void update(byte[] paramArrayOfbyte) throws SignatureException;
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\SignatureUpdater$Output.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
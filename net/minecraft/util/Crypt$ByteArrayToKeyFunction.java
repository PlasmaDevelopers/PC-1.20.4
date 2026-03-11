package net.minecraft.util;

interface ByteArrayToKeyFunction<T extends java.security.Key> {
  T apply(byte[] paramArrayOfbyte) throws CryptException;
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\Crypt$ByteArrayToKeyFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
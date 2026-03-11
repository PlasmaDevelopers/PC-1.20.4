package net.minecraft.util;

import java.util.function.IntConsumer;

public interface BitStorage {
  int getAndSet(int paramInt1, int paramInt2);
  
  void set(int paramInt1, int paramInt2);
  
  int get(int paramInt);
  
  long[] getRaw();
  
  int getSize();
  
  int getBits();
  
  void getAll(IntConsumer paramIntConsumer);
  
  void unpack(int[] paramArrayOfint);
  
  BitStorage copy();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\BitStorage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
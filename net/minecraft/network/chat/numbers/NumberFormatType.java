package net.minecraft.network.chat.numbers;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.FriendlyByteBuf;

public interface NumberFormatType<T extends NumberFormat> {
  MapCodec<T> mapCodec();
  
  void writeToStream(FriendlyByteBuf paramFriendlyByteBuf, T paramT);
  
  T readFromStream(FriendlyByteBuf paramFriendlyByteBuf);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\numbers\NumberFormatType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
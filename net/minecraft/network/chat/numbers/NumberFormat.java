package net.minecraft.network.chat.numbers;

import net.minecraft.network.chat.MutableComponent;

public interface NumberFormat {
  MutableComponent format(int paramInt);
  
  NumberFormatType<? extends NumberFormat> type();
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\numbers\NumberFormat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
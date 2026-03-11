/*    */ package net.minecraft.network.chat.numbers;
/*    */ 
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements NumberFormatType<BlankFormat>
/*    */ {
/* 12 */   private static final MapCodec<BlankFormat> CODEC = MapCodec.unit(BlankFormat.INSTANCE);
/*    */ 
/*    */   
/*    */   public MapCodec<BlankFormat> mapCodec() {
/* 16 */     return CODEC;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeToStream(FriendlyByteBuf $$0, BlankFormat $$1) {}
/*    */ 
/*    */   
/*    */   public BlankFormat readFromStream(FriendlyByteBuf $$0) {
/* 25 */     return BlankFormat.INSTANCE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\numbers\BlankFormat$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
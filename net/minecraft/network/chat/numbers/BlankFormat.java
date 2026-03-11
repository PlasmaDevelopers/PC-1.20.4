/*    */ package net.minecraft.network.chat.numbers;
/*    */ 
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ 
/*    */ public class BlankFormat implements NumberFormat {
/*  9 */   public static final BlankFormat INSTANCE = new BlankFormat();
/*    */   
/* 11 */   public static final NumberFormatType<BlankFormat> TYPE = new NumberFormatType<BlankFormat>() {
/* 12 */       private static final MapCodec<BlankFormat> CODEC = MapCodec.unit(BlankFormat.INSTANCE);
/*    */ 
/*    */       
/*    */       public MapCodec<BlankFormat> mapCodec() {
/* 16 */         return CODEC;
/*    */       }
/*    */ 
/*    */ 
/*    */       
/*    */       public void writeToStream(FriendlyByteBuf $$0, BlankFormat $$1) {}
/*    */ 
/*    */       
/*    */       public BlankFormat readFromStream(FriendlyByteBuf $$0) {
/* 25 */         return BlankFormat.INSTANCE;
/*    */       }
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MutableComponent format(int $$0) {
/* 34 */     return Component.empty();
/*    */   }
/*    */ 
/*    */   
/*    */   public NumberFormatType<BlankFormat> type() {
/* 39 */     return TYPE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\numbers\BlankFormat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
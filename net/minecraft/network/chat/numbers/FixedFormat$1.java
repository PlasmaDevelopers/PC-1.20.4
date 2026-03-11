/*    */ package net.minecraft.network.chat.numbers;
/*    */ 
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.ComponentSerialization;
/*    */ 
/*    */ class null implements NumberFormatType<FixedFormat> {
/*    */   static {
/* 11 */     CODEC = ComponentSerialization.CODEC.fieldOf("value").xmap(FixedFormat::new, $$0 -> $$0.value);
/*    */   }
/*    */ 
/*    */   
/*    */   private static final MapCodec<FixedFormat> CODEC;
/*    */   
/*    */   public MapCodec<FixedFormat> mapCodec() {
/* 18 */     return CODEC;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToStream(FriendlyByteBuf $$0, FixedFormat $$1) {
/* 23 */     $$0.writeComponent($$1.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public FixedFormat readFromStream(FriendlyByteBuf $$0) {
/* 28 */     Component $$1 = $$0.readComponentTrusted();
/* 29 */     return new FixedFormat($$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\numbers\FixedFormat$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
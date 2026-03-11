/*    */ package net.minecraft.network.chat.numbers;
/*    */ 
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.nbt.NbtOps;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.chat.Style;
/*    */ 
/*    */ class null
/*    */   implements NumberFormatType<StyledFormat> {
/*    */   static {
/* 13 */     CODEC = Style.Serializer.MAP_CODEC.xmap(StyledFormat::new, $$0 -> $$0.style);
/*    */   }
/*    */   private static final MapCodec<StyledFormat> CODEC;
/*    */   public MapCodec<StyledFormat> mapCodec() {
/* 17 */     return CODEC;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToStream(FriendlyByteBuf $$0, StyledFormat $$1) {
/* 22 */     $$0.writeWithCodec((DynamicOps)NbtOps.INSTANCE, Style.Serializer.CODEC, $$1.style);
/*    */   }
/*    */ 
/*    */   
/*    */   public StyledFormat readFromStream(FriendlyByteBuf $$0) {
/* 27 */     Style $$1 = (Style)$$0.readWithCodecTrusted((DynamicOps)NbtOps.INSTANCE, Style.Serializer.CODEC);
/* 28 */     return new StyledFormat($$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\numbers\StyledFormat$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
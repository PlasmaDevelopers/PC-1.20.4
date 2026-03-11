/*    */ package net.minecraft.network.chat.numbers;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.nbt.NbtOps;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.Style;
/*    */ 
/*    */ public class StyledFormat implements NumberFormat {
/* 12 */   public static final NumberFormatType<StyledFormat> TYPE = new NumberFormatType<StyledFormat>() { static {
/* 13 */         CODEC = Style.Serializer.MAP_CODEC.xmap(StyledFormat::new, $$0 -> $$0.style);
/*    */       }
/*    */       private static final MapCodec<StyledFormat> CODEC;
/*    */       public MapCodec<StyledFormat> mapCodec() {
/* 17 */         return CODEC;
/*    */       }
/*    */ 
/*    */       
/*    */       public void writeToStream(FriendlyByteBuf $$0, StyledFormat $$1) {
/* 22 */         $$0.writeWithCodec((DynamicOps)NbtOps.INSTANCE, Style.Serializer.CODEC, $$1.style);
/*    */       }
/*    */ 
/*    */       
/*    */       public StyledFormat readFromStream(FriendlyByteBuf $$0) {
/* 27 */         Style $$1 = (Style)$$0.readWithCodecTrusted((DynamicOps)NbtOps.INSTANCE, Style.Serializer.CODEC);
/* 28 */         return new StyledFormat($$1);
/*    */       } }
/*    */   ;
/*    */   
/* 32 */   public static final StyledFormat NO_STYLE = new StyledFormat(Style.EMPTY);
/* 33 */   public static final StyledFormat SIDEBAR_DEFAULT = new StyledFormat(Style.EMPTY.withColor(ChatFormatting.RED));
/* 34 */   public static final StyledFormat PLAYER_LIST_DEFAULT = new StyledFormat(Style.EMPTY.withColor(ChatFormatting.YELLOW));
/*    */   
/*    */   final Style style;
/*    */   
/*    */   public StyledFormat(Style $$0) {
/* 39 */     this.style = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public MutableComponent format(int $$0) {
/* 44 */     return Component.literal(Integer.toString($$0)).withStyle(this.style);
/*    */   }
/*    */ 
/*    */   
/*    */   public NumberFormatType<StyledFormat> type() {
/* 49 */     return TYPE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\numbers\StyledFormat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.network.chat.numbers;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.IdMap;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ public class NumberFormatTypes {
/*    */   static {
/* 10 */     MAP_CODEC = BuiltInRegistries.NUMBER_FORMAT_TYPE.byNameCodec().dispatchMap(NumberFormat::type, $$0 -> $$0.mapCodec().codec());
/* 11 */   } public static final Codec<NumberFormat> CODEC = MAP_CODEC.codec(); public static final MapCodec<NumberFormat> MAP_CODEC;
/*    */   
/*    */   public static NumberFormatType<?> bootstrap(Registry<NumberFormatType<?>> $$0) {
/* 14 */     NumberFormatType<?> $$1 = (NumberFormatType)Registry.register($$0, "blank", BlankFormat.TYPE);
/* 15 */     Registry.register($$0, "styled", StyledFormat.TYPE);
/* 16 */     Registry.register($$0, "fixed", FixedFormat.TYPE);
/*    */     
/* 18 */     return $$1;
/*    */   }
/*    */   
/*    */   public static <T extends NumberFormat> void writeToStream(FriendlyByteBuf $$0, T $$1) {
/* 22 */     NumberFormatType<T> $$2 = (NumberFormatType)$$1.type();
/* 23 */     $$0.writeId((IdMap)BuiltInRegistries.NUMBER_FORMAT_TYPE, $$2);
/* 24 */     $$2.writeToStream($$0, $$1);
/*    */   }
/*    */   
/*    */   public static NumberFormat readFromStream(FriendlyByteBuf $$0) {
/* 28 */     NumberFormatType<?> $$1 = (NumberFormatType)$$0.readById((IdMap)BuiltInRegistries.NUMBER_FORMAT_TYPE);
/* 29 */     return (NumberFormat)$$1.readFromStream($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\numbers\NumberFormatTypes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
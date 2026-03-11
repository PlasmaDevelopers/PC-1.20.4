/*    */ package net.minecraft.util.valueproviders;
/*    */ 
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public abstract class IntProvider {
/* 11 */   private static final Codec<Either<Integer, IntProvider>> CONSTANT_OR_DISPATCH_CODEC = Codec.either((Codec)Codec.INT, BuiltInRegistries.INT_PROVIDER_TYPE
/*    */       
/* 13 */       .byNameCodec().dispatch(IntProvider::getType, IntProviderType::codec));
/*    */   static {
/* 15 */     CODEC = CONSTANT_OR_DISPATCH_CODEC.xmap($$0 -> (IntProvider)$$0.map(ConstantInt::of, ()), $$0 -> ($$0.getType() == IntProviderType.CONSTANT) ? Either.left(Integer.valueOf(((ConstantInt)$$0).getValue())) : Either.right($$0));
/*    */   }
/*    */   
/*    */   public static final Codec<IntProvider> CODEC;
/*    */   
/*    */   public static Codec<IntProvider> codec(int $$0, int $$1) {
/* 21 */     return codec($$0, $$1, CODEC);
/*    */   }
/*    */   
/*    */   public static <T extends IntProvider> Codec<T> codec(int $$0, int $$1, Codec<T> $$2) {
/* 25 */     return ExtraCodecs.validate($$2, $$2 -> ($$2.getMinValue() < $$0) ? DataResult.error(()) : (($$2.getMaxValue() > $$1) ? DataResult.error(()) : DataResult.success($$2)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 36 */   public static final Codec<IntProvider> NON_NEGATIVE_CODEC = codec(0, 2147483647);
/* 37 */   public static final Codec<IntProvider> POSITIVE_CODEC = codec(1, 2147483647);
/*    */   
/*    */   public abstract int sample(RandomSource paramRandomSource);
/*    */   
/*    */   public abstract int getMinValue();
/*    */   
/*    */   public abstract int getMaxValue();
/*    */   
/*    */   public abstract IntProviderType<?> getType();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\valueproviders\IntProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
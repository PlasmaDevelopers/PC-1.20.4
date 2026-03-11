/*    */ package net.minecraft.util.valueproviders;
/*    */ 
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ 
/*    */ public abstract class FloatProvider implements SampledFloat {
/* 10 */   private static final Codec<Either<Float, FloatProvider>> CONSTANT_OR_DISPATCH_CODEC = Codec.either((Codec)Codec.FLOAT, BuiltInRegistries.FLOAT_PROVIDER_TYPE
/*    */       
/* 12 */       .byNameCodec().dispatch(FloatProvider::getType, FloatProviderType::codec));
/*    */   static {
/* 14 */     CODEC = CONSTANT_OR_DISPATCH_CODEC.xmap($$0 -> (FloatProvider)$$0.map(ConstantFloat::of, ()), $$0 -> ($$0.getType() == FloatProviderType.CONSTANT) ? Either.left(Float.valueOf(((ConstantFloat)$$0).getValue())) : Either.right($$0));
/*    */   }
/*    */   
/*    */   public static final Codec<FloatProvider> CODEC;
/*    */   
/*    */   public static Codec<FloatProvider> codec(float $$0, float $$1) {
/* 20 */     return ExtraCodecs.validate(CODEC, $$2 -> ($$2.getMinValue() < $$0) ? DataResult.error(()) : (($$2.getMaxValue() > $$1) ? DataResult.error(()) : DataResult.success($$2)));
/*    */   }
/*    */   
/*    */   public abstract float getMinValue();
/*    */   
/*    */   public abstract float getMaxValue();
/*    */   
/*    */   public abstract FloatProviderType<?> getType();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\valueproviders\FloatProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
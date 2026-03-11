/*    */ package net.minecraft.util.valueproviders;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ 
/*    */ public interface FloatProviderType<P extends FloatProvider> {
/*  8 */   public static final FloatProviderType<ConstantFloat> CONSTANT = register("constant", ConstantFloat.CODEC);
/*  9 */   public static final FloatProviderType<UniformFloat> UNIFORM = register("uniform", UniformFloat.CODEC);
/* 10 */   public static final FloatProviderType<ClampedNormalFloat> CLAMPED_NORMAL = register("clamped_normal", ClampedNormalFloat.CODEC);
/* 11 */   public static final FloatProviderType<TrapezoidFloat> TRAPEZOID = register("trapezoid", TrapezoidFloat.CODEC);
/*    */ 
/*    */   
/*    */   Codec<P> codec();
/*    */   
/*    */   static <P extends FloatProvider> FloatProviderType<P> register(String $$0, Codec<P> $$1) {
/* 17 */     return (FloatProviderType<P>)Registry.register(BuiltInRegistries.FLOAT_PROVIDER_TYPE, $$0, () -> $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\valueproviders\FloatProviderType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
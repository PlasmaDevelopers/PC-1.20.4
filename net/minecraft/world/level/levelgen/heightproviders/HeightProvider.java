/*    */ package net.minecraft.world.level.levelgen.heightproviders;
/*    */ 
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.levelgen.VerticalAnchor;
/*    */ import net.minecraft.world.level.levelgen.WorldGenerationContext;
/*    */ 
/*    */ public abstract class HeightProvider {
/* 11 */   private static final Codec<Either<VerticalAnchor, HeightProvider>> CONSTANT_OR_DISPATCH_CODEC = Codec.either(VerticalAnchor.CODEC, BuiltInRegistries.HEIGHT_PROVIDER_TYPE
/*    */       
/* 13 */       .byNameCodec().dispatch(HeightProvider::getType, HeightProviderType::codec));
/*    */   static {
/* 15 */     CODEC = CONSTANT_OR_DISPATCH_CODEC.xmap($$0 -> (HeightProvider)$$0.map(ConstantHeight::of, ()), $$0 -> ($$0.getType() == HeightProviderType.CONSTANT) ? Either.left(((ConstantHeight)$$0).getValue()) : Either.right($$0));
/*    */   }
/*    */   
/*    */   public static final Codec<HeightProvider> CODEC;
/*    */   
/*    */   public abstract HeightProviderType<?> getType();
/*    */   
/*    */   public abstract int sample(RandomSource paramRandomSource, WorldGenerationContext paramWorldGenerationContext);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\heightproviders\HeightProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
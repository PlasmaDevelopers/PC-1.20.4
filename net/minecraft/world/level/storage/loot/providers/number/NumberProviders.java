/*    */ package net.minecraft.world.level.storage.loot.providers.number;
/*    */ 
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ 
/*    */ public class NumberProviders
/*    */ {
/* 13 */   private static final Codec<NumberProvider> TYPED_CODEC = BuiltInRegistries.LOOT_NUMBER_PROVIDER_TYPE.byNameCodec().dispatch(NumberProvider::getType, LootNumberProviderType::codec);
/*    */   static {
/* 15 */     CODEC = ExtraCodecs.lazyInitializedCodec(() -> {
/*    */           Codec<NumberProvider> $$0 = ExtraCodecs.withAlternative(TYPED_CODEC, UniformGenerator.CODEC);
/*    */           return Codec.either(ConstantValue.INLINE_CODEC, $$0).xmap((), ());
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<NumberProvider> CODEC;
/*    */   
/* 24 */   public static final LootNumberProviderType CONSTANT = register("constant", (Codec)ConstantValue.CODEC);
/* 25 */   public static final LootNumberProviderType UNIFORM = register("uniform", (Codec)UniformGenerator.CODEC);
/* 26 */   public static final LootNumberProviderType BINOMIAL = register("binomial", (Codec)BinomialDistributionGenerator.CODEC);
/* 27 */   public static final LootNumberProviderType SCORE = register("score", (Codec)ScoreboardValue.CODEC);
/*    */   
/*    */   private static LootNumberProviderType register(String $$0, Codec<? extends NumberProvider> $$1) {
/* 30 */     return (LootNumberProviderType)Registry.register(BuiltInRegistries.LOOT_NUMBER_PROVIDER_TYPE, new ResourceLocation($$0), new LootNumberProviderType($$1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\providers\number\NumberProviders.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.world.level.storage.loot.providers.score;
/*    */ 
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ 
/*    */ public class ScoreboardNameProviders
/*    */ {
/* 13 */   private static final Codec<ScoreboardNameProvider> TYPED_CODEC = BuiltInRegistries.LOOT_SCORE_PROVIDER_TYPE.byNameCodec().dispatch(ScoreboardNameProvider::getType, LootScoreProviderType::codec);
/*    */   
/* 15 */   public static final Codec<ScoreboardNameProvider> CODEC = ExtraCodecs.lazyInitializedCodec(() -> Codec.either(ContextScoreboardNameProvider.INLINE_CODEC, TYPED_CODEC).xmap((), ()));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 22 */   public static final LootScoreProviderType FIXED = register("fixed", (Codec)FixedScoreboardNameProvider.CODEC);
/* 23 */   public static final LootScoreProviderType CONTEXT = register("context", (Codec)ContextScoreboardNameProvider.CODEC);
/*    */   
/*    */   private static LootScoreProviderType register(String $$0, Codec<? extends ScoreboardNameProvider> $$1) {
/* 26 */     return (LootScoreProviderType)Registry.register(BuiltInRegistries.LOOT_SCORE_PROVIDER_TYPE, new ResourceLocation($$0), new LootScoreProviderType($$1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\providers\score\ScoreboardNameProviders.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
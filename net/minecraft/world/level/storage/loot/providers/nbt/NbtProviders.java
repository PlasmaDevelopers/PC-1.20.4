/*    */ package net.minecraft.world.level.storage.loot.providers.nbt;
/*    */ 
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ 
/*    */ public class NbtProviders
/*    */ {
/* 13 */   private static final Codec<NbtProvider> TYPED_CODEC = BuiltInRegistries.LOOT_NBT_PROVIDER_TYPE.byNameCodec().dispatch(NbtProvider::getType, LootNbtProviderType::codec);
/*    */   
/* 15 */   public static final Codec<NbtProvider> CODEC = ExtraCodecs.lazyInitializedCodec(() -> Codec.either(ContextNbtProvider.INLINE_CODEC, TYPED_CODEC).xmap((), ()));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 22 */   public static final LootNbtProviderType STORAGE = register("storage", (Codec)StorageNbtProvider.CODEC);
/* 23 */   public static final LootNbtProviderType CONTEXT = register("context", (Codec)ContextNbtProvider.CODEC);
/*    */   
/*    */   private static LootNbtProviderType register(String $$0, Codec<? extends NbtProvider> $$1) {
/* 26 */     return (LootNbtProviderType)Registry.register(BuiltInRegistries.LOOT_NBT_PROVIDER_TYPE, new ResourceLocation($$0), new LootNbtProviderType($$1));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\providers\nbt\NbtProviders.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
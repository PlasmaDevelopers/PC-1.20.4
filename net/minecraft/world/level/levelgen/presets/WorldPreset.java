/*    */ package net.minecraft.world.level.levelgen.presets;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.Lifecycle;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.MappedRegistry;
/*    */ import net.minecraft.core.WritableRegistry;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.RegistryFileCodec;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.world.level.dimension.LevelStem;
/*    */ import net.minecraft.world.level.levelgen.WorldDimensions;
/*    */ 
/*    */ public class WorldPreset {
/*    */   static {
/* 22 */     DIRECT_CODEC = ExtraCodecs.validate(RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.unboundedMap(ResourceKey.codec(Registries.LEVEL_STEM), LevelStem.CODEC).fieldOf("dimensions").forGetter(())).apply((Applicative)$$0, WorldPreset::new)), WorldPreset::requireOverworld);
/*    */   }
/*    */   
/*    */   public static final Codec<WorldPreset> DIRECT_CODEC;
/* 26 */   public static final Codec<Holder<WorldPreset>> CODEC = (Codec<Holder<WorldPreset>>)RegistryFileCodec.create(Registries.WORLD_PRESET, DIRECT_CODEC);
/*    */   
/*    */   private final Map<ResourceKey<LevelStem>, LevelStem> dimensions;
/*    */   
/*    */   public WorldPreset(Map<ResourceKey<LevelStem>, LevelStem> $$0) {
/* 31 */     this.dimensions = $$0;
/*    */   }
/*    */   
/*    */   private Registry<LevelStem> createRegistry() {
/* 35 */     MappedRegistry mappedRegistry = new MappedRegistry(Registries.LEVEL_STEM, Lifecycle.experimental());
/* 36 */     WorldDimensions.keysInOrder(this.dimensions.keySet().stream()).forEach($$1 -> {
/*    */           LevelStem $$2 = this.dimensions.get($$1);
/*    */           if ($$2 != null) {
/*    */             $$0.register($$1, $$2, Lifecycle.stable());
/*    */           }
/*    */         });
/* 42 */     return mappedRegistry.freeze();
/*    */   }
/*    */   
/*    */   public WorldDimensions createWorldDimensions() {
/* 46 */     return new WorldDimensions(createRegistry());
/*    */   }
/*    */   
/*    */   public Optional<LevelStem> overworld() {
/* 50 */     return Optional.ofNullable(this.dimensions.get(LevelStem.OVERWORLD));
/*    */   }
/*    */ 
/*    */   
/*    */   private static DataResult<WorldPreset> requireOverworld(WorldPreset $$0) {
/* 55 */     if ($$0.overworld().isEmpty()) {
/* 56 */       return DataResult.error(() -> "Missing overworld dimension");
/*    */     }
/* 58 */     return DataResult.success($$0, Lifecycle.stable());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\presets\WorldPreset.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
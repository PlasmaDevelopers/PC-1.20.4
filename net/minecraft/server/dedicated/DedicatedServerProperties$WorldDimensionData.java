/*     */ package net.minecraft.server.dedicated;
/*     */ 
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.JsonOps;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.resources.RegistryOps;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.FlatLevelSource;
/*     */ import net.minecraft.world.level.levelgen.WorldDimensions;
/*     */ import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
/*     */ import net.minecraft.world.level.levelgen.presets.WorldPreset;
/*     */ import net.minecraft.world.level.levelgen.presets.WorldPresets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class WorldDimensionData
/*     */   extends Record
/*     */ {
/*     */   private final JsonObject generatorSettings;
/*     */   private final String levelType;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/server/dedicated/DedicatedServerProperties$WorldDimensionData;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #210	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/server/dedicated/DedicatedServerProperties$WorldDimensionData;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/dedicated/DedicatedServerProperties$WorldDimensionData;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #210	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/server/dedicated/DedicatedServerProperties$WorldDimensionData;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/server/dedicated/DedicatedServerProperties$WorldDimensionData;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #210	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/server/dedicated/DedicatedServerProperties$WorldDimensionData;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   WorldDimensionData(JsonObject $$0, String $$1) {
/* 210 */     this.generatorSettings = $$0; this.levelType = $$1; } public JsonObject generatorSettings() { return this.generatorSettings; } public String levelType() { return this.levelType; }
/* 211 */    private static final Map<String, ResourceKey<WorldPreset>> LEGACY_PRESET_NAMES = Map.of("default", WorldPresets.NORMAL, "largebiomes", WorldPresets.LARGE_BIOMES);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldDimensions create(RegistryAccess $$0) {
/* 217 */     Registry<WorldPreset> $$1 = $$0.registryOrThrow(Registries.WORLD_PRESET);
/*     */     
/* 219 */     Holder.Reference<WorldPreset> $$2 = (Holder.Reference<WorldPreset>)$$1.getHolder(WorldPresets.NORMAL).or(() -> $$0.holders().findAny()).orElseThrow(() -> new IllegalStateException("Invalid datapack contents: can't find default preset"));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 224 */     Objects.requireNonNull($$1);
/* 225 */     Holder<WorldPreset> $$3 = Optional.<ResourceLocation>ofNullable(ResourceLocation.tryParse(this.levelType)).map($$0 -> ResourceKey.create(Registries.WORLD_PRESET, $$0)).or(() -> Optional.ofNullable(LEGACY_PRESET_NAMES.get(this.levelType))).flatMap($$1::getHolder).orElseGet(() -> {
/*     */           DedicatedServerProperties.LOGGER.warn("Failed to parse level-type {}, defaulting to {}", this.levelType, $$0.key().location());
/*     */           
/*     */           return $$0;
/*     */         });
/* 230 */     WorldDimensions $$4 = ((WorldPreset)$$3.value()).createWorldDimensions();
/*     */ 
/*     */     
/* 233 */     if ($$3.is(WorldPresets.FLAT)) {
/* 234 */       RegistryOps<JsonElement> $$5 = RegistryOps.create((DynamicOps)JsonOps.INSTANCE, (HolderLookup.Provider)$$0);
/* 235 */       Objects.requireNonNull(DedicatedServerProperties.LOGGER); Optional<FlatLevelGeneratorSettings> $$6 = FlatLevelGeneratorSettings.CODEC.parse(new Dynamic((DynamicOps)$$5, generatorSettings())).resultOrPartial(DedicatedServerProperties.LOGGER::error);
/* 236 */       if ($$6.isPresent()) {
/* 237 */         return $$4.replaceOverworldGenerator($$0, (ChunkGenerator)new FlatLevelSource($$6.get()));
/*     */       }
/*     */     } 
/*     */     
/* 241 */     return $$4;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\dedicated\DedicatedServerProperties$WorldDimensionData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.client.gui.screens.worldselection;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.client.gui.screens.CreateBuffetWorldScreen;
/*    */ import net.minecraft.client.gui.screens.CreateFlatWorldScreen;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderGetter;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ import net.minecraft.world.level.biome.BiomeSource;
/*    */ import net.minecraft.world.level.biome.FixedBiomeSource;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.FlatLevelSource;
/*    */ import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
/*    */ import net.minecraft.world.level.levelgen.WorldDimensions;
/*    */ import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
/*    */ import net.minecraft.world.level.levelgen.presets.WorldPreset;
/*    */ import net.minecraft.world.level.levelgen.presets.WorldPresets;
/*    */ 
/*    */ public interface PresetEditor {
/*    */   public static final Map<Optional<ResourceKey<WorldPreset>>, PresetEditor> EDITORS;
/*    */   
/*    */   static {
/* 31 */     EDITORS = Map.of(
/* 32 */         Optional.of(WorldPresets.FLAT), ($$0, $$1) -> {
/*    */           ChunkGenerator $$2 = $$1.selectedDimensions().overworld();
/*    */           
/*    */           RegistryAccess.Frozen frozen = $$1.worldgenLoadContext();
/*    */           
/*    */           HolderLookup.RegistryLookup registryLookup1 = frozen.lookupOrThrow(Registries.BIOME);
/*    */           
/*    */           HolderLookup.RegistryLookup registryLookup2 = frozen.lookupOrThrow(Registries.STRUCTURE_SET);
/*    */           
/*    */           HolderLookup.RegistryLookup registryLookup3 = frozen.lookupOrThrow(Registries.PLACED_FEATURE);
/*    */           
/*    */           return (Screen)new CreateFlatWorldScreen($$0, (), ($$2 instanceof FlatLevelSource) ? ((FlatLevelSource)$$2).settings() : FlatLevelGeneratorSettings.getDefault((HolderGetter)registryLookup1, (HolderGetter)registryLookup2, (HolderGetter)registryLookup3));
/* 44 */         }Optional.of(WorldPresets.SINGLE_BIOME_SURFACE), ($$0, $$1) -> new CreateBuffetWorldScreen($$0, $$1, ()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static WorldCreationContext.DimensionsUpdater flatWorldConfigurator(FlatLevelGeneratorSettings $$0) {
/* 52 */     return ($$1, $$2) -> {
/*    */         FlatLevelSource flatLevelSource = new FlatLevelSource($$0);
/*    */         return $$2.replaceOverworldGenerator((RegistryAccess)$$1, (ChunkGenerator)flatLevelSource);
/*    */       };
/*    */   }
/*    */   
/*    */   private static WorldCreationContext.DimensionsUpdater fixedBiomeConfigurator(Holder<Biome> $$0) {
/* 59 */     return ($$1, $$2) -> {
/*    */         Registry<NoiseGeneratorSettings> $$3 = $$1.registryOrThrow(Registries.NOISE_SETTINGS);
/*    */         Holder.Reference reference = $$3.getHolderOrThrow(NoiseGeneratorSettings.OVERWORLD);
/*    */         FixedBiomeSource fixedBiomeSource = new FixedBiomeSource($$0);
/*    */         NoiseBasedChunkGenerator noiseBasedChunkGenerator = new NoiseBasedChunkGenerator((BiomeSource)fixedBiomeSource, (Holder)reference);
/*    */         return $$2.replaceOverworldGenerator((RegistryAccess)$$1, (ChunkGenerator)noiseBasedChunkGenerator);
/*    */       };
/*    */   }
/*    */   
/*    */   Screen createEditScreen(CreateWorldScreen paramCreateWorldScreen, WorldCreationContext paramWorldCreationContext);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\PresetEditor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
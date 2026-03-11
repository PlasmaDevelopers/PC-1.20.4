/*    */ package net.minecraft.world.level.levelgen.structure;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function4;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Map;
/*    */ import net.minecraft.core.HolderSet;
/*    */ import net.minecraft.core.RegistryCodecs;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ import net.minecraft.world.entity.MobCategory;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ import net.minecraft.world.level.levelgen.GenerationStep;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class StructureSettings
/*    */   extends Record
/*    */ {
/*    */   final HolderSet<Biome> biomes;
/*    */   final Map<MobCategory, StructureSpawnOverride> spawnOverrides;
/*    */   final GenerationStep.Decoration step;
/*    */   final TerrainAdjustment terrainAdaptation;
/*    */   public static final MapCodec<StructureSettings> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #47	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #47	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #47	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/Structure$StructureSettings;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public StructureSettings(HolderSet<Biome> $$0, Map<MobCategory, StructureSpawnOverride> $$1, GenerationStep.Decoration $$2, TerrainAdjustment $$3) {
/* 47 */     this.biomes = $$0; this.spawnOverrides = $$1; this.step = $$2; this.terrainAdaptation = $$3; } public HolderSet<Biome> biomes() { return this.biomes; } public Map<MobCategory, StructureSpawnOverride> spawnOverrides() { return this.spawnOverrides; } public GenerationStep.Decoration step() { return this.step; } public TerrainAdjustment terrainAdaptation() { return this.terrainAdaptation; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 53 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(StructureSettings::biomes), (App)Codec.simpleMap(MobCategory.CODEC, StructureSpawnOverride.CODEC, StringRepresentable.keys((StringRepresentable[])MobCategory.values())).fieldOf("spawn_overrides").forGetter(StructureSettings::spawnOverrides), (App)GenerationStep.Decoration.CODEC.fieldOf("step").forGetter(StructureSettings::step), (App)TerrainAdjustment.CODEC.optionalFieldOf("terrain_adaptation", TerrainAdjustment.NONE).forGetter(StructureSettings::terrainAdaptation)).apply((Applicative)$$0, StructureSettings::new));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\Structure$StructureSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */
/*    */ package net.minecraft.world.level.levelgen.structure.placement;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.RegistryFileCodec;
/*    */ import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
/*    */ import net.minecraft.world.level.levelgen.structure.StructureSet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public final class ExclusionZone
/*    */   extends Record
/*    */ {
/*    */   private final Holder<StructureSet> otherSet;
/*    */   private final int chunkCount;
/*    */   public static final Codec<ExclusionZone> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement$ExclusionZone;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #39	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement$ExclusionZone;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement$ExclusionZone;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #39	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement$ExclusionZone;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement$ExclusionZone;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #39	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/placement/StructurePlacement$ExclusionZone;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   public Holder<StructureSet> otherSet() {
/* 39 */     return this.otherSet; } public int chunkCount() { return this.chunkCount; }
/* 40 */   public ExclusionZone(Holder<StructureSet> $$0, int $$1) { this.otherSet = $$0; this.chunkCount = $$1; } static {
/* 41 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)RegistryFileCodec.create(Registries.STRUCTURE_SET, StructureSet.DIRECT_CODEC, false).fieldOf("other_set").forGetter(ExclusionZone::otherSet), (App)Codec.intRange(1, 16).fieldOf("chunk_count").forGetter(ExclusionZone::chunkCount)).apply((Applicative)$$0, ExclusionZone::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   boolean isPlacementForbidden(ChunkGeneratorStructureState $$0, int $$1, int $$2) {
/* 48 */     return $$0.hasStructureChunkInRange(this.otherSet, $$1, $$2, this.chunkCount);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\placement\StructurePlacement$ExclusionZone.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */